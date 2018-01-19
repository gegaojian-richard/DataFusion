package com.iip.datafusion.dgs.controller;

import com.iip.datafusion.dgs.model.*;
import com.iip.datafusion.dgs.service.CommonService;
import com.iip.datafusion.util.jsonutil.Result;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@Controller
public class AccuracyController {

    @Autowired
    private CommonService commonService;

    @RequestMapping(path = "/dgs/accuracy/formulaCheck",method = RequestMethod.POST)
    @ResponseBody
    public Result formulaCheck(@RequestBody FormulaCheckParam formulaCheckParam){
        String dataSourceId = formulaCheckParam.getDataSourceId();
        String tableName = formulaCheckParam.getTableName();
        String whereClause = formulaCheckParam.getWhereClause();
        ArrayList<ColumnAttributeValue> columnAttributeValues = formulaCheckParam.getColumnAttributeValues();

        //如果where子句中的字段存在语义的对应关系
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

        try{
            JSONArray jsonArray = new JSONArray();
            //where子句是一个等式
            if(newWhereClause.contains("="))
            {
                String[] splits = newWhereClause.split("=");
                String selectClause = "* , " + splits[1].trim() + " AS newValue";
                String columnName = splits[0].trim();
                newWhereClause = "NOT(" + newWhereClause + ")";
                SqlRowSet sqlRowSet = commonService.doSelect(dataSourceId,tableName,selectClause,newWhereClause);

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
            //where子句是一个限制条件
            else{
                newWhereClause = "NOT(" + newWhereClause + ")";
                String selectClause = "*";
                SqlRowSet sqlRowSet = commonService.doSelect(dataSourceId,tableName,selectClause,newWhereClause);

                ArrayList<String> columnNames = getColumnNames(sqlRowSet);
                JSONArray resultSet = getJsonObjectList(sqlRowSet,columnNames);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("resultSet", resultSet);
                jsonObject.put("message","应满足：" + whereClause);

                jsonArray.add(jsonObject);
            }

            Result result = new Result(1,null,jsonArray.toString());
            return result;
        }catch (Exception e){
            Result result = new Result(0,"出现内部错误",null);
            return result;
        }
    }

    @RequestMapping(path = "/dgs/accuracy/lengthCheck",method = RequestMethod.POST)
    @ResponseBody
    public Result lengthCheck(@RequestBody LengthCheckParam lengthCheckParam){
        String dataSourceId = lengthCheckParam.getDataSourceId();
        String tableName = lengthCheckParam.getTableName();
        String columnName = lengthCheckParam.getColumnName();
        String length = lengthCheckParam.getLength();

        String selectClause = "*";
        String whereClause = "length("+ columnName + ") <> " + length;

        try{
            JSONArray jsonArray = new JSONArray();
            SqlRowSet sqlRowSet = commonService.doSelect(dataSourceId,tableName,selectClause,whereClause);
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

    @RequestMapping(path = "/dgs/accuracy/conditionCheck",method = RequestMethod.POST)
    @ResponseBody
    public Result conditionCheck(@RequestBody ConditionCheckParam conditionCheckParam){
        String dataSourceId = conditionCheckParam.getDataSourceId();
        String tableName = conditionCheckParam.getTableName();
        String columnName = conditionCheckParam.getColumnName();
        ArrayList<ConditionValue> conditionValues = conditionCheckParam.getConditionValues();

        try {
            JSONArray jsonArray = new JSONArray();
            String whereClause;
            for(ConditionValue cv : conditionValues) {
                String condition = cv.getCondition();
                String value = cv.getValue();

                whereClause = "NOT(CASE WHEN " + condition + " THEN " + columnName + " = " + value + " END)";
                String selectClause = "*";
                SqlRowSet sqlRowSet = commonService.doSelect(dataSourceId, tableName, selectClause, whereClause);

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

    @RequestMapping(path = "/dgs/accuracy/emailCheck",method = RequestMethod.POST)
    @ResponseBody
    public Result emailCheck(@RequestBody EmailCheckParam emailCheckParam){
        String dataSourceId = emailCheckParam.getDataSourceId();
        String tableName = emailCheckParam.getTableName();
        String columnName = emailCheckParam.getColumnName();
        String whereClause = columnName +" REGEXP '^[a-zA-Z0-9]+[a-zA-Z0-9_-]*@[a-zA-Z0-9]+([\\.][a-zA-Z0-9]+){1,}$'";
        whereClause = "NOT(" + whereClause + ")";
        String selectClause = "*";
        try{
            SqlRowSet sqlRowSet = commonService.doSelect(dataSourceId,tableName,selectClause,whereClause);

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

    @RequestMapping(path = "/dgs/accuracy/update",method = RequestMethod.POST)
    @ResponseBody
    public Result update(@RequestBody UpdateParam updateParam){
        String dataSourceId = updateParam.getDataSourceId();
        String tableName = updateParam.getTableName();
        ArrayList<Map<String,String>> newValues = updateParam.getNewValues();

        ArrayList<String> trueColumnNames = commonService.getTableColumnList(dataSourceId,tableName);

        boolean status = true;

        JSONArray jsonArray = new JSONArray();
        for(Map<String,String> newValue : newValues){

            String columnClause = "(";
            String valueClause  = "(";
            for(String key:newValue.keySet()){
                if(trueColumnNames.contains(key)) {
                    columnClause += key + ",";
                    valueClause += newValue.get(key) + ",";
                }else
                    return new Result(0,key + " 属性不存在",null);
            }

            columnClause = columnClause.substring(0,columnClause.length()-1);
            columnClause += ") ";
            valueClause = valueClause.substring(0,valueClause.length()-1);
            valueClause += ") ";

            try{
                boolean res = commonService.doReplace(dataSourceId,tableName,columnClause,valueClause);
            }catch(Exception e){
                status = false;
            }
        }

        Result result;
        if(status){
            result = new Result(1,null,"更新成功");
        }else{
            result = new Result(0,null,"更新失败");
        }
        return result;
    }

//    @RequestMapping(path = "/dgs/accuracy/formulaUpdate",method = RequestMethod.POST)
//    @ResponseBody
//    public Result formulaUpdate(@RequestBody FormulaUpdateParam formulaUpdateParam){
//        String dataSourceId = formulaUpdateParam.getDataSourceId();
//        String tableName = formulaUpdateParam.getTableName();
//        String setClause = formulaUpdateParam.getSetClause();
//        String whereClause = formulaUpdateParam.getWhereClause();
//
//        //whereClause = "NOT(" + whereClause +")";
//        boolean status = accuracyService.updateData(dataSourceId,tableName,setClause,whereClause);
//
//        Result result;
//        if(status){
//            result = new Result(1,null,"更新成功");
//        }else{
//            result = new Result(0,null,null);
//        }
//        return result;
//    }
//
//    @RequestMapping(path = "/dgs/accuracy/lengthUpdate",method = RequestMethod.POST)
//    @ResponseBody
//    public Result lengthUpdate(@RequestBody LengthUpdateParam lengthUpdateParam) {
//        String dataSourceId = lengthUpdateParam.getDataSourceId();
//        String tableName = lengthUpdateParam.getTableName();
//        String columnName = lengthUpdateParam.getColumnName();
//        String length = lengthUpdateParam.getLength();
//        String fill = lengthUpdateParam.getFill();
//
//        String whereClause = "length(" + columnName + ") < " + length;
//        SqlRowSet sqlRowSet = accuracyService.selectData(dataSourceId, tableName, whereClause);
//
//        ArrayList<String> columnNames = getColumnNames(sqlRowSet);
//        boolean flag = true;
//        JSONArray jsonArray = new JSONArray();
//        JSONArray resultSet = new JSONArray();
//        while (sqlRowSet.next()) {
//            String oldValue = sqlRowSet.getString(columnName);
//            String newValue = oldValue;
//            while (newValue.length() < Integer.parseInt(length)) {
//                newValue += fill;
//            }
//            boolean status = accuracyService.updateData(dataSourceId, tableName, columnName, newValue, whereClause);
//            if (!status) {
//                flag = false;
//                JSONObject jo = new JSONObject();
//                for (String name : columnNames) {
//                    jo.put(name, sqlRowSet.getString(name));
//                }
//                resultSet.add(jo);
//            }
//        }
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("resultSet", resultSet);
//        jsonObject.put("message", "更新失败");
//        jsonArray.add(jsonObject);
//
//        Result result;
//        if (flag) {
//            result = new Result(1, null, "全部更新成功");
//        } else {
//            result = new Result(0, null, jsonArray.toString());
//        }
//        return result;
//    }
//

    private ArrayList<String> getColumnNames(SqlRowSet sqlRowSet){
        SqlRowSetMetaData smd = sqlRowSet.getMetaData();
        int columnCount = smd.getColumnCount();
        ArrayList<String> columnNames = new ArrayList<String>();
        for(int i = 1;i <= columnCount;i++){
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
