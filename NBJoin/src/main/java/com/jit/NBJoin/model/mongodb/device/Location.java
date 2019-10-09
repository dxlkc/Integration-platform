package com.jit.NBJoin.model.mongodb.device;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    //省 市 区
    private String province;
    private String city;
    private String area;
}
