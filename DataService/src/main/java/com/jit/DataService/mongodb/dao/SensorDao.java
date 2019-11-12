package com.jit.DataService.mongodb.dao;


import com.jit.DataService.mongodb.model.SensorModel.Sensor;

import java.util.List;

public interface SensorDao {

    /****************查找********************/

    Sensor findOne(String deviceId, String sensorType);

    List<Sensor> findAllByDeviceId(String deviceId);

    /****************添加********************/

    Sensor add(Sensor sensor);

    /****************更新********************/

    long updateThreshold(String deviceId, String sensorType, String max, String min);

    long updateValue(String deviceId, String sensorType, String value);

    long updateState(String deviceId, String sensorType, Integer state);

    /****************删除********************/

    long deleteAllByDeviceId(String deviceId);

    long deleteOne(String deviceId, String sensorType);


}
