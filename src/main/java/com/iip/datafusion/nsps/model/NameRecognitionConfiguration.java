package com.iip.datafusion.nsps.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @Author Junnor.G
 * @Date 2018/2/1 下午4:14
 */
public class NameRecognitionConfiguration {
    @JsonProperty("path")
    String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
