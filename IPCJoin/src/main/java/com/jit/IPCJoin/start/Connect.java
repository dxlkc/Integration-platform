package com.jit.IPCJoin.start;

import com.jit.IPCJoin.mqtt.up.UpMqtt;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Connect implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        //连接到mqtt代理服务器
        UpMqtt.getInstance().firstconnect();
    }
}