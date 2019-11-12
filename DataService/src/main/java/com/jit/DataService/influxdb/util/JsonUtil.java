package com.jit.DataService.influxdb.util;

import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JsonUtil {

    public static <T> Map<String, T> JasonObjectToMap(HashMap<String, T> map ,JSONObject jsonObject){
        for (Iterator<String> keys = jsonObject.keys(); keys.hasNext();) {
            String key = keys.next();
            map.put(key,(T)jsonObject.get(key));
        }
        return map;
    }
}
