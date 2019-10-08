package com.jit.LoraJoin.mqtt;

import com.jit.LoraJoin.model.mongodb.device.Device;
import com.jit.LoraJoin.util.MyThreadPoolExecutor;
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
//    private static final String FIX = "device_";

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
        byte[] data = mqttMessage.toString().getBytes();
        for (byte value: data) {
            System.out.print(value + " ");
        }

        MyThreadPoolExecutor.getInstance().getMyThreadPoolExecutor().execute(new Runnable() {
            @Override
            public void run() {
                //数据包解析


            }
        });

    }

}
