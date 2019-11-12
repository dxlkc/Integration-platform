package com.jit.DataService.mongodb.dao.Impl;

import com.jit.DataService.mongodb.dao.SensorDao;
import com.jit.DataService.mongodb.model.SensorModel.Sensor;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Log4j2
@Component
public class SensorDaoImpl implements SensorDao {

    @Resource
    private MongoTemplate mongoTemplate;

    /***************添加********************/

    //查询一个设备的一个传感器
    @Override
    public Sensor findOne(String deviceId, String sensorType) {
        Query query = new Query(Criteria.where("deviceId").is(deviceId).and("sensorType").is(sensorType));
        return mongoTemplate.findOne(query, Sensor.class);
    }

    //查询一个设备的所有传感器
    @Override
    public List<Sensor> findAllByDeviceId(String deviceId) {
        Query query = new Query(Criteria.where("deviceId").is(deviceId));
        return mongoTemplate.find(query, Sensor.class);
    }

    /***************添加********************/

    //添加传感器
    @Override
    public Sensor add(Sensor sensor) {
        log.info("添加传感器  " + sensor.getDeviceId());
        return mongoTemplate.save(sensor);
    }

    /*****************更新*********************/

    //更改阈值
    @Override
    public long updateThreshold(String deviceId, String sensorType, String max, String min) {
        log.info("更改阈值  " + deviceId);
        Query query = new Query(Criteria.where("deviceId").is(deviceId)
                .and("sensorType").is(sensorType));
        Update update = new Update();
        update.set("max", max);
        update.set("min", min);
        UpdateResult result = mongoTemplate.updateMulti(query, update, Sensor.class);
        return result.getModifiedCount();
    }

    //更新最新值
    @Override
    public long updateValue(String deviceId, String sensorType, String value) {
        log.info("更新最新值  " + deviceId + ":" + sensorType + ":" + value);
        Query query = new Query(Criteria.where("deviceId").is(deviceId).and("sensorType").is(sensorType));
        Update update = new Update();
        update.set("value", value);

        UpdateResult result = mongoTemplate.updateMulti(query, update, Sensor.class);
        return result.getModifiedCount();
    }

    //更新状态（是否异常）
    @Override
    public long updateState(String deviceId, String sensorType, Integer state) {
        log.info("更新状态  " + deviceId + ":" + sensorType + ":" + state);
        Query query = new Query(Criteria.where("deviceId").is(deviceId).and("sensorType").is(sensorType));
        Update update = new Update();
        update.set("state", state);

        UpdateResult result = mongoTemplate.updateMulti(query, update, Sensor.class);
        return result.getModifiedCount();
    }

    /***************删除************************/
    //删除一个设备的所有传感器
    @Override
    public long deleteAllByDeviceId(String deviceId) {
        log.info("删除设备的所有传感器  " + deviceId);
        Query query = new Query(Criteria.where("deviceId").is(deviceId));
        DeleteResult result = mongoTemplate.remove(query, Sensor.class);
        return result.getDeletedCount();
    }

    //删除一个传感器
    @Override
    public long deleteOne(String deviceId, String sensorType) {
        log.info("删除一个传感器  " + deviceId + ":" + sensorType);
        Query query = new Query(Criteria.where("deviceId").is(deviceId).and("sensorType").is(sensorType));
        DeleteResult result = mongoTemplate.remove(query, Sensor.class);
        return result.getDeletedCount();
    }
}
