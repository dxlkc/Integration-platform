package com.jit.DataService.influxdb.config;

import lombok.extern.log4j.Log4j2;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:config/influxdb.properties")
@Log4j2
public class InfluxdbConfig {

    @Value("${influxdb.url}")
    private String url;

    @Value("${influxdb.port}")
    private String port;

    @Value("${influxdb.username}")
    private String username;

    @Value("${influxdb.password}")
    private String password;

    @Value("${influxdb.db}")
    private String dbName;

    @Bean(name = "influxdb")
    public InfluxDB InfluxdbConnect() {
        log.debug("influxdb : connectting to "+"http://"+url+":"+port);
        InfluxDB influxDB = InfluxDBFactory.connect("http://"+url+":"+port, username, password);
        log.info("influxdb : connect success");
        return influxDB;
    }

    public String getDbName(){
        return dbName;
    }
}
