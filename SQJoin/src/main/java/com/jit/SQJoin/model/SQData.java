package com.jit.SQJoin.model;

import java.util.HashMap;
import java.util.Map;

public class SQData {

    private static SQData sqData = null;
    private static Map<String, String> map = new HashMap<>();

    static {
        //设备数据字段
        map.put("STCD", "监测站代码");
        map.put("TM", "时间");
        map.put("ATC", "温度");
        map.put("AHC", "湿度");
        map.put("AWS", "（平均）风速");
        map.put("AWD", "风向");
        map.put("ALI", "光照强度");
        map.put("APT", "日降水量");
        map.put("AET", "日蒸发量");
        map.put("AAP", "气压");
        map.put("ET_XS", "小时蒸发量");
        map.put("DCDY", "电池电压");
        map.put("ET_JS", "ET的次数");
        map.put("JY_YX", "有效降雨");
        map.put("JY_YX_R", "日累计有效降雨");
        map.put("AWS_MAX", "最大风速");
        map.put("AWS_MIN", "最小风速");
        map.put("SWC20", "20CM含水量");
        map.put("SWC40", "40CM含水量");
        map.put("SWC60", "60CM含水量");
        map.put("SWC80", "80CM含水量");
        map.put("STC20", "20CM温度");
        map.put("STC40", "40CM温度");
        map.put("STC60", "60CM温度");
        map.put("STC80", "80CM温度");

        //站点信息字段
        map.put("MonitoringStationCode", "监测站代码");
        map.put("MonitoringStationName", "监测站名称");
        map.put("MonitoringStationTypeId", "监测站类型");
        map.put("AutoMonitoringDeviceTypeId", "自动设备类型");
        map.put("AutoMonitoringDeviceNum", "自动监测设备编号");
        map.put("AutoMonitoringDeviceCommunicationNum", "自动监测设备通讯号码");
        map.put("BuildStationDate", "站点建立时间");
        map.put("ManufacturerId", "关联生产厂商 ID");
        map.put("TraderId", "关联经销商ID");
        map.put("ProvinceId", "关联省份 ID");
        map.put("CityId", "关联地市 ID");
        map.put("DistrictCountyId", "关联区县 ID");
        map.put("VillageTownId", "关联乡镇 ID");
        map.put("LongitudeDegree", "经度");
        map.put("LongitudeSub", "分");
        map.put("LongitudeSecond", "秒");
        map.put("LatitudeDegree", "纬度");
        map.put("LatitudeSub", "分");
        map.put("LatitudeSecond", "秒");
        map.put("SelectedMoistureContent20CmSensor", "土壤湿度20Cm是否显示");
        map.put("SelectedMoistureContent40CmSensor", "土壤湿度40Cm是否显示");
        map.put("SelectedMoistureContent60CmSensor", "土壤湿度60Cm是否显示");
        map.put("SelectedMoistureContent80CmSensor", "土壤湿度80Cm是否显示");
        map.put("SelectedSoilTemperature20CmSensor", "土壤温度20Cm是否显示");
        map.put("SelectedSoilTemperature40CmSensor", "土壤温度40Cm是否显示");
        map.put("SelectedSoilTemperature60CmSensor", "土壤温度60Cm是否显示");
        map.put("SelectedSoilTemperature80CmSensor", "土壤温度80Cm是否显示");
        map.put("SelectedSoilSalinity20CmSensor", "土壤盐度20Cm是否显示");
        map.put("SelectedSoilSalinity40CmSensor", "土壤盐度40Cm是否显示");
        map.put("SelectedSoilSalinity60CmSensor", "土壤盐度60Cm是否显示");
        map.put("SelectedSoilSalinity80CmSensor", "土壤盐度80Cm是否显示");
        map.put("SelectedAirTemperatureSensor", "空气温度是否显示");
        map.put("SelectedAirtRelativeHumiditySensor", "空气相对湿度是否显示");
        map.put("SelectedRainfallSensor", "降雨量是否显示");
        map.put("SelectedIlluminationIntensitySensor", "总辐射是否显示");
        map.put("SelectedWindSpeedSensor", "风速是否显示");
        map.put("SelectedWindDirectionSensor", "风向是否显示");
        map.put("SelectedAtmosphericPressureSensor", "大气压力是否显示");
        map.put("SelectedEt0", "Et0 显示");
        map.put("Address", "地址");
        map.put("IsOpenEt0Calculation", "是否开启 Et0 计算");
    }

    private SQData() {
    }

    public static SQData getInstance() {
        if (null == sqData) {
            sqData = new SQData();
        }
        return sqData;
    }

    public String get(String key) {
        return map.get(key);
    }

}
