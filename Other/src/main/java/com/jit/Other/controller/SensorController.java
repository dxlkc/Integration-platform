package com.jit.Other.controller;

import com.jit.Other.dao.dataservice.DataDao;
import com.jit.Other.model.SensorModel.Sensor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.log4j.Log4j2;
import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/sensor")
@RestController
@CrossOrigin
@Log4j2
public class SensorController {

    @Resource
    DataDao mongoDao;


    //查看某个设备的所有传感器的信息
    @RequestMapping("/findAllSensorInfo")
    public List<Sensor> findAllSensorInfo(@RequestParam("deviceId") String deviceId){
        log.info("find all sensor info");
        return mongoDao.findAllSensorInfo(deviceId);
    }


    //查看某个设备的某个传感器的信息
    @RequestMapping("/findOneSensorInfo")
    public  Sensor findOneSensorInfo(@RequestParam("deviceId") String deviceId,
                                     @RequestParam("sensorType") String sensorType){
        log.info("find "+ sensorType+"sensor information");
        return mongoDao.findOneSensorInfo(deviceId,sensorType);
    }
//----------------------------------
    //添加传感器
    @RequestMapping("/addSensor")
    public  Sensor addSensor(@RequestParam("SensorType") Sensor sensor){
        log.info("new sensor"+sensor+"has been added");
        return mongoDao.addSensor(sensor);
    }
//
//--------------------------------
////////////////////已删除
//    //更新传感器名称
    @RequestMapping("/updateSensorName")
    public String updateSensorName(@RequestParam("deviceId")String deviceId,
                                   @RequestParam("sensorType")String sensorType,
                                   @RequestParam("sensorName")String sensorName){
        log.info("update" + sensorType+"name to"+sensorName);
        return mongoDao.updateSensorName(deviceId,sensorType,sensorName);
    }




    //更新传感器阈值
    @RequestMapping("/updateSensorThreshold")
    public  long updateSensorThreshold(@RequestParam("deviceId")String deviceId,
                                         @RequestParam("sensorType")String sensorType,
                                         @RequestParam("max")String max,
                                         @RequestParam("min")String min){
        log.info("update "+ sensorType+ "  threshold" );
        return mongoDao.updateSensorThreshold(deviceId,sensorType,max,min);
    }

    //更新传感器当前数据
    @RequestMapping("/updateSensorValue")
    public  long updateSensorValue(@RequestParam("deviceId")String deviceId,
                                     @RequestParam("sensorType")String sensorType,
                                     @RequestParam("value")String value){
        log.info("update " + deviceId+ "value");
        return mongoDao.updateSensorValue(deviceId,sensorType,value);
    }


    //更新传感器状态
    @RequestMapping("/updateSensorState")
    public  long updateSensorState(@RequestParam("deviceId")String deviceId,
                                     @RequestParam("sensorType")String sensorType,
                                     @RequestParam("state")String state){

        log.info("update sensor state");
        return mongoDao.updateSensorState(deviceId,sensorType,state);
    }

    //删除一个传感器
    @RequestMapping("/deleteOneSensor")
    public long deleteOneSensor(@RequestParam("deviceId")String deviceId,
                                  @RequestParam("sensorType")String sensorType){

        log.info("delete "+sensorType);
        return mongoDao.deleteOneSensor(deviceId,sensorType);
    }


//    //删除一个设备的所有传感器
//    @RequestMapping("/findOneSensorInfo")
//    public String delete_all_sensor(@RequestParam("deviceId")String deviceId){
//
//        return "3243";
//    }

}





