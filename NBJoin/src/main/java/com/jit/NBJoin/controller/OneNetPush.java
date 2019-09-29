package com.jit.NBJoin.controller;

import com.jit.NBJoin.feignclient.Pushservice;
import com.jit.NBJoin.feignclient.influxservice.InfluxDao;
import com.jit.NBJoin.feignclient.mongoservice.MongoDao;
import com.jit.NBJoin.model.mongodb.device.Device;
import com.jit.NBJoin.model.mongodb.device.JoinInfo;
import com.jit.NBJoin.model.mongodb.sensor.Sensor;
import com.jit.NBJoin.model.mongodb.sensor.SensorInfo;
import com.jit.NBJoin.model.onenet.downdata.Args;
import com.jit.NBJoin.model.onenet.downdata.ExecuteRet;
import com.jit.NBJoin.model.onenet.downdata.Execute;
import com.jit.NBJoin.model.rabbitmq.MqData;
import com.jit.NBJoin.model.onenet.updata.DataPoint;
import com.jit.NBJoin.model.onenet.updata.LoginInfo;
import com.jit.NBJoin.model.Lwm2mObject;
import com.jit.NBJoin.util.MyThreadPoolExecutor;
import com.jit.NBJoin.util.Util;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@RestController
@EnableAutoConfiguration
@Slf4j
public class OneNetPush {
    @Autowired
    private InfluxDao influxDao;
    @Autowired
    private MongoDao mongoDao;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private Pushservice pushservice;

    private static String token = "abcdefghijkmlnopqrstuvwxyz";//用户自定义token和OneNet第三方平台配置里的token一致
    private static String aeskey = "whBx2ZwAU5LOHVimPj1MPx56QRe3OsGGWRe4dr17crV";//aeskey和OneNet第三方平台配置里的token一致
    //用于发下行
    private static WebClient webClient = WebClient.builder()
            .baseUrl("http://api.heclouds.com")
            .defaultHeader("api-key", "EOGxgpokcGfmPrWQeMNpHeSpo4k=")
            .defaultHeader("Content-Type", "application/json")
            .build();

    @PostMapping(value = "/receive")
    public String receive(@RequestBody String body) throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        log.info("data receive:  body String --- " + body);
        Util.BodyObj obj = Util.resolveBody(body, false);
        if (obj != null) {
            //验证token
            boolean dataRight = Util.checkSignature(obj, token);
            if (dataRight) {
                //json 解析
                JSONObject jsonObject = JSONObject.fromObject(obj.getMsg().toString());

                if (2 == (int) jsonObject.get("type")) {
                    LoginInfo loginInfo = (LoginInfo) JSONObject.toBean(jsonObject, LoginInfo.class);
                    String deviceId = loginInfo.getDev_id().toString();

                    MyThreadPoolExecutor.getInstance().getMyThreadPoolExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            //访问mongodb 修改在线状态
                            Device device = mongoDao.findDeviceById(deviceId);
                            //若该设备已存在则更新状态，否则新增
                            if (null != device) {
                                mongoDao.updateDeviceState(deviceId, loginInfo.getStatus());
                            } else {
                                JoinInfo joinInfo = JoinInfo.builder()
                                        .imei("")
                                        .build();
                                Device newDevice = Device.builder()
                                        .deviceId(deviceId)
                                        .state(loginInfo.getStatus())
                                        .joinInfo(joinInfo)
                                        .joinType("onenet")
                                        .build();
                                if (mongoDao.saveDevice(newDevice) != null){
                                    System.out.println("device save success");
                                }
                            }
                        }
                    });

                } else if (7 == (int) jsonObject.get("type")) {
                    //System.out.println("下发响应");
                } else {
                    DataPoint dataPoint = (DataPoint) JSONObject.toBean(jsonObject, DataPoint.class);
                    System.out.println(dataPoint.toString());

                    //解析 objId、instId、resId
                    String[] code = dataPoint.getDs_id().split("_");
                    Integer objId = Integer.valueOf(code[0]);
                    Integer instId = Integer.valueOf(code[1]);
                    Integer resId = Integer.valueOf(code[2]);
                    String type = Lwm2mObject.Type(objId);
                    System.out.println("objId:" + objId + " instId:" + instId + " resId:" + resId);

                    //deviceId
                    String deviceId = dataPoint.getDev_id().toString();

                    //存influxDB  ok
                    MyThreadPoolExecutor.getInstance().getMyThreadPoolExecutor().execute(new Runnable() {
                        @Override
                        public void run() {

                            //存入influxDB
                            String measurement = deviceId + "_" + type;
                            Map<String, String> tag = new HashMap<>();
                            Map<String, Object> field = new HashMap<>();
                            field.put("value", dataPoint.getValue());

                            JSONObject tags = JSONObject.fromObject(tag);
                            JSONObject fields = JSONObject.fromObject(field);
                            influxDao.insert(measurement, tags.toString(), fields.toString());
                        }
                    });

                    //发送到mq  ok
                    MyThreadPoolExecutor.getInstance().getMyThreadPoolExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            //发到mq  只传必要信息 （需添加字段）
                            MqData mqData = MqData.builder()
                                    .device_id(deviceId)
                                    .type(type)
                                    .value(dataPoint.getValue())
                                    .build();
                            JSONObject message = JSONObject.fromObject(mqData);
                            //rabbitTemplate.convertAndSend(message.toString());
                            pushservice.push(message.toString());
                        }
                    });

                    //修改mongoDB数据 ok
                    MyThreadPoolExecutor.getInstance().getMyThreadPoolExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            //判断设备是否存在  不存在则新建
                            Device device = mongoDao.findDeviceById(deviceId);
                            if (device == null){
                                JoinInfo joinInfo = JoinInfo.builder()
                                        .imei("")
                                        .build();
                                Device newDevice = Device.builder()
                                        .deviceId(deviceId)
                                        .state(1)
                                        .joinInfo(joinInfo)
                                        .joinType("onenet")
                                        .build();
                                if (mongoDao.saveDevice(newDevice) != null){
                                    System.out.println("device save success");
                                }
                            }

                            //修改最后上报时间 和 sensor 对应的值 和 状态
                            Sensor sensor = mongoDao.findSensorByDeviceIdAndType(deviceId, type);

                            if (null != sensor) {
                                //sensor 修改值
                                mongoDao.updateSensorValue(deviceId, type, dataPoint.getValue().toString());
                                //device 修改最后运行时间
                                mongoDao.updateDeviceTime(deviceId, dataPoint.getAt().toString());
                                mongoDao.updateDeviceState(deviceId,1);
                            } else {
                                //添加新sensor
                                SensorInfo sensorInfo = SensorInfo.builder()
                                        .objId(objId)
                                        .instId(instId)
                                        .resId(resId)
                                        .build();
                                Sensor newSensor = Sensor.builder()
                                        .sensorType(type)
                                        .deviceId(deviceId)
                                        .state(1)
                                        .value(dataPoint.getValue().toString())
                                        .info(sensorInfo)
                                        .build();
                                if (mongoDao.saveSensor(newSensor) != null){
                                    System.out.println("sensor save success");
                                }
                            }
                        }
                    });

                }
            } else {
                log.warn("data receive: signature error");
            }
        } else {
            log.warn("data receive: body empty error");
        }
        return "ok";
    }

    @GetMapping(value = "/receive")
    public String check(@RequestParam(value = "msg") String msg,
                        @RequestParam(value = "nonce") String nonce,
                        @RequestParam(value = "signature") String signature) throws UnsupportedEncodingException {
        log.info("url&token check: msg:{} nonce:{} signature:{}", msg, nonce, signature);
        if (Util.checkToken(msg, nonce, signature, token)) {
            return msg;
        } else {
            return "error";
        }
    }

    //发送下行控制信息
    @PostMapping(value = "/down")
    public String down(@RequestParam("params") String params,
                       @RequestParam("args") String args) {

//        JSONObject jsonObject = JSONObject.fromObject(params);
//        Execute execute = (Execute) JSONObject.toBean(jsonObject,Execute.class);

        Execute execute = new Execute("869405030315502", 3303, 0, 5700);

        //ok
        Mono<ExecuteRet> res = webClient.post().uri(execute.toUrl())
                .contentType(MediaType.APPLICATION_JSON)
                .syncBody(Args.builder().args("2333").build())
                .retrieve()
                .bodyToMono(ExecuteRet.class);

        ExecuteRet executeRet = res.block();
        return executeRet.toString();
    }
}
