package com.iip.datafusion.nsps.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.context.annotation.Configuration;

/**
 * @Author Junnor.G
 * @Date 2018/1/31 下午2:21
 */

public class TestConfiguration {
    @JsonProperty("path")
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
