package com.iip.datafusion.dgs.model;

import java.util.HashMap;
import java.util.Map;

public class InstanceEntity {


    Map<String,String> kv = new HashMap<String,String>();

    public Map<String, String> getKv() {
        return kv;
    }

    public void setKv(Map<String, String> kv) {
        this.kv = kv;
    }

    @Override
    public String toString() {
        String res = "";
        for(String key : kv.keySet()){
            res += ("\n"+""+key+" : "+kv.get(key));
        }
        return res;
    }
}
