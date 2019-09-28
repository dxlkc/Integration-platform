package com.jit.LoraJoin.mqtt;

import com.jit.LoraJoin.model.mongodb.device.Device;
import net.sf.json.JSONObject;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

public class PushCallback implements MqttCallback {
    //日志记录器
    private Logger logger = LoggerFactory.getLogger(getClass());

    //influxDB中的measurement是唯一的
    //表固定前缀
    private static final String FIX = "device_";

    //连接断开
    @Override
    public void connectionLost(Throwable throwable) {
        logger.warn("连接断开，可以重连...");
        while (true) {
            try {
                Thread.sleep(2000);
                logger.warn("正在尝试重连...");
                ClientMqtt.getInstance().connect();
            } catch (MqttException e) {
                logger.warn("重连失败...");
                //打印日志
                continue;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
    }

    //发送信息成功时 回调
    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        logger.info("deliveryComplete-----" + iMqttDeliveryToken.isComplete());
    }

    //接收信息成功时 回调
    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        System.out.println(mqttMessage.toString());
//
//
//        //获取当前时间
//        long currentTime = new Date().getTime();
//        String timestr = String.valueOf(currentTime / 1000);
//        Integer time = Integer.valueOf(timestr);
//
//
//        //设备是继电器下的 还是独立的
//        //0为 独立 ； 1为继电器下的
//        Integer type = 0;
//
//        //解析环境数据（还缺 别的数据）
//        String jsonStr = new String(mqttMessage.getPayload());
//        //转换成jsonObject
//        JSONObject jsonObject = JSONObject.fromObject(jsonStr);
//        //第一层获取devEUI和deviceName
//        String devEUI = jsonObject.getString("devEUI");
//        String devName = jsonObject.getString("deviceName");
//        //第一层获取“object”字段的 jsonObject
//        JSONObject jsonObject2 = jsonObject.getJSONObject("object");
//        //存放第二层jsonObject的数据(温度等信息)
//        //这个map  <temperature，Object>
//        Map<String, JSONObject> map = jsonObject2;
//
//
//        //通过socket 发送给客户端
//        CustomSocket customSocket = CustomSocket.getInstance();
//        try {
//            customSocket.SendMsg(jsonStr);
//        } catch (IOException e) {
//            logger.warn("转发失败 : 输出流发生异常！");
//        } catch (Exception e) {
//            logger.warn("转发失败 : 与应用服务器连接断开！");
//        }
//
//
//        //判断数据库中是否有tablename
//        boolean flag1;
//        //判断数据库中 devices_info 是否有该条信息
//        Device device = SaveData.findDeviceInfo(devEUI);
//        boolean hasDeviceinfo = device != null;
//
//        System.out.println(hasDeviceinfo);
//        //没有则添加数据
//        if (!hasDeviceinfo) {
//            device = new Device();
//            device.setDeveui(devEUI);
//            device.setDevname(devName);
//            device.setState(1);
//            device.setLastruntime(time);
//            device.setType(type);
//            device.setReceivecount(0);
//            SaveData.save(device, null);
//        }
//
//
//        for (Entry<String, JSONObject> entry1 : map.entrySet()) {
//            String typeKey = entry1.getKey();
//            Map<String, Object> map1 = entry1.getValue();
//
//            for (Entry<String, Object> entry2 : map1.entrySet()) {
//                //构造表名
//                StringBuilder mName = new StringBuilder();
//                mName.append(FIX)
//                        .append(typeKey)
//                        .append("_")
//                        .append(devEUI);
//
//                //查数据库 是否有表名
//                flag1 = null != SaveData.findByTableName(mName.toString());
//                System.out.println(flag1);
//                //没有表名则添加数据 否则 更新数据
//                if (!flag1) {
//                    System.out.println("------------i am in-------------");
//                    //把表名 和 类型 存入数据库
//                    switch (typeKey) {
//                        case "sendrate":
//                            SaveData.updateSendrate(devEUI, Integer.valueOf(String.valueOf(entry2.getValue())));
//                            break;
//                        case "count":
//                            SaveData.updateCount(devEUI, Integer.valueOf(String.valueOf(entry2.getValue())), device.getReceivecount());
//                            break;
//                        case "power":
//                            SaveData.updatePower(devEUI, Integer.valueOf(String.valueOf(entry2.getValue())));
//                            break;
//                        default:
//                            DeviceType deviceType = new DeviceType();
//                            deviceType.setDevtable(mName.toString());
//                            deviceType.setType(typeKey);
//                            deviceType.setDeveui(devEUI);
//                            deviceType.setValue(String.valueOf(entry2.getValue()));
//                            SaveData.save(null, deviceType);
//                    }
//                } else {
//                    //更新devices_type中的value
//                    SaveData.update(devEUI, mName.toString(), time, String.valueOf(entry2.getValue()), devName);
//                }
//            }
//        }//end for
//    }
//
//    //这个类用于保存数据
//    @Component
//    public static class SaveData {
//        @Autowired
//        private DeviceRepository deviceRepository;
//        @Autowired
//        private DeviceTypeRepository deviceTypeRepository;
//
//        private static SaveData saveData;
//
//        @PostConstruct
//        public void init() {
//            saveData = this;
//            saveData.deviceRepository = this.deviceRepository;
//            saveData.deviceTypeRepository = this.deviceTypeRepository;
//        }
//
//        //保存数据到devices_info and devices_type
//        private static void save(Device device, DeviceType deviceType) {
//            if (device != null) {
//                saveData.deviceRepository.save(device);
//            }
//            if (deviceType != null) {
//                saveData.deviceTypeRepository.save(deviceType);
//            }
//        }
//
//        //更新devices_info 表的 lastruntime and devname
//        //更新devices_info 表的 value
//        private static void update(String deveui, String tablename, Integer time, String value, String devname) {
//            saveData.deviceRepository.updateLastruntimeAndValueAndDevname(deveui, time, devname);
//            saveData.deviceTypeRepository.updateValue(tablename, value);
//        }
//
//        //查devices_type表
//        private static DeviceType findByTableName(String tablename) {
//            return saveData.deviceTypeRepository.findByDevtable(tablename);
//        }
//
//        //查devices_info表
//        private static Device findDeviceInfo(String deveui) {
//            return saveData.deviceRepository.findByDeveui(deveui);
//        }
//
//        //更新devices_info 表的 sendrate
//        private static void updateSendrate(String deveui, Integer sendrate) {
//            saveData.deviceRepository.updateSendrate(deveui, sendrate);
//        }
//
//        //更新devices_info 表的 sendcount 和 receivecount
//        private static void updateCount(String deveui, Integer sendcount, Integer receivecount) {
//            //判断 sendcount < receivecount时 receivecount = 1  否则 receivecount++
//            receivecount = receivecount > sendcount ? 1 : receivecount + 1;
//            saveData.deviceRepository.updateCount(deveui, sendcount, receivecount);
//        }
//
//        //更新devices_info 表的 power
//        private static void updatePower(String deveui, Integer power) {
//            saveData.deviceRepository.updatePower(deveui, power);
//        }
    }
}
