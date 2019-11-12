package com.jit.Other.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jit.Other.dao.dataservice.DataDao;
import com.jit.Other.model.DeviceModel.Device;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.log4j.Log4j2;

import javax.annotation.Resource;
import java.util.*;

@RequestMapping("/influx")
@RestController
@CrossOrigin
@Log4j2
public class influxdbController {


    @Resource
    public DataDao mongoDao;


    //查看历史数据
    @RequestMapping("/query_info")
    public String query_information(@RequestParam("start") String startTime,
                                    @RequestParam("end") String endTime,
                                    @RequestParam("deviceId") String device_id) {

        log.info("look for history data information");
        Device device = mongoDao.findOneDeviceInfo(device_id);
        if (device == null) {
            log.warn("look for history information but found nothing");
        }
        ArrayList arrayList = new ArrayList();
        arrayList = (ArrayList) device.getSensorList();
        Map map = new HashMap();
        for (int i = 0; i < arrayList.size(); i++) {
//            System.out.println(arrayList.get(i));
            map.put(arrayList.get(i), mongoDao.history_data(startTime, endTime, device_id, (String) arrayList.get(i)));
            //          map.put(arrayList.get(i),influxDao.history_data("2019-09-26 09:30:30", "2019-09-28 09:30:30", "545180995", "temperature"));
        }

        System.out.println(map);
        JSONObject JSONObj = JSONObject.parseObject(JSON.toJSONString(map));
        return JSONObj.toJSONString();
//        return mongoDao.findDeviceById("545180995").toString();
    }


}
