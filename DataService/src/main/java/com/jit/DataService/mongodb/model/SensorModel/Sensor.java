package com.jit.DataService.mongodb.model.SensorModel;

import lombok.Data;

import java.io.Serializable;

@Data
public class Sensor implements Serializable {

    //所属设备ID
    private String deviceId;
    //传感器类型
    private String sensorType;
    //最新值  从板子获取数据
    private String value;
    //是否异常  正常：0    异常：1
    private Integer state;
    //信息 三种接入方式各不相同
    private SensorInfo info;
    //最大阈值 用户自定义
    private String max;
    //最小阈值 用户自定义
    private String min;

}
