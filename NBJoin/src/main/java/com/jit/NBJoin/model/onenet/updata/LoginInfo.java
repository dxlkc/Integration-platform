package com.jit.NBJoin.model.onenet.updata;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginInfo {
    private Integer type;
    private Integer dev_id;
    private Integer status;
    private Integer login_type ;
    private BigInteger at;
    private String imei;
}
