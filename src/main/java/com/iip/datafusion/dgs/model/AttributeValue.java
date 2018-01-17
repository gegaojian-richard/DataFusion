package com.iip.datafusion.dgs.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AttributeValue {

    @JsonProperty("attribute")
    private String attribute;
    @JsonProperty("value")
    private String value;

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
