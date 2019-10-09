package com.jit.LoraJoin.service;

import net.sf.json.JSONObject;

public interface LoraService {

    void doSeeed(String eui, String data);

    void doConsum(String eui, JSONObject jsonObject);
}
