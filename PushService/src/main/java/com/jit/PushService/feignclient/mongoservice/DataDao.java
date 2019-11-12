package com.jit.PushService.feignclient.mongoservice;


import com.jit.PushService.Entity.mongodb.device.Device;
import com.jit.PushService.Entity.mongodb.sensor.Sensor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("service-data")
public interface DataDao {
    @PostMapping(value = "/influxdb/data")
    void insert(@RequestParam String measurement,
                @RequestParam String tags,
                @RequestParam String fields);

    @PostMapping(value = "/influxdb/measurement")
    String deleteMeasurement(@RequestParam String measurement);

    /*************************** Device *********************************/
    //存入新的设备
    @PostMapping(value = "/device/add")
    Device save(@RequestBody Device device);

    //查找某个设备
    @PostMapping(value = "/device/find/one")
    Device findDeviceById(@RequestParam String deviceId);

    //修改设备的上下线状态
    @PostMapping(value = "/device/update/state")
    long updateState(@RequestParam String deviceId, @RequestParam Integer state);

    //更新某个设备的最后上报时间
    @PostMapping(value = "/device/update/lastRunTime")
    long updateTime(@RequestParam String deviceId, @RequestParam String lastRunTime);

    //获取某个设备的所有传感器列表
    @PostMapping(value = "/device/find/allType")
    List<String> findAllType(@RequestParam String deviceId);

    /************************* Sensor **************************/
    //存入新的传感器
    @PostMapping("/sensor/add")
    Sensor save(@RequestBody Sensor sensor);

    //查找某个传感器
    @PostMapping("/sensor/find/one")
    Sensor findSensorByDeviceIdAndType(@RequestParam String deviceId, @RequestParam String sensorType);

    //更新某个传感器的值
    @PostMapping("/sensor/update/value")
    long updateValue(@RequestParam String deviceId, @RequestParam String sensorType, @RequestParam String value);
}
