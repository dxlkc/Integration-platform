package com.jit.DataService.mongodb.controller;

import com.jit.DataService.mongodb.dao.DeviceDao;
import com.jit.DataService.mongodb.dao.SensorDao;
import com.jit.DataService.mongodb.model.DeviceModel.Device;
import com.jit.DataService.mongodb.model.DeviceModel.Location;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Log4j2
@CrossOrigin
@RestController
@RequestMapping("/device")
public class DeviceController {

    @Resource
    private DeviceDao deviceDao;

    @Resource
    private SensorDao sensorDao;

    /***************查询********************/

    //查询所有设备信息
    @PostMapping(value = "/find/all")
    public List<Device> findAllDevice() {
        return deviceDao.findAllDevice();
    }

    //查询一个设备信息
    @PostMapping(value = "/find/one")
    public Device findByDeviceId(@RequestParam String deviceId) {
        return deviceDao.findByDeviceId(deviceId);
    }

    //查询设备imei
    @PostMapping(value = "/get/imei")
    public String getImeiByDeviceId(@RequestParam String deviceId) {
        return deviceDao.getImeiByDeviceId(deviceId);
    }

    //查询设备传感器列表
    @PostMapping(value = "/find/allType")
    public List<String> getSensorListByDeviceId(@RequestParam String deviceId) {
        return deviceDao.getSensorListByDeviceId(deviceId);
    }


    /****************更新********************/

    //更改设备名
    @PostMapping(value = "/update/deviceName")
    public long updateDeviceName(@RequestParam String deviceId, @RequestParam String deviceName) {
        return deviceDao.updateDeviceName(deviceId, deviceName);
    }

    //更新设备开关状态
    @PostMapping(value = "/update/state")
    public long updateLinkState(@RequestParam String deviceId, @RequestParam Integer state) {
        return deviceDao.updateState(deviceId, state);
    }

    //更改设备地址
    @PostMapping(value = "/update/location")
    public long updateLocationDetail(@RequestParam String deviceId, @RequestParam String province,
            @RequestParam String city,@RequestParam String area) {
        return deviceDao.updateLocation(deviceId, new Location(province, city, area));
    }

    //更新运行时间
    @PostMapping(value = "/update/lastRunTime")
    public long updateLastRunTime(@RequestParam String deviceId, @RequestParam String lastRunTime) {
        return deviceDao.updateLastRunTime(deviceId, lastRunTime);
    }

    //更新设备备注
    @PostMapping(value = "/update/discription")
    public long updateDiscription(@RequestParam String deviceId, @RequestParam String discription) {
        return deviceDao.updateDiscription(deviceId, discription);
    }

    //修改设备imei
    @PostMapping(value = "/update/imei")
    public long updateImeiByDeviceId(@RequestParam String deviceId, @RequestParam String imei) {
        return deviceDao.updateImeiByDeviceId(deviceId, imei);
    }

    /****************删除********************/

    //删除设备
    @PostMapping(value = "/delete/device")
    public long deleteByDeviceId(@RequestParam String deviceId) {
        sensorDao.deleteAllByDeviceId(deviceId);
        return deviceDao.deleteByDeviceId(deviceId);
    }

    /****************添加********************/
    //添加设备
    @PostMapping(value = "/add")
    public Device addDevice(@RequestBody Device device) {
        return deviceDao.addDevice(device);
    }

}
