package com.jit.Other.controller;


import com.jit.Other.dao.MysqlDao.DeviceDao;
import com.jit.Other.dao.dataservice.DataDao;
import com.jit.Other.model.DeviceModel.Device;
import com.jit.Other.service.MysqlServiceInterface;
import org.springframework.web.bind.annotation.*;
import lombok.extern.log4j.Log4j2;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/device")
@RestController
@ResponseBody
@CrossOrigin
@Log4j2
public class deviceController {

    @Resource
    DataDao mongoDao;
    @Resource
    DeviceDao deviceDao;

    @Resource
    MysqlServiceInterface mysqlServiceInterface;
//查看一个用户拥有的device列表
    @RequestMapping("/findUserAllDevice")
    public List findUserAllDevice(@RequestParam("userName") String userName){

        log.info("findUserAllDevice");
        return mysqlServiceInterface.show_all_device(userName);

    }

    @RequestMapping("/findAllType")
    public List<String> findAllType(@RequestParam("deviceId") String deviceId){
        log.info("findAllType");
        return mongoDao.findAllType(deviceId);
    }

    //查看所有设备
    @RequestMapping("/findAllDevice")
    public List findAllDevice(){
       List<Device> a =  mongoDao.findAllDevice();
       if (a == null){
           log.warn("findAllDevice,but found nothing");
       }
       List<String> res = new ArrayList<>();
       for(Device t : a){
           res.add(t.getDeviceId());
       }
       log.info("findAllDevice Success");
       return res;
    }

    @RequestMapping("/lookOneDeviceInfo")
    public  Device lookOneDeviceInfo(@RequestParam("deviceId") String deviceId){
        log.info("lookOneDeviceInfo");
      return mongoDao.findOneDeviceInfo(deviceId);

    }


//删除设备
    @RequestMapping("/deleteDevice")
    public  String deleteDevice(@RequestParam("userName") String userName,
                                @RequestParam("deviceId") String deviceId){
        log.info("deleteOneDevice");
        deviceDao.delete_one_device(userName,deviceId);
        return "ok";
    }
//添加设备
    @RequestMapping("/addDevice")
    public String  addDevice(@RequestParam("deviceId") String deviceId,
                                @RequestParam("userName") String userName){

        System.out.println(deviceDao.judge_exist_device(userName,deviceId));

        if(deviceDao.judge_exist_device(userName,deviceId)==null){
            deviceDao.add_one_device(userName,deviceId);
            log.info("add one device success");
            return "ok";
        }
        log.warn("add One device,but the device is exist");
        return "the device is exist";

    }


    //修改device名字
    @RequestMapping("/changeDeviceName")
    public long changeDeviceName(@RequestParam("deviceId") String deviceId,
                                   @RequestParam("deviceName") String deviceName){
        log.info("change device name");
        return mongoDao.changeDeviceName(deviceId,deviceName);
    }

    //更新链接状态
    @RequestMapping("/changeConnectState")
    public long changeConnectState(@RequestParam("deviceId") String deviceId,
                                   @RequestParam("state") String state){
        log.info("change connect state");
        return mongoDao.changeConnectState(deviceId,state);
    }

    //更新设备位置
    @RequestMapping("/changeDeviceLocation")
    public long changeDeviceLocation(@RequestParam("deviceId") String deviceId,
                                     @RequestParam("province") String province,
                                     @RequestParam("city")String city,
                                     @RequestParam("area")String area){
        log.info("change device location");
        return mongoDao.changeDeviceLocation(deviceId,province,city,area);
        
    }

    //更新设备上次运行时间
    @RequestMapping("/changeDeviceLastRunTime")
    public  long changeDeviceLastRunTime(@RequestParam("deviceId") String deviceId,
                                          @RequestParam("lastRunTime") String lastRunTime){
        log.info("change device last run time");
        return mongoDao.changeDeviceLastRunTime(deviceId,lastRunTime);

    }

    //更新设备备注
    @RequestMapping("/changeDeviceDiscription")
    public long changeDeviceDiscription(@RequestParam("deviceId")String deviceId,
                                        @RequestParam("discription")String discription){
        log.info("change device discription");
        return mongoDao.changeDeviceDiscription(deviceId,discription);
    }

    //查询设备Imei
    @RequestMapping("/getImei")
    public String getImei(@RequestParam("deviceId")String deviceId){
        log.info("get imei");
        return mongoDao.getImei(deviceId);
    }

    //修改设备imei
    @RequestMapping("/changeImei")
    public long  changeImei(@RequestParam("deviceId")String deviceId,
                            @RequestParam("imei")String imei){
        log.info("change imei");
        return  mongoDao.changeImei(deviceId,imei);
    }
}
