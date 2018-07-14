package com.iip.datafusion.dgs.model;

import com.iip.datafusion.backend.job.JobBase;
import com.iip.datafusion.util.jsonutil.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zengc
 * @date 2018/3/26 19:18
 */
public class UpdateIntegrityJob extends JobBase{

    private String dataSourceId;
    private String tableName;
    private int type; //0代表手动更新，1代表自动更新
    private List<Map<String,String>> mapEntries;
    private Map<String,String> unifyMap;
    private List<String> sqlList;
    private Result result;

    public String getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(String dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<String> getSqlList() {
        return sqlList;
    }

    public void setSqlList(List<String> sqlList) {
        this.sqlList = sqlList;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Map<String, String>> getMapEntries() {
        return mapEntries;
    }

    public void setMapEntries(List<Map<String, String>> mapEntries) {
        this.mapEntries = mapEntries;
    }

    public Map<String, String> getUnifyMap() {
        return unifyMap;
    }

    public void setUnifyMap(Map<String, String> unifyMap) {
        this.unifyMap = unifyMap;
    }

    public String sqlChangeType(String raw,String type){
        Map<String,Integer> typeMap = new HashMap<>();
        //String 0 int 1
        typeMap.put("CHAR",0);
        typeMap.put("VARCHAR",0);
        typeMap.put("LONGVARCHAR",0);
        typeMap.put("NUMERIC",1);
        typeMap.put("DECIMAL",1);
        typeMap.put("BIT",1);
        typeMap.put("BOOLEAN",1);
        typeMap.put("TINYINT",1);
        typeMap.put("SMALLINT",1);
        typeMap.put("INTEGER",1);
        typeMap.put("BIGINT",1);
        typeMap.put("REAL",1);
        typeMap.put("FLOAT",1);
        typeMap.put("DOUBLE",1);
        typeMap.put("BINARY",0);
        typeMap.put("VARBINARY",0);
        typeMap.put("LONGVARBINARY",0);
        typeMap.put("TIME",1);
        typeMap.put("DATE",1);
        typeMap.put("TIMESTAMP",1);

        if(typeMap.containsKey(type)){
            if(typeMap.get(type) == 0){
                if(raw.indexOf("\"")== -1){
                    raw = "\""+raw+"\"";
                    return raw;
                }
            }
        }
        return raw;
    }
}
