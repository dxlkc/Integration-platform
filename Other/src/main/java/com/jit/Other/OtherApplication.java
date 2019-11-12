package com.jit.Other;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.ArrayList;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class OtherApplication {

	public static void main(String[] args) {
		SpringApplication.run(OtherApplication.class, args);
	}

}
