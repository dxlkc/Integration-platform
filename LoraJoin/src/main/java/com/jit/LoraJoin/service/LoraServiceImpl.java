package com.jit.LoraJoin.service;

import com.jit.LoraJoin.feignclient.influxservice.InfluxDao;
import com.jit.LoraJoin.feignclient.mongoservice.MongoDao;
import com.jit.LoraJoin.model.LoraData;
import com.jit.LoraJoin.model.mongodb.device.Device;
import com.jit.LoraJoin.model.mongodb.sensor.Sensor;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.tomcat.util.buf.HexUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoraServiceImpl implements LoraService {
    @Resource
    private InfluxDao influxDao;
    @Resource
    private MongoDao mongoDao;

    public static int byteArrayToInt(byte[] bytes) {
        int value = 0;
        // 由高位到低位
        for (int i = 0; i < 4; i++) {
            int shift = (4 - 1 - i) * 8;
            value += (bytes[i] & 0x000000FF) << shift;// 往高位游
        }
        return value;
    }

    @Override
    public void doSeeed(String devEUI, String base64String) {
        byte[] data = Base64.decodeBase64(base64String);
        System.out.println(HexUtils.toHexString(data));

        LoraData loraData = LoraData.getInstance();

        //开始解析
        int dataLength = data.length;
        int typeNum = dataLength / 7;

        for (int i = 0; i < typeNum; i++) {
            //解析 类型和值
            byte typeArray[] = new byte[2];
            typeArray[0] = data[2 + i * 7];
            typeArray[1] = data[1 + i * 7];
            String type0 = HexUtils.toHexString(typeArray);
            System.out.println("type = " + type0);

            String type = loraData.get(type0);
            if (null == type) {
                return;
            }

            byte valueArray[] = new byte[4];
            valueArray[0] = data[6 + i * 7];
            valueArray[1] = data[5 + i * 7];
            valueArray[2] = data[4 + i * 7];
            valueArray[3] = data[3 + i * 7];
            Float value = (float) (byteArrayToInt(valueArray)) / 1000.0f;
            System.out.println("value = " + value.toString());

            //数据存入influxdb
            updateInfluxDB(devEUI + "_" + type, value);

            //终端 and 传感器模型存入mongodb
            updateMongoDB(devEUI, type, value);
        }
        System.out.println("----解析结束----");
    }

    @Override
    public void doConsum(String devEUI, JSONObject jsonObject) {
        //存放第二层jsonObject的数据(温度等信息)
        //这个map  <temperature，Object>
        Map<String, JSONObject> map = jsonObject;

        for (Map.Entry<String, JSONObject> entry1 : map.entrySet()) {
            String type = entry1.getKey();
            Map<String, Object> map1 = entry1.getValue();

            for (Map.Entry<String, Object> entry2 : map1.entrySet()) {
                //构造表名
                String measurement = devEUI + "_" + type;

                //把表名 和 类型 存入数据库
                switch (type) {
                    case "sendrate":
                        break;
                    case "count":
                        break;
                    case "power":
                        break;
                    default:
                        //添加数据
                        Object value = entry2.getValue();
                        updateInfluxDB(devEUI + "_" + type, value);
                        //修改mongodb
                        updateMongoDB(devEUI, type, value);
                }
            }
        }
    }

    //添加新值
    private void updateInfluxDB(String measurement, Object value) {
        Map<String, String> tag = new HashMap<>();
        Map<String, Object> field = new HashMap<>();
        field.put("value", value);

        JSONObject tags = JSONObject.fromObject(tag);
        JSONObject fields = JSONObject.fromObject(field);
        influxDao.insert(measurement, tags.toString(), fields.toString());
    }

    //添加或修改 Device 和 Sensor
    private void updateMongoDB(String devEUI, String type, Object value) {
        //获取当前时间
        long currentTime = new Date().getTime();
        String timestr = String.valueOf(currentTime / 1000);

        if (null == mongoDao.findDeviceById(devEUI)) {
            //存device
            Device device = Device.builder()
                    .deviceId(devEUI)
                    .state(1)
                    .joinType("lora")
                    .lastRunTime(timestr)
                    .build();
            Device dRes = mongoDao.save(device);
            if (null == dRes) {
                System.out.println("Device save error");
            }
        }

        if (null == mongoDao.findSensorByDeviceIdAndType(devEUI, type)) {
            Sensor sensor = Sensor.builder()
                    .sensorType(type)
                    .deviceId(devEUI)
                    .state(1)
                    .value(value.toString())
                    .build();
            Sensor sRes = mongoDao.save(sensor);
            if (null == sRes) {
                System.out.println("Sensor save error");
            }
        } else {
            mongoDao.updateState(devEUI, 1);
            mongoDao.updateTime(devEUI, timestr);
            mongoDao.updateValue(devEUI, type, value.toString());
        }
    }
}
