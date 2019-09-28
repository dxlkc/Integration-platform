package com.jit.InfluxDBService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReturnMsg {
    private Integer code;
    private String msg;         //json字符串
}
