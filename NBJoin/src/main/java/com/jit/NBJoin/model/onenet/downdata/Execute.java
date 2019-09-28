package com.jit.NBJoin.model.onenet.downdata;

//存放下发到的设备对象信息
public class Execute extends CommonEntity {

    public Execute(String imei, Integer obj_id, Integer obj_inst_id, Integer res_id) {
        this.imei = imei;
        this.obj_id = obj_id;
        this.obj_inst_id = obj_inst_id;
        this.res_id = res_id;
    }

    @Override
    public String toUrl() {
        StringBuilder url = new StringBuilder();
        url.append("/nbiot/execute?imei=").append(this.imei);
        url.append("&obj_id=").append(this.obj_id);
        url.append("&obj_inst_id=").append(this.obj_inst_id);
        url.append("&res_id=").append(this.res_id);
        return url.toString();
    }
}
