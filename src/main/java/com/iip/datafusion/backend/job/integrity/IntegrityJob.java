package com.iip.datafusion.backend.job.integrity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iip.datafusion.backend.job.Job;
import com.iip.datafusion.backend.job.JobBase;
import com.iip.datafusion.util.dbutil.DataSourceRouterManager;
import com.iip.datafusion.util.jsonutil.Result;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * 完整性检查工作描述
 * Created by GeGaojian on 2018/01/18.
 */

public class IntegrityJob extends JobBase implements Job {

    private String dataSourceId;
    private String tableName;
    private List<String> sqlList;
    private String innerJobType;

    public List<String> getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(List<String> columnNames) {
        this.columnNames = columnNames;
    }

    private List<String> columnNames;

    @JsonIgnore
    public String getInnerJobType() {
        return innerJobType;
    }

    public void setInnerJobType(String innerJobType) {
        this.innerJobType = innerJobType;
    }

    @JsonIgnore
    public String getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(String dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    @JsonIgnore
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @JsonIgnore
    public List<String> getSqlList() {
        return sqlList;
    }

    public void setSqlList(List<String> sqlList) {
        this.sqlList = sqlList;
    }

    public String rowSetToJson(SqlRowSet sqlRowSet) {

        SqlRowSetMetaData sqlRsmd = sqlRowSet.getMetaData();
        ArrayList<String> trueColumnNames = new ArrayList<>();
        for(int i=1;i<=sqlRsmd.getColumnCount();i++){

            trueColumnNames.add(sqlRsmd.getColumnName(i));

        }
        JSONObject wholeJsonObj = new JSONObject();
        JSONArray array = new JSONArray();


        // 遍历ResultSet中的每条数据
        while (sqlRowSet.next()) {
            JSONObject jsonObj = new JSONObject();

            // 遍历每一列
            for (int i = 0; i < trueColumnNames.size(); i++) {
                String columnName =trueColumnNames.get(i);
                String value = sqlRowSet.getString(columnName);
                if(value !=null)
                    jsonObj.put(columnName, value);
                else
                    jsonObj.put(columnName, "NULL");
                //System.out.println(columnName+" "+value+"\n");
            }
            array.add(jsonObj);
        }
        wholeJsonObj.put("items",array);
        //System.out.println(array.toString());
        return wholeJsonObj.toString();


    }

    @Override
    public String getDescription() {
        return ""+dataSourceId+"."+tableName;
    }
}
