package com.jit.LoraJoin.feignclient.influxservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("service-influxdb")
public interface InfluxDao {

    @PostMapping(value = "/influxdb/data")
    void insert(@RequestParam String measurement,
                @RequestParam String tags,
                @RequestParam String fields);

    @PostMapping(value = "/influxdb/measurement")
    String deleteMeasurement(@RequestParam String measurement);
}
