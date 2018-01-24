package com.iip.datafusion.backend.job.join;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JoinUnit {

    private List<String> fields2Select = new ArrayList<>(); // 需要select的字段
    private String databaseID; // 数据库标识
    private String tableName; // 表格名
    private String where="";
    private String order="";

    private String parentTable=""; // 左连接的join表格名
    private String joinParentField=""; // 左连接的join字段
    private String joinField=""; // 本表格的join字段

    private List<JoinUnit> joinUnits; // 以本表作为左连接的join表格的表格列表
    private JoinUnit parentJoinUnit; // 左连接的join表格

    public JoinUnit(String databaseID, String tableName){
        this.databaseID = databaseID;
        this.tableName = tableName;
        this.where = "";
    }

    /**
     * 添加一个需要select的字段
     * @param fieldName
     */
    public void addField(String fieldName){

        //todo: 检查是否重复

        fields2Select.add(fieldName);
    }

    public void addJoinUnit(JoinUnit joinUnit){
        joinUnit.setParentJoinUnit(this);
        joinUnits.add(joinUnit);
    }

    public void setParentJoinUnit(JoinUnit joinUnit){
        this.parentJoinUnit = joinUnit;
    }

    public String getSQLStatement(){
        StringBuilder result = new StringBuilder("select ");
        result.append(fields2Select.get(0));
        for (int i = 1; i < fields2Select.size(); i++) {
            result.append(", ").append(fields2Select.get(i));
        }
        result.append("from ").append(tableName);
        return result.toString();
    }

    public String getSQLStatement2(){
        StringBuilder result = new StringBuilder("select ");
        return result.toString();
    }
}
