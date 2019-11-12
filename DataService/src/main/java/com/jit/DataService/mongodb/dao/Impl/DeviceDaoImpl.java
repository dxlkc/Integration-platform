package com.jit.DataService.mongodb.dao.Impl;

import com.jit.DataService.mongodb.dao.DeviceDao;
import com.jit.DataService.mongodb.model.DeviceModel.Device;
import com.jit.DataService.mongodb.model.DeviceModel.JoinInfo;
import com.jit.DataService.mongodb.model.DeviceModel.Location;
import com.jit.DataService.mongodb.model.SensorModel.Sensor;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicUpdate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Component
public class DeviceDaoImpl implements DeviceDao {
    @Resource
    private MongoTemplate mongoTemplate;

    //查询所有设备信息
    @Override
    public List<Device> findAllDevice() {
        return mongoTemplate.findAll(Device.class);
    }

    //查询单个设备信息
    @Override
    public Device findByDeviceId(String deviceId) {
        Query query = new Query(Criteria.where("deviceId").is(deviceId));
        return mongoTemplate.findOne(query, Device.class);
    }

    @Override
    public String getImeiByDeviceId(String deviceId){
        Query query = new Query(Criteria.where("deviceId").is(deviceId));
        return mongoTemplate.findOne(query, Device.class).getJoinInfo().getImei();
    }

    @Override
    public List<String> getSensorListByDeviceId(String deviceId) {
        Query query = new Query(Criteria.where("deviceId").is(deviceId));
        return mongoTemplate.findOne(query, Device.class).getSensorList();
    }

    /*********************************************更新信息部分*************************************************************/

    //更新连接状态
    @Override
    public long updateState(String deviceId, Integer state) {
        log.info("更新连接状态  " + deviceId + ":" + state);
        Query query = new Query(Criteria.where("deviceId").is(deviceId));
        Update update = new Update();
        update.set("state", state);

        UpdateResult result = mongoTemplate.updateMulti(query, update, Device.class);
        return result.getModifiedCount();
    }


    //更新位置
    @Override
    public long updateLocation(@NotNull String deviceId, Location location) {
        log.info("更新位置  " + deviceId);
        Query query = new Query(Criteria.where("deviceId").is(deviceId));
        Update update = new Update();
        update.set("location", location);

        UpdateResult result = mongoTemplate.updateMulti(query, update, Device.class);
        return result.getModifiedCount();
    }

    //更新运行时间
    @Override
    public long updateLastRunTime(String deviceId, String lastRunTime) {
        log.info("更新运行时间  " + deviceId);
        Query query = new Query(Criteria.where("deviceId").is(deviceId));
        Update update = new Update();
        update.set("lastRunTime", lastRunTime);

        UpdateResult result = mongoTemplate.updateMulti(query, update, Device.class);
        return result.getModifiedCount();
    }

    //更改设备名
    @Override
    public long updateDeviceName(String deviceId, String deviceName) {
        Query query = new Query(Criteria.where("deviceId").is(deviceId));
        Update update = new Update();
        update.set("deviceName", deviceName);

        UpdateResult result = mongoTemplate.updateMulti(query, update, Device.class);
        return result.getModifiedCount();
    }

    //更新设备备注
    @Override
    public long updateDiscription(String deviceId, String discription) {
        Query query = new Query(Criteria.where("deviceId").is(deviceId));
        Update update = new Update();
        update.set("discription", discription);

        UpdateResult result = mongoTemplate.updateMulti(query, update, Device.class);
        return result.getModifiedCount();
    }

    //更新imei
    @Override
    public long updateImeiByDeviceId(String deviceId, String imei) {
        log.info("更新imei" + deviceId + ":" + imei);
        Query query = new Query(Criteria.where("deviceId").is(deviceId));
        Update update = new Update();
        update.set("JoinInfo", new JoinInfo(imei));

        UpdateResult result = mongoTemplate.updateMulti(query, update, Device.class);
        return result.getModifiedCount();
    }
    //新增传感器
    public long addSensor(String deviceId, String sensorType) {
        log.info("新增传感器  " + deviceId + ":" + sensorType);
        Query query = new Query(Criteria.where("deviceId").is(deviceId));
        Update update = new Update();
        update.push("sensorList", sensorType);
        UpdateResult result = mongoTemplate.updateMulti(query, update, Device.class);
        return result.getModifiedCount();
    }

    //删除传感器
    public long deleteSensor(String deviceId, String sensorType) {
        log.info("删除传感器  " + deviceId + sensorType);
        Query query = new Query(Criteria.where("deviceId").is(deviceId));
        Update update = new BasicUpdate("{'$pull':{'sensorList':'" + sensorType + "'}}");
        UpdateResult result = mongoTemplate.updateMulti(query, update, Device.class);
        return result.getModifiedCount();
    }

    //删除所有传感器
    public long deleteAllSensor(String deviceId) {
        log.info("删除设备所有传感器  " + deviceId);
        Query query = new Query(Criteria.where("deviceId").is(deviceId));
        Update update = new Update();
        update.set("sensorList", new ArrayList<>());

        UpdateResult result = mongoTemplate.updateMulti(query, update, Device.class);
        return result.getModifiedCount();
    }

    /*********************************************删除信息部分*************************************************************/

    //删除Device
    public long deleteByDeviceId(String deviceId) {
        log.info("删除设备  " +deviceId);
        Query query = new Query(Criteria.where("deviceId").is(deviceId));
        DeleteResult result = mongoTemplate.remove(query, Device.class);
        //删除Device下的设备
        mongoTemplate.remove(query, Sensor.class);
        return result.getDeletedCount();
    }



    /*********************************************添加信息部分*************************************************************/

    //添加device
    public Device addDevice(Device device) {
        log.info("添加设备  " + device.getDeviceId());
        return mongoTemplate.save(device);
    }

    //只添加 deviceId (用户没在网页添加，但板子已经发来信息)
    public Device addByDeviceId(String deviceId) {
        Device device = new Device();
        device.setDeviceId(deviceId);
        return mongoTemplate.save(device);
    }


}
