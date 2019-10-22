package com.jit.LoraJoin.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:config/mqtt.properties")
@Log4j2
public class MqttConfig {

    @Value("${mqtt.ip}")
    private String ip;

    @Value("${mqtt.port}")
    private Integer port;

    public String getHost() {
        log.info("mqtt proxy : tcp://" + ip + ":" + port);
        return "tcp://" + ip + ":" + port;
    }
}
