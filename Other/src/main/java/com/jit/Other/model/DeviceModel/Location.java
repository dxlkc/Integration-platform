package com.jit.Other.model.DeviceModel;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Location {
    //省 市 区
    private String privince;
    private String city;
    private String area;
}
