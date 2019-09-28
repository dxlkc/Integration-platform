package com.jit.IPCJoin.feignclient.mongoservice;

import com.jit.IPCJoin.model.mongodb.device.Device;
import com.jit.IPCJoin.model.mongodb.sensor.Sensor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("service-mongodb")
public interface MongoDao {

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
