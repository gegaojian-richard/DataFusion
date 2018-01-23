package com.iip.datafusion.eems.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iip.datafusion.cms.model.ColumnStructure;

import java.util.List;

public class Entity{
    @JsonProperty("id")
    private int  id;
    @JsonProperty("displayName")
    private String displayName;
    @JsonProperty("tableName")
    private String tableName;
    @JsonProperty("dbPosition")
    private String dbPosition;
    // todo: 主数据库中存储的是物理位置，不是当时系统中某个DataSource的ID
    @JsonProperty("entityType")
    private int entityType;//0表示实体，1表示事件
    @JsonProperty("properties")
    private String properties;

    @JsonCreator
    public Entity(@JsonProperty("id") int id,
                  @JsonProperty("displayName") String displayName,
                  @JsonProperty("tableName") String tableName,
                  @JsonProperty("dbPosition") String dbPosition,
                  @JsonProperty("entityType") int entityType,
                  @JsonProperty("properties") String properties){
        this.displayName = displayName;
        this.tableName = tableName;
        this.dbPosition = dbPosition;
        this.entityType = entityType;
        this.properties = properties;
    }

    public Entity(){
        super();
    }

    public int  getId() {
        return id;
    }
    public void setId(Integer id){this.id=id;}

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDbPosition() {
        return dbPosition;
    }

    public void setDbPosition(String dbPosition) {
        this.dbPosition = dbPosition;
    }

    public int  getEntityType() {
        return entityType;
    }

    public void setEntityType(int  type) {
        this.entityType = type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }
}