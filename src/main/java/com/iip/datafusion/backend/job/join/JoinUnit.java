package com.iip.datafusion.backend.job.join;

import java.util.*;

/**
 * 数据整合单元
 * Created by GeGaojian on 2018/01/18.
 */
public class JoinUnit {

    private Map<String, String> s2tMap = new HashMap<>(); // 该joinunit需要映射到目标表的字段
    private List<String> fields2Select = new ArrayList<>(); // 需要select的字段
    private String databaseID; // 数据库标识
    private String tableName;  // 表格名

    private String parentTable=""; // 左连接的join表格名
    private String parentJoinField=""; // 左连接的join字段
    private String joinField=""; // 本表格的join字段

    private List<JoinUnit> joinUnits = new ArrayList<>(); // 以本表作为左连接的join表格的表格列表
    private JoinUnit parentJoinUnit; // 左连接的join表格

    public JoinUnit(String databaseID, String tableName){
        this.databaseID = databaseID;
        this.tableName = tableName;
    }

    /**
     * 添加一个需要select的字段
     * @param fieldName
     */
    public void addSelectField(String fieldName){

        //todo: 检查重复字段， 可以直接使用contains吗
        if (!fields2Select.contains(fieldName)) {
            fields2Select.add(fieldName);
        }
    }

    public void addFieldMap(String targetFieldName, String sourceFieldName){
        s2tMap.put(sourceFieldName, targetFieldName);
    }

    public void addJoinUnit(JoinUnit joinUnit){
        joinUnit.setParentJoinUnit(this);
        joinUnits.add(joinUnit);
    }

    public void setParentJoinUnit(JoinUnit joinUnit){
        this.parentJoinUnit = joinUnit;
    }

    public void setJoinField(String joinField) {
        this.joinField = joinField;
    }

    public void setParentJoinField(String parentJoinField) {
        this.parentJoinField = parentJoinField;
    }

    public String getSQLStatement(){
        StringBuilder result = new StringBuilder("select ");
        result.append(fields2Select.get(0));
        for (int i = 1; i < fields2Select.size(); i++) {
            result.append(", ").append(fields2Select.get(i));
        }
        result.append(" from ").append(tableName);
        if (joinField!=""){
            result.append(" where ").append(joinField).append(" = ?");
        }
        return result.toString();
    }

    public String getDatabaseID() {
        return databaseID;
    }

    public JoinUnit getParentJoinUnit() {
        return parentJoinUnit;
    }

    public SQLTask getSQLTask(){
        SQLTask sqlTask = new SQLTask();
        sqlTask.setSql(getSQLStatement());
        sqlTask.setSelectedFields((ArrayList<String>) fields2Select);
        sqlTask.setDatasourceID(databaseID);
        sqlTask.whereFieldName = parentJoinField;
        if (parentJoinUnit != null)
            sqlTask.setParentJoinUnit(parentJoinUnit.getJoinUnitID());
        else
            sqlTask.setParentJoinUnit("");
        sqlTask.setCurrentJoinUnit(getJoinUnitID());
        sqlTask.setAsync(false);

        return sqlTask;
    }

    public String getJoinUnitID(){
        return databaseID + ":" + tableName;
    }

    public List<JoinUnit> getJoinUnits() {
        return joinUnits;
    }
}
