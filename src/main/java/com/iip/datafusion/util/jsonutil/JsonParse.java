package com.iip.datafusion.util.jsonutil;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

/**
 * Created by jingwei on 2017/12/27.
 * 用于json与java对象的转化，全局维护一个即可
 */
public class JsonParse {
    private static ObjectMapper mapper = new ObjectMapper();

    public static ObjectMapper getMapper() {
        return mapper;
    }
}
