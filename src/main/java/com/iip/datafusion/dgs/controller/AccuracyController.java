package com.iip.datafusion.dgs.controller;

import com.iip.datafusion.dgs.model.*;
import com.iip.datafusion.dgs.service.AccuracyService;
import com.iip.datafusion.util.jsonutil.Result;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class AccuracyController {

    @Autowired
    private AccuracyService accuracyService;

    @RequestMapping(path = "/dgs/accuracy/formulaCheck",method = RequestMethod.POST)
    @ResponseBody
    public Result formulaCheck(@RequestBody FormulaCheckParam formulaCheckParam){
        String dataSourceId = formulaCheckParam.getDataSourceId();
        String tableName = formulaCheckParam.getTableName();
        String whereClause = formulaCheckParam.getWhereClause();
        ArrayList<ColumnAttributeValue> columnAttributeValues = formulaCheckParam.getColumnAttributeValues();

        String newWhereClause = whereClause;
        if(!columnAttributeValues.isEmpty()){
            for(ColumnAttributeValue cav : columnAttributeValues){
                String clause = "(CASE " + cav.getColumn();
                ArrayList<AttributeValue> attributeValues = cav.getAttributeValues();
                for(AttributeValue av : attributeValues){
                    clause += " WHEN '" + av.getAttribute() + "' THEN " + av.getValue();
                }
                clause += " END)";
                newWhereClause = newWhereClause.replaceAll(cav.getColumn(),clause);
            }
        }

        JSONArray jsonArray = new JSONArray();
        if(newWhereClause.contains("="))
        {
            String[] splits = newWhereClause.split("=");
            String selectClause = "* , " + splits[1].trim() + " AS newValue";
            String columnName = splits[0].trim();

            newWhereClause = "NOT(" + newWhereClause + ")";
            SqlRowSet sqlRowSet = accuracyService.selectData(dataSourceId,selectClause,tableName, newWhereClause);

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
        }
        else{
            newWhereClause = "NOT(" + newWhereClause + ")";
            SqlRowSet sqlRowSet = accuracyService.selectData(dataSourceId,tableName, newWhereClause);

            ArrayList<String> columnNames = getColumnNames(sqlRowSet);
            JSONArray resultSet = getJsonObjectList(sqlRowSet,columnNames);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("resultSet", resultSet);
            jsonObject.put("message","应满足：" + whereClause);

            jsonArray.add(jsonObject);
        }

        Result result = new Result(1,null,jsonArray.toString());
        return result;
    }

    @RequestMapping(path = "/dgs/accuracy/formulaUpdate",method = RequestMethod.POST)
    @ResponseBody
    public Result formulaUpdate(@RequestBody FormulaUpdateParam formulaUpdateParam){
        String dataSourceId = formulaUpdateParam.getDataSourceId();
        String tableName = formulaUpdateParam.getTableName();
        String setClause = formulaUpdateParam.getSetClause();
        String whereClause = formulaUpdateParam.getWhereClause();

        //whereClause = "NOT(" + whereClause +")";
        boolean status = accuracyService.updateData(dataSourceId,tableName,setClause,whereClause);

        Result result;
        if(status){
            result = new Result(1,null,"更新成功");
        }else{
            result = new Result(0,null,null);
        }
        return result;
    }

    @RequestMapping(path = "/dgs/accuracy/lengthCheck",method = RequestMethod.POST)
    @ResponseBody
    public Result lengthCheck(@RequestBody LengthCheckParam lengthCheckParam){
        String dataSourceId = lengthCheckParam.getDataSourceId();
        String tableName = lengthCheckParam.getTableName();
        String columnName = lengthCheckParam.getColumnName();
        String length = lengthCheckParam.getLength();

        JSONArray jsonArray = new JSONArray();

        //找到指定列小于规定长度的所有记录
        String whereClause = "length("+ columnName + ") < " + length;
        SqlRowSet sqlRowSet = accuracyService.selectData(dataSourceId,tableName,whereClause);
        ArrayList<String> columnNames = getColumnNames(sqlRowSet);
        JSONObject jsonObject1 = new JSONObject();
        JSONArray resultSet1 = getJsonObjectList(sqlRowSet,columnNames);
        jsonObject1.put("resultSet", resultSet1);
        jsonObject1.put("message",columnName + "的长度小于" + length);
        jsonArray.add(jsonObject1);

        //找到指定列小于规定长度的所有记录
        whereClause = "length("+ columnName + ") > " + length;
        sqlRowSet = accuracyService.selectData(dataSourceId,tableName,whereClause);
        JSONObject jsonObject2 = new JSONObject();
        JSONArray resultSet2 = getJsonObjectList(sqlRowSet,columnNames);
        jsonObject2.put("resultSet",resultSet2);
        jsonObject2.put("message",columnName + "的长度大于" + length);
        jsonArray.add(jsonObject2);

        Result result = new Result(1,null,jsonArray.toString());
        return result;
    }

    @RequestMapping(path = "/dgs/accuracy/lengthUpdate",method = RequestMethod.POST)
    @ResponseBody
    public Result lengthUpdate(@RequestBody LengthUpdateParam lengthUpdateParam){
        String dataSourceId = lengthUpdateParam.getDataSourceId();
        String tableName = lengthUpdateParam.getTableName();
        String columnName = lengthUpdateParam.getColumnName();
        String length = lengthUpdateParam.getLength();
        String fill = lengthUpdateParam.getFill();

        String whereClause = "length("+ columnName + ") < " + length;
        SqlRowSet sqlRowSet = accuracyService.selectData(dataSourceId,tableName,whereClause);

        ArrayList<String> columnNames = getColumnNames(sqlRowSet);
        boolean flag = true;
        JSONArray jsonArray = new JSONArray();
        JSONArray resultSet = new JSONArray();
        while(sqlRowSet.next()){
            String oldValue = sqlRowSet.getString(columnName);
            String newValue = oldValue;
            while(newValue.length() < Integer.parseInt(length)){
                newValue += fill;
            }
            boolean status = accuracyService.updateData(dataSourceId,tableName,columnName,newValue,whereClause);
            if(!status){
                flag = false;
                JSONObject jo = new JSONObject();
                for (String name : columnNames) {
                    jo.put(name, sqlRowSet.getString(name));
                }
                resultSet.add(jo);
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("resultSet", resultSet);
        jsonObject.put("message","更新失败");
        jsonArray.add(jsonObject);

        Result result;
        if(flag){
            result = new Result(1,null,"全部更新成功");
        }else{
            result = new Result(0,null,jsonArray.toString());
        }
        return result;
    }

    @RequestMapping(path = "/dgs/accuracy/conditionCheck",method = RequestMethod.POST)
    @ResponseBody
    public Result conditionCheck(@RequestBody ConditionCheckParam conditionCheckParam){
        String dataSourceId = conditionCheckParam.getDataSourceId();
        String tableName = conditionCheckParam.getTableName();
        String columnName = conditionCheckParam.getColumnName();
        ArrayList<ConditionValue> conditionValues = conditionCheckParam.getConditionValues();
        String whereClause;

        JSONArray jsonArray = new JSONArray();
        for(ConditionValue cv : conditionValues) {
            String condition = cv.getCondition();
            String value = cv.getValue();

            whereClause = "NOT(CASE WHEN " + condition + " THEN " + columnName + " = " + value + " END)";
            SqlRowSet sqlRowSet = accuracyService.selectData(dataSourceId,tableName,whereClause);

            ArrayList<String> columnNames = getColumnNames(sqlRowSet);
            JSONArray resultSet = getJsonObjectList(sqlRowSet,columnNames);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("resultSet", resultSet);
            jsonObject.put("message",columnName + "应改为：" + value);
            jsonArray.add(jsonObject);
        }

        Result result = new Result(1,null,jsonArray.toString());
        return result;
    }

    @RequestMapping(path = "/dgs/accuracy/update",method = RequestMethod.POST)
    @ResponseBody
    public Result update(@RequestBody UpdateParam updateParam){
        String dataSourceId = updateParam.getDataSourceId();
        String tableName = updateParam.getTableName();
        String columnName = updateParam.getColumnName();
        String newValue = updateParam.getNewValue();
        String whereClause = updateParam.getWhereClause();

        boolean status = accuracyService.updateData(dataSourceId,tableName,columnName,newValue,whereClause);

        Result result;
        if(status){
            result = new Result(1,null,"更新成功");
        }else{
            result = new Result(0,null,null);
        }
        return result;
    }

    private ArrayList<String> getColumnNames(SqlRowSet sqlRowSet){
        SqlRowSetMetaData smd = sqlRowSet.getMetaData();
        int columnCount = smd.getColumnCount();
        ArrayList<String> columnNames = new ArrayList<String>();
        for(int i = 1;i < columnCount;i++){
            columnNames.add(smd.getColumnName(i));
        }
        return columnNames;
    }

    private JSONArray getJsonObjectList(SqlRowSet sqlRowSet,ArrayList<String> colname){
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
