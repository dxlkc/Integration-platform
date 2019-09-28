package com.jit.InfluxDBService.controller;

import com.jit.InfluxDBService.InfluxdbDao.InfluxdbDao;
import com.jit.InfluxDBService.model.HistoryData;
import com.jit.InfluxDBService.util.JsonUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@RestController
public class InfluxController {

    @Autowired
    private InfluxdbDao influxdbDao;

    //添加信息  tags可以为null
    @PostMapping(value = "/influxdb/data")
    public void insert(@RequestParam("measurement") String measurement, @RequestParam("tags") String tags,
        @RequestParam("fields") String fields) {
        influxdbDao.insert(measurement,
                JsonUtil.JasonObjectToMap(new HashMap<String, String>(), JSONObject.fromObject(tags)),
                JsonUtil.JasonObjectToMap(new HashMap<String, Object>(), JSONObject.fromObject(fields)));
    }

    //删除表 ok
    @PostMapping(value = "/influxdb/measurement")
    public String deleteMeasurement(@RequestParam("measurement") String measurement) {
        return influxdbDao.deleteMeasurement(measurement);
    }

    //查询历史数据
    @PostMapping(value = "/influxdb/history")
    public String selectHistory(@RequestParam("start") String startTime, @RequestParam("end") String endTime,
                                @RequestParam("deviceId") String deviceId, @RequestParam("type") String type){

        String measurement = deviceId + "_" + type;
        ArrayList<HistoryData> res = influxdbDao.findByTime(startTime,endTime,measurement);

        return JSONArray.fromObject(res).toString();
    }
}
