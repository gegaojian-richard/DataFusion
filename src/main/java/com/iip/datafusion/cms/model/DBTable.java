package com.iip.datafusion.cms.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DBTable {
    @JsonProperty("id")
    private String id;
    private String displayName;
    private List<TableStructure> tables;

    public String getId() {
        return id;
    }

    public void setId(String  id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public List<TableStructure> getTables() {
        return tables;
    }

    public void setTables(List<TableStructure> tables) {
        this.tables = tables;
    }
}
