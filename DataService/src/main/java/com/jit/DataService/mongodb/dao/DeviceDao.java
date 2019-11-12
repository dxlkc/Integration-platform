package com.jit.DataService.mongodb.dao;

import com.jit.DataService.mongodb.model.DeviceModel.Device;
import com.jit.DataService.mongodb.model.DeviceModel.Location;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface DeviceDao {

    /****************查找********************/

    List<Device> findAllDevice();

    Device findByDeviceId(String deviceId);

    String getImeiByDeviceId(String deviceId);

    List<String> getSensorListByDeviceId(String deviceId);

    /****************更新********************/

    long updateState(String deviceId, Integer state);

    long updateLocation(@NotNull String deviceId, Location location);

    long updateLastRunTime(String deviceId, String lastRunTime);

    long updateDeviceName(String deviceId, String deviceName);

    long updateDiscription(String deviceId, String discription);

    long updateImeiByDeviceId(String deviceId, String imei);

    long addSensor(String deviceId, String sensorName);

    long deleteSensor(String deviceId, String sensorType);

    long deleteAllSensor(String deviceId);

    /****************删除********************/

    long deleteByDeviceId(String deviceId);

    /****************添加********************/

    Device addDevice(Device device);

    Device addByDeviceId(String deviceId);


}
