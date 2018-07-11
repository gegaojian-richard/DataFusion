package com.iip.datafusion.dgs.model.consistency;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MapEntries2 {
    @JsonProperty("key")
    String key;
    @JsonProperty("value")
    String value;

//    public MapEntries() {
//        super();
//    }

    @JsonCreator
    public MapEntries2(@JsonProperty("key") String key,
                      @JsonProperty("value") String value
    ) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String updatetype) {
        this.key = updatetype;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String updateP_key,String referValue) {
        this.value = updateP_key+','+referValue;
    }

}
