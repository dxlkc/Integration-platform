package com.jit.InfluxDBService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class InfluxDBServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InfluxDBServiceApplication.class, args);
	}
}
