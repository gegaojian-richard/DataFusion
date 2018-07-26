package com.iip.datafusion.nsps.model.doc2vec;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iip.datafusion.backend.configuration.Configuration;

import java.util.ArrayList;


/**
 * @Author YLX
 * @Date 2018/2/1
 */
public class Doc2VecConfiguration implements Configuration {

    @JsonProperty("dataSourceId")
    private String dataSourceId;
    @JsonProperty("path")
    private String path;


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(String dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

}
