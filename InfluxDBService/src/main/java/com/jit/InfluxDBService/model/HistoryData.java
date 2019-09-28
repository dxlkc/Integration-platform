package com.jit.InfluxDBService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.AssertTrue;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryData {
    private String time;
    private String value;
}
