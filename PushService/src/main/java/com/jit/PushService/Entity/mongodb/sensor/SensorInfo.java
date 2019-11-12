package com.jit.PushService.Entity.mongodb.sensor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SensorInfo {
    private Integer objId;
    private Integer instId;
    private Integer resId;
}
