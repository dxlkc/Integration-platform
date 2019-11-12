package com.jit.LoraJoin.mqtt;


import com.jit.LoraJoin.service.LoraService;
import com.jit.LoraJoin.util.MyThreadPoolExecutor;
import lombok.extern.log4j.Log4j2;
import net.sf.json.JSONObject;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Log4j2
public class PushCallback implements MqttCallback {

    //连接断开
    @Override
    public void connectionLost(Throwable throwable) {
        log.warn("连接断开，可以重连...");
        while (true) {
            try {
                Thread.sleep(2000);
                log.warn("正在尝试重连...");
                ClientMqtt.getInstance().connect();
            } catch (MqttException e) {
                log.warn("重连失败...");
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
        log.info("deliveryComplete-----" + iMqttDeliveryToken.isComplete());
    }

    //接收信息成功时 回调
    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        log.info(mqttMessage.toString());

        MyThreadPoolExecutor.getInstance().getMyThreadPoolExecutor().execute(new Runnable() {
            @Override
            public void run() {
                //数据包解析
                String jsonStr = new String(mqttMessage.getPayload());
                JSONObject jsonObject = JSONObject.fromObject(jsonStr);

                //第一层获取devEUI和deviceName
                String devEUI = jsonObject.getString("devEUI");
                String devName = jsonObject.getString("deviceName");

                //第一层获取“object”字段的 jsonObject
                JSONObject jsonObject2 = jsonObject.getJSONObject("object");

                if (jsonObject2.isNullObject()) {
                    //seeed处理
                    log.info("-----doSeeed-----");
                    String data = jsonObject.getString("data");
                    if (!"".equals(data)){
                        Service.service.doSeeed(devEUI,data);
                    }
                } else {
                    //原生lora处理
                    log.info("-----doConsum-----");
                    Service.service.doConsum(devEUI,jsonObject2);
                }
            }
        });

    }

    @Component
    private static class Service {
        @Autowired
        private LoraService loraService;

        private static Service service;

        @PostConstruct
        public void init() {
            service = this;
            service.loraService = this.loraService;
        }

        public void doSeeed(String eui, String data) {
            loraService.doSeeed(eui, data);
        }

        public void doConsum(String eui, JSONObject jsonObject) {
            loraService.doConsum(eui, jsonObject);
        }

    }
}
