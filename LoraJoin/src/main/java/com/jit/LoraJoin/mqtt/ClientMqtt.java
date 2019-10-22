package com.jit.LoraJoin.mqtt;

import com.jit.LoraJoin.config.LoraTopicConfig;
import com.jit.LoraJoin.config.MqttConfig;
import com.jit.LoraJoin.util.UID;
import lombok.extern.log4j.Log4j2;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Log4j2
public class ClientMqtt {

    private final String HOST = Config.getHost();
    private static String clientid;
    private MqttClient client;

    private static ClientMqtt clientMqtt = null;

    private ClientMqtt(){}
    public static ClientMqtt getInstance() {
        if (null == clientMqtt){
            clientMqtt = new ClientMqtt();
        }
        clientid = UID.getUid();
        return clientMqtt;
    }

    //选项
    private MqttConnectOptions getOptions() {
        MqttConnectOptions options = new MqttConnectOptions();

        // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，设置为true表示每次连接到服务器都以新的身份连接
        options.setCleanSession(false);
        // 设置超时时间 单位为秒
        options.setConnectionTimeout(50);
        // 设置会话心跳时间 单位为秒 服务器会每隔1.5*200秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
        options.setKeepAliveInterval(12);
        return options;
    }

    //连接并订阅
    protected void connect() throws MqttException {
        MqttConnectOptions options = getOptions();

        //设置主机名，客户端id，MemoryPersistence设置clientid的保存形式，默认为以内存保存
        client = new MqttClient(HOST, clientid, new MemoryPersistence());
        //设置回调函数
        client.setCallback(new PushCallback());

        if (!client.isConnected()) {
            client.connect(options);
            log.debug("mqtt连接成功");
        } else {
            log.debug("mqtt已连接");
        }

        //获取订阅主题
        int length = Config.getTopics().size();
        int i = 0;

        String[] topics = new String[length];
        for (String topic : Config.getTopics()) {
            topics[i++] = topic;
        }

        int[] qos = new int[length];
        for (i = 0; i < length; i++){
            qos[i] = 1;
        }

        //订阅
        client.subscribe(topics, qos);
    }

    //发布信息
    public void publish(String topic, String message) {
        try {
            client.publish(topic, new MqttMessage(message.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //服务器启动后  第一次连接时使用
    public void firstconnect() {
        while (true) {
            try {
                Thread.sleep(2000);
                connect();
            } catch (MqttException e) {
                log.warn("无法连接MQTT代理服务器...");
                continue;
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            return;
        }
    }

    @Component
    private static class Config{
        @Autowired
        private MqttConfig mqttConfig;
        @Autowired
        private LoraTopicConfig loraTopicConfig;

        private static Config config;

        @PostConstruct
        public void init() {
            config = this;
            config.mqttConfig = this.mqttConfig;
        }

        public static String getHost(){
            return config.mqttConfig.getHost();
        }

        public static List<String> getTopics(){
            return config.loraTopicConfig.getTopics();
        }

    }
}
