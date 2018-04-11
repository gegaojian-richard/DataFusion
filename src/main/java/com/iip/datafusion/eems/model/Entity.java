package com.iip.datafusion.eems.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Entity{
    @JsonProperty("id")
    private int  id;
    @JsonProperty("displayName")
    private String displayName;
    @JsonProperty("tableName")
    private String tableName;
    @JsonProperty("dbPosition")
    private String dbPosition;//格式同dataSourceProperty中的URL  host:port/dataBaseName
    @JsonProperty("entityType")
    private int entityType;//0表示实体，1表示事件
    @JsonProperty("properties")
    private String properties;

    /*表示该entity是否已连接
    dbID.isEmpty()==true  未连接
    dbID.isEmpty()==false 已连接，存储连接的ID
     */
    @JsonProperty("dbID")
    private String dbID;

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
        //初始化为空字符串，表示未连接
        this.dbID="";
    }

    public Entity(){
        super();
        this.dbID="";
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

    public String getDbID() {
        return dbID;
    }

    public void setDbID(String dbID) {
        this.dbID = dbID;
    }
}