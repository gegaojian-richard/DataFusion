package com.iip.datafusion.dgs.model.consistency;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MapEntries {
    @JsonProperty("key")
    String key;
    @JsonProperty("value")
    String value;

//    public MapEntries() {
//        super();
//    }

    @JsonCreator
    public MapEntries(@JsonProperty("key") String key,
                      @JsonProperty("value") String value
    ) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String mainColumnName,String mainPrimary_key) {
        this.key = mainColumnName+','+mainPrimary_key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String followDatasourceID,String followTableName,String followColumnName,String followPrimary_key) {
        this.value = followDatasourceID+','+followTableName+','+followColumnName+','+followPrimary_key;
    }

}
