package com.jit.SQJoin.model.mongodb.device;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Device implements Serializable {
    //设备ID
    private String deviceId;
    //连接状态  断开：0  连接：1
    private Integer state;
    //接入类型
    private String joinType;
    //位置信息
    private Location location;
    //用户定义的板子名称 用户自定义
    private String deviceName;
    //描述 用户自定
    private String discription;
    //加入信息
    private JoinInfo joinInfo;
    //上次运行时间
    private String lastRunTime;
    //传感器列表
    private List<String> sensorList;
}

