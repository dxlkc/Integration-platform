package com.jit.NBJoin.model;

public class Lwm2mObject {
    public static String Type(Integer type) {
        String res = null;
        switch (type) {
            case 3303:
                res = "temperature";
                break;
            case 3304:
                res = "humidity";
                break;
        }

        return res;
    }
}
