package com.jit.NBJoin.model.onenet.downdata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//接收oneNet下发的返回参数
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExecuteRet {
    private Integer errno;          // 0 表示成功
    private String error;           // “succ” 表示成功
}
