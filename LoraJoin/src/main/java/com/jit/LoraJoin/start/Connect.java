package com.jit.LoraJoin.start;

import com.jit.LoraJoin.mqtt.ClientMqtt;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Connect implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        //连接到mqtt代理服务器
        ClientMqtt.getInstance().firstconnect();
    }
}