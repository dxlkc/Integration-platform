package com.jit.Other.service;

import com.alibaba.fastjson.JSONObject;

public class UserSensorHistoryInformation {
    private  String  sensorType;
    private JSONObject sendTypeHistoryInformation;
    public String getSensorType() {
        return sensorType;
    }

    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
    }

    public JSONObject getSendTypeHistoryInformation() {
        return sendTypeHistoryInformation;
    }

    public void setSendTypeHistoryInformation(JSONObject sendTypeHistoryInformation) {
        this.sendTypeHistoryInformation = sendTypeHistoryInformation;
    }


}
