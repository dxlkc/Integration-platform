package com.jit.IPCJoin.model.mongodb.sensor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Sensor implements Serializable {
    //板子ID 固定
    private String deviceId;
    //传感器类型
    private String sensorType;
    //最新值  从板子获取数据
    private String value;
    //是否异常 正常：0    异常：1
    private Integer state;
    //信息 三种接入方式各不相同
    private SensorInfo info;
    //最大阈值 用户自定义
    private String max;
    //最小阈值 用户自定义
    private String min;

}
