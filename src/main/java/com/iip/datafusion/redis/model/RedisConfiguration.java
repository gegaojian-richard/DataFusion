package com.iip.datafusion.redis.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author zengc
 * @date 2018/3/10 18:16
 */
public class RedisConfiguration {

    @JsonProperty("key")
    private String key;
    @JsonProperty("start")
    private int start;
    @JsonProperty("end")
    private int end;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}
