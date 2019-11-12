package com.jit.PushService.listener;

import com.jit.PushService.Entity.MqData;
import net.sf.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@RestController
@Component
public class MqListener {

    @PostMapping(value = "/pushdata")
    @RabbitListener(queues = "oneNet.queue")
    public void handler(@RequestParam(value = "msg") String msg) {
        System.out.println(msg);

        JSONObject jsonObject = JSONObject.fromObject(msg);
        MqData mqData= (MqData) JSONObject.toBean(jsonObject,MqData.class);

        CopyOnWriteArraySet<WebSocketService> webSocketSet =  WebSocketService.webSocketSet;

        //遍历客户set，找到正在访问这个device的实时数据的用户，并发送消息给他
        for(WebSocketService item: webSocketSet){
            if(item.devId!=null && item.devId.equals(mqData.getDevice_id())) {
                try {
                    item.session.getBasicRemote().sendText(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }
            }
        }
    }
}
