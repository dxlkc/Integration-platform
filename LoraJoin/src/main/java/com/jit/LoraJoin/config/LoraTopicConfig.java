package com.jit.LoraJoin.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@PropertySource(value = "classpath:config/loratopic.properties")
@ConfigurationProperties(prefix = "lora")
@Data
public class LoraTopicConfig {

    private List<String> topics = new ArrayList<String>();

}
