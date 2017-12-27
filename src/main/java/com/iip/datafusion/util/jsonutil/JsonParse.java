package com.iip.datafusion.util.jsonutil;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

/**
 * Created by jingwei on 2017/12/27.
 * 用于json与java对象的转化，全局维护一个即可
 */
@Component
public class JsonParse {
    private ObjectMapper mapper;

    public JsonParse() {
         this.mapper = new ObjectMapper();
    }

    public ObjectMapper getMapper() {
        return mapper;
    }
}
