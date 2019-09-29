package com.jit.NBJoin.feignclient.mongoservice;

import com.jit.NBJoin.model.mongodb.device.Device;
import com.jit.NBJoin.model.mongodb.sensor.Sensor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("service-mongodb")
public interface MongoDao {

    /*************************** Device *********************************/
    //存入新的设备
    @PostMapping(value = "/device/add")
    Device saveDevice(@RequestBody Device device);

    //查找某个设备
    @PostMapping(value = "/device/find/one")
    Device findDeviceById(@RequestParam String deviceId);

    //修改设备的上下线状态
    @PostMapping(value = "/device/update/state")
    long updateDeviceState(@RequestParam String deviceId, @RequestParam Integer state);

    //更新某个设备的最后上报时间
    @PostMapping(value = "/device/update/lastRunTime")
    long updateDeviceTime(@RequestParam String deviceId, @RequestParam String lastRunTime);

    /************************* Sensor **************************/
    //存入新的传感器
    @PostMapping("/sensor/add")
    Sensor saveSensor(@RequestBody Sensor sensor);

    //查找某个传感器
    @PostMapping("/sensor/find/one")
    Sensor findSensorByDeviceIdAndType(@RequestParam String deviceId, @RequestParam String sensorType);

    //更新某个传感器的值
    @PostMapping("/sensor/update/value")
    long updateSensorValue(@RequestParam String deviceId, @RequestParam String sensorType, @RequestParam String value);
}
