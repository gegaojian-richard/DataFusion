package com.iip.datafusion.eems.model;
public class Entity{
    private int  id;
    private String displayName;
    private String tableName;
    private String dbPosition;
    private int entityType;//0表示实体，1表示事件
    private String properties;
    public Entity(){

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

    public String getProperties() {
        return properties;
    }

    public void setProperties(String proterties) {
        this.properties = proterties;
    }
}