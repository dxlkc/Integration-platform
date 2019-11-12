package com.jit.DataService.mongodb.model.DeviceModel;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Location {
    //省 市 区
    private String province;
    private String city;
    private String area;
}
