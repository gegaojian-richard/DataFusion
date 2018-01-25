package com.iip.datafusion.dfs.join;


import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by GeGaojian on 2017/12/22.
 */

public class JoinUnit {
    private Map<String, String> S2TMap = new LinkedHashMap<>();  // 原字段与目标字段映射表
    private String databaseID; // 数据库标识
    private String tableName; // 表格名
    private String where="";
    private String order="";

    private String parentTable=""; // 左连接的join表格名
    private String parentJoinField=""; // 左连接的join字段
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
     * @param sourceFieldName
     * @param targetFieldName 当select的字段为
     */
    public void addField(String sourceFieldName,String targetFieldName){

        //todo: 检查是否重复

        S2TMap.put(sourceFieldName, targetFieldName);
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
}
