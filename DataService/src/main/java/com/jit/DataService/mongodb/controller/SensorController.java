package com.jit.DataService.mongodb.controller;

import com.jit.DataService.mongodb.dao.DeviceDao;
import com.jit.DataService.mongodb.dao.SensorDao;
import com.jit.DataService.mongodb.model.SensorModel.Sensor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/sensor")
public class SensorController {

    @Resource
    private SensorDao sensorDao;

    @Resource
    private DeviceDao deviceDao;

    /****************查询********************/

    //查询一个设备的一个传感器
    @PostMapping(value = "/find/one")
    public Sensor find(@RequestParam String deviceId, @RequestParam String sensorType) {
        return sensorDao.findOne(deviceId, sensorType);
    }

    //查询一个设备的所有传感器
    @PostMapping(value = "/find/all")
    public List<Sensor> findAll(@RequestParam String deviceId) {
        return sensorDao.findAllByDeviceId(deviceId);
    }


    /****************添加********************/

    //添加传感器
    @PostMapping(value = "/add")
    public Sensor add(@RequestBody Sensor sensor) {
        if(deviceDao.addSensor(sensor.getDeviceId(), sensor.getSensorType()) != 0) {
            return sensorDao.add(sensor);
        }
        return null;
    }

    /****************更新********************/

    //更改阈值
    @PostMapping(value = "/update/threshold")
    public long updateThreshold(@RequestParam String deviceId, @RequestParam String sensorType,
                                @RequestParam String max, @RequestParam String min) {
        return sensorDao.updateThreshold(deviceId, sensorType, max, min);
    }

    //更新最新值
    @PostMapping(value = "/update/value")
    public long updateValue(@RequestParam String deviceId, @RequestParam String sensorType,
                            @RequestParam String value) {
        return sensorDao.updateValue(deviceId, sensorType, value);
    }

    //更新状态（是否异常）
    @PostMapping(value = "/update/state")
    public long updateState(@RequestParam String deviceId, @RequestParam String sensorType,
                            @RequestParam Integer state) {
        return sensorDao.updateState(deviceId, sensorType, state);
    }

    /****************删除********************/

    //删除一个传感器
    @PostMapping(value = "/delete/one")
    public long deleteOne(@RequestParam String deviceId, @RequestParam String sensorType) {
        return deviceDao.deleteSensor(deviceId, sensorType) + sensorDao.deleteOne(deviceId, sensorType);
    }

    //删除一个设备的所有传感器
    @PostMapping(value = "/delete/all")
    public long deleteAll(@RequestParam String deviceId) {
        sensorDao.deleteAllByDeviceId(deviceId);
        return deviceDao.deleteAllSensor(deviceId);
    }
}
