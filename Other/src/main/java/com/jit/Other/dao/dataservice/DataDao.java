package com.jit.Other.dao.dataservice;

import com.alibaba.fastjson.JSONObject;
import com.jit.Other.model.DeviceModel.Device;
import com.jit.Other.model.SensorModel.Sensor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("service-data")
public interface DataDao {
    //-----influxdb-------
    @PostMapping(value = "/influxdb/history")
    public String history_data(@RequestParam("start") String startTime,
                               @RequestParam("end") String endTime,
                               @RequestParam("deviceId") String device_id,
                               @RequestParam("type") String type);

    //-----mongodb-------
//------------------------------------------device
    //查看某个设备信息
    @PostMapping(value = "/device/find/one")
    Device findOneDeviceInfo(@RequestParam("deviceId") String deviceId);

    @PostMapping(value = "/device/find/allType")
    List<String> findAllType(@RequestParam("deviceId") String deviceId);
//-查看某个用户的所有设备
    @PostMapping(value = "/device/find/all")
    List<Device> findAllDevice();

//    删除某一个device下的某个sensor
    @PostMapping(value = "/device/delete/device")
    String deleteDevice(@RequestParam("deviceId") String  deviceId);

    //添加一个device
    @PostMapping(value = "/device/add")
    JSONObject addDevice(@RequestParam("Device") String Device);

    //修改一个device的名字
    @PostMapping(value = "/device/update/deviceName")
    long changeDeviceName(@RequestParam("deviceId") String deviceId,
                            @RequestParam("deviceName") String deviceName);
    //修改设备链接状态
    @PostMapping(value = "/device/update/state")
    long changeConnectState(@RequestParam("deviceId") String deviceId,
                            @RequestParam("state") String state);

    //修改设备位置
    @PostMapping(value = "/device/update/location")
    long changeDeviceLocation(@RequestParam("deviceId") String deviceId,
                              @RequestParam("province") String province,
                              @RequestParam("city")String city,
                              @RequestParam("area")String area);

    //修改设备上次运行时间
    @PostMapping(value = "/device/update/lastRunTime")
    long changeDeviceLastRunTime(@RequestParam("deviceId") String deviceId,
                                 @RequestParam("lastRunTime") String lastRunTime);

    //修改设备备注
    @PostMapping(value = "/device/update/discription")
    long changeDeviceDiscription(@RequestParam("deviceId")String deviceId,
                                 @RequestParam("discription")String discription);


    //查看设备Imei
    @PostMapping(value ="/device/get/imei")
     String getImei(@RequestParam("deviceId")String deviceId);

    //修改设备Imei
    @PostMapping(value = "device/update/imei")
    long changeImei(@RequestParam("deviceId")String deviceId,
                    @RequestParam("imei")String imei);

//------------------------------------------------------------sensor



    //查看某个设备所有传感器信息
    @PostMapping(value = "/sensor/find/all")
    List<Sensor> findAllSensorInfo(@RequestParam("deviceId") String deviceId);

    //查看某个设备的某个传感器的信息
    @PostMapping("/sensor/find/one")
      Sensor findOneSensorInfo(@RequestParam("deviceId") String deviceId,
                                     @RequestParam("sensorType") String sensorType);

    //添加传感器
    @PostMapping("/sensor/add")
     Sensor addSensor(@RequestParam("Sensor") Sensor Sensor);

    //更新传感器名称
    @PostMapping("/sensor/update/name")
     String  updateSensorName(@RequestParam("deviceId")String deviceId,
                                   @RequestParam("sensorType")String sensorType,
                                   @RequestParam("sensorName")String sensorName);

    //更新传感器阈值
    @PostMapping("/sensor/update/threshold")
      long updateSensorThreshold(@RequestParam("deviceId")String deviceId,
                                         @RequestParam("sensorType")String sensorType,
                                         @RequestParam("max")String max,
                                         @RequestParam("min")String min);

    //更新传感器当前数据
    @PostMapping("/sensor/update/value")
      long updateSensorValue(@RequestParam("deviceId")String deviceId,
                                     @RequestParam("sensorType")String sensorType,
                                     @RequestParam("value")String value);


    //更新传感器状态
    @PostMapping("/sensor/update/state")
    long updateSensorState(@RequestParam("deviceId")String deviceId,
                                     @RequestParam("sensorType")String sensorType,
                                     @RequestParam("state")String state);

    //删除一个传感器
    @PostMapping("/sensor/delete/one")
     long deleteOneSensor(@RequestParam("deviceId")String deviceId,
                                  @RequestParam("sensorType")String sensorType);


    //删除一个设备的所有传感器
    @PostMapping("/sensor/delete/all")
    public String deleteAllSensor(@RequestParam("deviceId")String deviceId);
}