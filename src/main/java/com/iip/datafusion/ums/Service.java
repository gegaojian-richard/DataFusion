package com.iip.datafusion.ums;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jingwei on 2017/12/19.
 */
public class Service {

    public Map<String ,String> register(String username, String password){
        Map<String,String> map = new HashMap<>();
        if(StringUtils.isEmpty(username)){
            map.put("msg","username is empty!");
        }
        return map;
    }
}
