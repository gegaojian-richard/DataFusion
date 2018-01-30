package com.iip.datafusion.dgs.model.accuracy;

import com.iip.datafusion.dgs.dao.AccuracyDao;
import com.iip.datafusion.util.jsonutil.Result;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AccuracyCheckUnit {

    @Autowired
    private AccuracyDao accuracyDao;

    public Result doCheck(String tableName, FormulaParam formulaParam){

        String whereClause = formulaParam.getWhereClause();
        List<ColumnAttributeValue> columnAttributeValues = formulaParam.getColumnAttributeValues();

        //如果where子句中的字段存在语义的对应关系
        String newWhereClause = whereClause;
        if(!columnAttributeValues.isEmpty()){
            for(ColumnAttributeValue cav : columnAttributeValues){
                String clause = "(CASE " + cav.getColumn();
                List<AttributeValue> attributeValues = cav.getAttributeValues();
                for(AttributeValue av : attributeValues){
                    clause += " WHEN '" + av.getAttribute() + "' THEN " + av.getValue();
                }
                clause += " END)";
                newWhereClause = newWhereClause.replaceAll(cav.getColumn(),clause);
            }
        }

        try{
            String[] splits = newWhereClause.split("=");
            String selectClause = "* , " + splits[1].trim() + " AS newValue";
            String columnName = splits[0].trim();
            newWhereClause = "NOT(" + newWhereClause + ")";
            SqlRowSet sqlRowSet = accuracyDao.doSelect(tableName,selectClause,newWhereClause);

            JSONArray jsonArray = new JSONArray();
            ArrayList<String> columnNames = getColumnNames(sqlRowSet);
            columnNames.remove("newValue");
            while(sqlRowSet.next()) {
                JSONArray resultSet = new JSONArray();
                JSONObject jo = new JSONObject();
                for (String name : columnNames){
                    jo.put(name,sqlRowSet.getString(name));
                }
                resultSet.add(jo);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("resultSet", resultSet);
                jsonObject.put("message", columnName + " 应改为：" + sqlRowSet.getString("newValue"));
                jsonArray.add(jsonObject);
            }

            Result result = new Result(1,null,jsonArray.toString());
            return result;
        }catch (Exception e){
            Result result = new Result(0,"出现内部错误",null);
            return result;
        }
    }

    public Result doCheck(String tableName, ConditionParam conditionParam){
        String columnName = conditionParam.getColumnName();
        List<ConditionValue> conditionValues = conditionParam.getConditionValues();

        try {
            JSONArray jsonArray = new JSONArray();
            String whereClause;
            for(ConditionValue cv : conditionValues) {
                String condition = cv.getCondition();
                String value = cv.getValue();

                whereClause = "NOT(CASE WHEN " + condition + " THEN " + columnName + " = " + value + " END)";
                String selectClause = "*";
                SqlRowSet sqlRowSet = accuracyDao.doSelect(tableName, selectClause, whereClause);

                ArrayList<String> columnNames = getColumnNames(sqlRowSet);
                JSONArray resultSet = getJsonObjectList(sqlRowSet, columnNames);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("resultSet", resultSet);
                jsonObject.put("message", columnName + "应改为：" + value);
                jsonArray.add(jsonObject);
            }
            Result result = new Result(1,null,jsonArray.toString());
            return result;

        }catch(Exception e){
            Result result = new Result(0,"出现内部错误",null);
            return result;
        }
    }

    public Result doCheck(String tableName, LengthParam lengthParam){
        String columnName = lengthParam.getColumnName();
        String length = lengthParam.getLength();

        String selectClause = "*";
        String whereClause = "length("+ columnName + ") <> " + length;

        try{
            JSONArray jsonArray = new JSONArray();
            SqlRowSet sqlRowSet = accuracyDao.doSelect(tableName,selectClause,whereClause);
            ArrayList<String> columnNames = getColumnNames(sqlRowSet);
            JSONObject jsonObject = new JSONObject();
            JSONArray resultSet = getJsonObjectList(sqlRowSet,columnNames);
            jsonObject.put("resultSet", resultSet);
            jsonObject.put("message",columnName + "的长度不等于" + length);
            jsonArray.add(jsonObject);

            Result result = new Result(1,null,jsonArray.toString());
            return result;
        }catch (Exception e){
            Result result = new Result(0,"出现内部错误",null);
            return result;
        }
    }

    public Result doCheck(String tableName, RangeParam rangeParam){
        String whereClause = rangeParam.getWhereClause();

        try {
            String newWhereClause = "NOT(" + whereClause + ")";
            String selectClause = "*";
            SqlRowSet sqlRowSet = accuracyDao.doSelect(tableName,selectClause,newWhereClause);

            JSONArray jsonArray = new JSONArray();
            ArrayList<String> columnNames = getColumnNames(sqlRowSet);
            JSONArray resultSet = getJsonObjectList(sqlRowSet,columnNames);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("resultSet", resultSet);
            jsonObject.put("message","应满足：" + whereClause);
            jsonArray.add(jsonObject);

            Result result = new Result(1,null,jsonArray.toString());
            return result;

        }catch (Exception e) {
            Result result = new Result(0, "出现内部错误", null);
            return result;
        }
    }

    public Result doCheck(String tableName, EmailParam emailParam){

        String columnName = emailParam.getColumnName();
        String whereClause = columnName +" REGEXP '^[a-zA-Z0-9]+[a-zA-Z0-9_-]*@[a-zA-Z0-9]+([\\.][a-zA-Z0-9]+){1,}$'";
        whereClause = "NOT(" + whereClause + ")";
        String selectClause = "*";
        try{
            SqlRowSet sqlRowSet = accuracyDao.doSelect(tableName,selectClause,whereClause);

            JSONArray jsonArray = new JSONArray();
            ArrayList<String> columnNames = getColumnNames(sqlRowSet);
            JSONArray resultSet = getJsonObjectList(sqlRowSet,columnNames);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("resultSet", resultSet);
            jsonObject.put("message","邮箱格式不正确！");
            jsonArray.add(jsonObject);

            Result result = new Result(1,null,jsonArray.toString());
            return result;
        }catch (Exception e) {
            Result result = new Result(0,"出现内部错误",null);
            return result;
        }
    }

    private ArrayList<String> getColumnNames(SqlRowSet sqlRowSet){
        SqlRowSetMetaData smd = sqlRowSet.getMetaData();
        int columnCount = smd.getColumnCount();
        ArrayList<String> columnNames = new ArrayList<String>();
        for(int i = 1;i <= columnCount;i++){
            columnNames.add(smd.getColumnName(i));
        }
        return columnNames;
    }

    private JSONArray getJsonObjectList(SqlRowSet sqlRowSet, ArrayList<String> colname){
        JSONArray list = new JSONArray();
        while (sqlRowSet.next()){
            JSONObject jo = new JSONObject();
            for (String name : colname){
                jo.put(name,sqlRowSet.getString(name));
            }
            list.add(jo);
        }
        return list;
    }
}
