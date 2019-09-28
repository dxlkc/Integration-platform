package com.jit.LoraJoin.model.rabbitmq;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MqData {
    private String device_id;
    private String type;
    private Object value;
}
