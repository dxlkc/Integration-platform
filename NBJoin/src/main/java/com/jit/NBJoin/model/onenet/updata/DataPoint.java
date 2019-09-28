package com.jit.NBJoin.model.onenet.updata;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataPoint {
    private Integer type;
    private BigInteger at;
    private String imei;
    private String ds_id;
    private Integer dev_id;
    private Object value;
}
