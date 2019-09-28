package com.jit.NBJoin.model.onenet.downdata;

public abstract class CommonEntity {
    // 设备imei号，平台唯一，必填参数
    protected String imei;
    // ISPO标准中的Object ID
    protected Integer obj_id;
    // ISPO标准中的Object Instance ID
    protected Integer obj_inst_id;
    // ISPO标准中的Resource ID
    protected Integer res_id;

    public abstract String toUrl();
}
