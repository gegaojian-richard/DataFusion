package com.iip.datafusion.backend.job.join;

public class SQLTask {
    String sql;
    String whereFieldName;
    String parentJoinUnit;
    String currentJoinUnit;
    String datasourceID;
    boolean async;

    public SQLTask(){
    }

    public SQLTask(String sql, String whereFieldName, String parentJoinUnit, String currentJoinUnit, String datasourceID, boolean async) {
        this.sql = sql;
        this.whereFieldName = whereFieldName;
        this.parentJoinUnit = parentJoinUnit;
        this.currentJoinUnit = currentJoinUnit;
        this.datasourceID = datasourceID;
        this.async = async;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getWhereFieldName() {
        return whereFieldName;
    }

    public void setWhereFieldName(String whereFieldName) {
        this.whereFieldName = whereFieldName;
    }

    public String getParentJoinUnit() {
        return parentJoinUnit;
    }

    public void setParentJoinUnit(String parentJoinUnit) {
        this.parentJoinUnit = parentJoinUnit;
    }

    public String getCurrentJoinUnit() {
        return currentJoinUnit;
    }

    public void setCurrentJoinUnit(String currentJoinUnit) {
        this.currentJoinUnit = currentJoinUnit;
    }

    public String getDatasourceID() {
        return datasourceID;
    }

    public void setDatasourceID(String datasourceID) {
        this.datasourceID = datasourceID;
    }

    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }
}
