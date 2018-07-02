package com.iip.datafusion.dgs.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iip.datafusion.backend.configuration.Configuration;

import java.util.List;
import java.util.Map;

/**
 * @author zengc
 * @date 2018/1/19 15:31
 */
public class UpdateIntegrityConfiguration implements Configuration {

    @JsonProperty("userId")
    private int userId;
    @JsonProperty("jobId")
    private int jobId;
    @JsonProperty("type")
    private int type;
    @JsonProperty("mapEntries")
    private List<Map<String,String>> mapEntries;
    @JsonProperty("unifyMap")
    private Map<String,String> unifyMap;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Map<String, String>> getMapEntries() {
        return mapEntries;
    }

    public void setMapEntries(List<Map<String, String>> mapEntries) {
        this.mapEntries = mapEntries;
    }

    public Map<String, String> getUnifyMap() {
        return unifyMap;
    }

    public void setUnifyMap(Map<String, String> unifyMap) {
        this.unifyMap = unifyMap;
    }
}

