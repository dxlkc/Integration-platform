package com.jit.InfluxDBService.InfluxdbDao;

import java.util.ArrayList;
import java.util.Map;

public interface InfluxdbDao {

    void insert(String measurement, Map<String, String> tags, Map<String, Object> fields);

    ArrayList findByTime(String starttime, String endtime, String measurement);

    String deleteMeasurement(String measurement);

}
