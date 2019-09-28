package com.jit.NBJoin.model.onenet.downdata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//存放下发的命令
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Args {
    private String args;
}
