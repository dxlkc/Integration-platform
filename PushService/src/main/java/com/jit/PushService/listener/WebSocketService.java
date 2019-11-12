package com.jit.PushService.listener;

import com.jit.PushService.Entity.MqData;
import com.jit.PushService.Entity.mongodb.device.Device;
import com.jit.PushService.Entity.mongodb.sensor.Sensor;
import com.jit.PushService.feignclient.mongoservice.DataDao;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

//和客户端连接
@ServerEndpoint("/websocket")
//在外部tomcat上运行需要注释掉
@Component
public class WebSocketService {
    //日志记录器
    Logger logger = LoggerFactory.getLogger(getClass());
    //存放连接进来的客户端
    public static CopyOnWriteArraySet<WebSocketService> webSocketSet = new CopyOnWriteArraySet<WebSocketService>();
    //连接进来的数量
    public static int onlineCount = 0;
    //通过session给客户端发数据
    public Session session;
    //这个连接对应的要查找的设备id
    public String devId;

    @Autowired
    DataDao dataDao;

    @OnOpen
    public void OnOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);
        addOnlineCount();
        logger.info("websocket:" + " sessionid = " + session.getId() + " 连接成功！人数为：" + getOnlineCount());
    }

    @OnError
    public void OnError(Session session, Throwable error) {
        webSocketSet.remove(this);
        logger.warn("websocket: " + session.getId() + " 连接错误！");
        logger.error(error.toString());
    }

    @OnClose
    public void OnClose(Session session) {
        while (!webSocketSet.remove(this)) {
        }
        subOnlineCount();
        logger.info("websocket: " + session.getId() + " 连接关闭！剩余人数为：" + getOnlineCount());
    }

    @OnMessage
    public synchronized void OnMessage(String msg, Session session) {  //接收eui
        System.out.println(msg);
        Device device = Service.service.dataDao.findDeviceById(msg);
        if (device != null)
            this.devId = msg;
        else {
            System.out.println("websocket wrong devID");
            try {
                session.getBasicRemote().sendText("can not find the device!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            List<String> typeList = Service.service.dataDao.findAllType(msg);
            JSONArray array = JSONArray.fromObject(typeList);
            session.getBasicRemote().sendText(array.toString());

            //找出这个设备所有传感器的当前值并发送
            MqData mqData = new MqData();

            for (String typeName : typeList) {
                Sensor sensor = Service.service.dataDao.findSensorByDeviceIdAndType(devId, typeName);
                mqData.setDevice_id(devId);
                mqData.setType(typeName);
                mqData.setValue(sensor.getValue());
                JSONObject jsonObject = JSONObject.fromObject(mqData);
                session.getBasicRemote().sendText(jsonObject.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //增加人数
    public static synchronized void addOnlineCount() {
        WebSocketService.onlineCount++;
    }

    //减少人数
    public static synchronized void subOnlineCount() {
        WebSocketService.onlineCount--;
    }

    //查看连入人数
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    @Component
    private static class Service {
        @Autowired
        private DataDao dataDao;

        private static Service service;

        @PostConstruct
        public void init() {
            service = this;
            service.dataDao = this.dataDao;
        }
    }
}
