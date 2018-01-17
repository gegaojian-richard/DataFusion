package com.iip.datafusion.dgs.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

/**
 * @author zengc
 * @date 2018/1/16 13:39
 */
public class TablesAnyEmptyCheckParam {

    @JsonProperty("dataSourceId")
    private String dataSourceId;
    @JsonProperty("tablesValue")
    Map<String,List<String>> tablesValue;

    public String getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(String dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    public Map<String, List<String>> getTablesValue() {
        return tablesValue;
    }

    public void setTablesValue(Map<String, List<String>> tablesValue) {
        this.tablesValue = tablesValue;
    }
}
