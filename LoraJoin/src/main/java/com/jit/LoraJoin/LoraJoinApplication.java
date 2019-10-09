package com.jit.LoraJoin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class LoraJoinApplication{

    public static void main(String[] args) {
        SpringApplication.run(LoraJoinApplication.class, args);
    }
}
