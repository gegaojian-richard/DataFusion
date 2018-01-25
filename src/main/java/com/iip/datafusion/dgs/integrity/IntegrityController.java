package com.iip.datafusion.dgs.integrity;

import com.iip.datafusion.backend.job.integrity.IntegrityJob;
import com.iip.datafusion.dgs.model.*;
import com.iip.datafusion.dgs.integrity.UpdateIntegrityConfiguration;
import com.iip.datafusion.dgs.integrity.IntegrityConfiguration;
import com.iip.datafusion.dgs.integrity.UpdateIntegrityParser;
import com.iip.datafusion.dgs.service.CommonService;
import com.iip.datafusion.dgs.integrity.IntegrityService;
import com.iip.datafusion.util.jsonutil.Result;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;
import java.util.ArrayList;
import java.util.Map;


@Controller
public class IntegrityController {
    @Autowired
    CommonService commonService;

    @Autowired
    IntegrityService integrityService;

    private int maxThreads = 3;


    @RequestMapping(path = {"/dgs/checkIntegrity"}, method = RequestMethod.POST)
    @ResponseBody
    public Result checkIntegrity(@RequestBody IntegrityConfiguration integrityConfiguration) {

        try{
            IntegrityJob integrityJob = integrityService.commitJob(integrityConfiguration);
            while (integrityJob.getResult()==null){

            }
            return integrityJob.getResult();
        }catch (Exception e){
            return new Result(0,e.getMessage(),null);
        }
        //return new Result();
    }
    /*
    @RequestMapping(path = {"/dgs/updateIntegrity"}, method = RequestMethod.POST)
    @ResponseBody
    public Result updateIntegrity(@RequestBody UpdateIntegrityConfiguration updateIntegrityConfiguration) {
        try{
            IntegrityJob integrityJob = UpdateIntegrityParser.parse(updateIntegrityConfiguration);
            return integrityJob.run();
        }catch (Exception e){
            return new Result(0,e.getMessage(),null);
        }
        //return new Result();
    }
    */



    @RequestMapping(path = {"/dgs/integrity/tableAnyEmptyCheck"}, method = RequestMethod.POST)
    @ResponseBody
    public Result tableAnyEmptyCheck(@RequestBody TableAnyEmptyCheckParam tableAnyEmptyCheckParam) {

        String dataSourceId = tableAnyEmptyCheckParam.getDataSourceId();
        String tableName = tableAnyEmptyCheckParam.getTableName();


        ArrayList<String> inputColumnNames = tableAnyEmptyCheckParam.getColumnNames();
        ArrayList<String> trueColumnNames = commonService.getTableColumnList(dataSourceId, tableName);

        if (inputColumnNames == null || inputColumnNames.size() <= 0) {
            return new Result(0, "传入参数属性值不能为空", null);
        }

        String whereClause = "";
        for (String columnName : inputColumnNames) {

            if (trueColumnNames.contains(columnName)) {
                whereClause += String.format(" ISNULL(%s) or", columnName);
            } else {
                return new Result(0, "传入参数不正确", null);
            }
        }

        whereClause = whereClause.substring(0, whereClause.lastIndexOf("or"));
        SqlRowSet sqlRowSet = commonService.doSelect(dataSourceId, tableName, "*",whereClause);

        try {
            String json = rowSetToJson(sqlRowSet);
            return new Result(1, null, json);
        } catch (Exception e) {
            return new Result(0, "出现内部错误", null);
        }


    }

    @RequestMapping(path = {"/dgs/integrity/tableAllCompleteCheck"}, method = RequestMethod.POST)
    @ResponseBody
    public Result tableAllCompleteCheck(@RequestBody TableAllCompleteCheckParam tableAllCompleteCheckParam) {

        String dataSourceId = tableAllCompleteCheckParam.getDataSourceId();
        String tableName = tableAllCompleteCheckParam.getTableName();
        ArrayList<String> trueColumnNames = commonService.getTableColumnList(dataSourceId, tableName);

        TableAnyEmptyCheckParam tableAnyEmptyCheckParam = new TableAnyEmptyCheckParam();
        tableAnyEmptyCheckParam.setColumnNames(trueColumnNames);
        tableAnyEmptyCheckParam.setDataSourceId(dataSourceId);
        tableAnyEmptyCheckParam.setTableName(tableName);

        return tableAnyEmptyCheck(tableAnyEmptyCheckParam);

    }

    @RequestMapping(path = {"/dgs/integrity/tablesAnyEmptyCheck"}, method = RequestMethod.POST)
    @ResponseBody
    public Result tablesAnyEmptyCheck(@RequestBody TablesAnyEmptyCheckParam tablesAnyEmptyCheckParam)
    {
        String dataSourceId = tablesAnyEmptyCheckParam.getDataSourceId();
        Map<String,List<String>> tablesValue = tablesAnyEmptyCheckParam.getTablesValue();

        ArrayList<String> tablesList = new ArrayList<>();
        ArrayList<List<String>> columnsList = new ArrayList<>();

        for(String key:tablesValue.keySet()){
            tablesList.add(key);
            columnsList.add(tablesValue.get(key));
        }

        class SqlResult{
            public SqlRowSet sqlRowSet;
            public String msg;

            public SqlResult(SqlRowSet sqlRowSet,String msg){
                this.sqlRowSet = sqlRowSet;
                this.msg = msg;
            }
        }
        ExecutorService threadPool = Executors.newFixedThreadPool(maxThreads);

        CompletionService<SqlResult> completionService = new ExecutorCompletionService<SqlResult>(
                threadPool);

        //提交多个任务
        for(int i = 0;i<tablesList.size();i++)
        {
            final String threadDataSourceID = dataSourceId;
            final String threadTableName = tablesList.get(i);
            final List<String> threadColumnNames = columnsList.get(i);


            completionService.submit(new Callable<SqlResult>() {
                @Override
                public SqlResult call() throws Exception {

                    ArrayList<String> trueColumnNames = commonService.getTableColumnList(threadDataSourceID, threadTableName);
                    //System.out.println(trueColumnNames);
                    //System.out.println(threadColumnNames);
                    if (threadColumnNames == null || threadColumnNames.size() <= 0) {
                        return new SqlResult(null,"传入参数属性值不能为空\n");
                    }

                    String whereClause = "";
                    for (String columnName : threadColumnNames) {

                        if (trueColumnNames.contains(columnName)) {
                            whereClause += String.format(" ISNULL(%s) or", columnName);
                        } else {
                            //System.out.println(columnName+"here");
                            return new SqlResult(null,columnName+"传入参数不正确\n");
                        }
                    }

                    whereClause = whereClause.substring(0, whereClause.lastIndexOf("or"));
                    SqlRowSet sqlRowSet = commonService.doSelect(threadDataSourceID, threadTableName," * ", whereClause);
                    return new SqlResult(sqlRowSet,threadTableName);

                }
            });
        }

        String jsonMsg = "";
        Map<String,String> resMap = new HashMap<>();
        try {
            //得到线程执行完之后的结果，那个线程任务先执行完就先返回，因为提交了10个任务
            //所以得到的时候也要分多次得到
            for (int i = 0; i < tablesList.size(); i++) {
                SqlResult threadRes = completionService.take().get();
                if(threadRes.sqlRowSet == null) {
                    jsonMsg += threadRes.msg;
                }else{
                    resMap.put(threadRes.msg,rowSetToJson(threadRes.sqlRowSet));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(resMap.size()>0) {
            try {
                String jsonResult = JSONObject.fromObject(resMap).toString();
                return new Result(1, jsonMsg, jsonResult);
            }catch (Exception e){
                return new Result(0, jsonMsg, null);
            }
        }
        else
            return new Result(0, jsonMsg, null);




        //return new Result(0,null,null);
    }

    @RequestMapping(path = {"/dgs/integrity/updateColumns"}, method = RequestMethod.POST)
    @ResponseBody
    public Result updateColumns(@RequestBody UpdateColumnsParam updateColumnsParam) {

        String dataSourceId = updateColumnsParam.getDataSourceId();
        String tableName = updateColumnsParam.getTableName();
        Map<String,Object> updateValues= updateColumnsParam.getUpdateValues();
        //System.out.println(dataSourceId+"\n"+tableName+"\n"+updateValues.size());
        ArrayList<String> updateClauses = new ArrayList<>();
        ArrayList<String> whereClauses = new ArrayList<>();
        for (String key:updateValues.keySet()){
            String updateClause = " " + key+" = "+updateValues.get(key);
            //System.out.println(updateClause+" "+updateValues.get(key).toString());
            String whereClause = String.format(" ISNULL(%s) ",key);
            updateClauses.add(updateClause);
            whereClauses.add(whereClause);
            //System.out.println(updateClause);
            //System.out.println(whereClause);
        }


        ExecutorService threadPool = Executors.newFixedThreadPool(maxThreads);

        CompletionService<String> completionService = new ExecutorCompletionService<String>(
                threadPool);

        //提交多个任务
        for(int i = 0;i<updateClauses.size();i++)
        {
            final String threadDataSourceID = dataSourceId;
            final String threadTableName = tableName;
            final String threadUpdateClause = updateClauses.get(i);
            final String threadWhereClause = whereClauses.get(i);
            completionService.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    //Thread.sleep(1000);
                    if(commonService.doUpdate(threadDataSourceID,threadTableName,threadUpdateClause,threadWhereClause)){
                        return " ";
                    }
                    else
                        return threadUpdateClause+" failed!\n";
                }
            });
        }

        String jsonResult = "";
        try {
            //得到线程执行完之后的结果，那个线程任务先执行完就先返回，因为提交了10个任务
            //所以得到的时候也要分多次得到
            for (int i = 0; i < updateClauses.size(); i++) {
                String threadRes = completionService.take().get();
                if(!threadRes.equals(" "))
                    jsonResult += threadRes;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(jsonResult.equals(""))
            return new Result(1, null, null);
        else
            return new Result(0, jsonResult, null);

    }

    @RequestMapping(path = {"/dgs/integrity/updateItems"}, method = RequestMethod.POST)
    @ResponseBody
    public Result updateItems(@RequestBody UpdateItemsParam updateItemsParam) {

        String dataSourceId = updateItemsParam.getDataSourceId();
        String tableName = updateItemsParam.getTableName();
        Map<String,String> updateValues= updateItemsParam.getUpdateValues();
        //System.out.println(dataSourceId+"\n"+tableName+"\n"+updateValues.size());
        ArrayList<String> updateClauses = new ArrayList<>();
        ArrayList<String> whereClauses = new ArrayList<>();
        for (String key:updateValues.keySet()){
            updateClauses.add(key);
            whereClauses.add(updateValues.get(key));
        }

        ExecutorService threadPool = Executors.newFixedThreadPool(maxThreads);

        CompletionService<String> completionService = new ExecutorCompletionService<String>(
                threadPool);

        //提交多个任务
        for(int i = 0;i<updateClauses.size();i++)
        {
            final String threadDataSourceID = dataSourceId;
            final String threadTableName = tableName;
            final String threadUpdateClause = updateClauses.get(i);
            final String threadWhereClause = whereClauses.get(i);
            completionService.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    //Thread.sleep(1000);
                    if(commonService.doUpdate(threadDataSourceID,threadTableName,threadUpdateClause,threadWhereClause)){
                        return " ";
                    }
                    else
                        return threadUpdateClause+" failed!\n";
                }
            });
        }

        String jsonResult = "";
        try {
            //得到线程执行完之后的结果，那个线程任务先执行完就先返回，因为提交了10个任务
            //所以得到的时候也要分多次得到
            for (int i = 0; i < updateClauses.size(); i++) {
                String threadRes = completionService.take().get();
                if(!threadRes.equals(" "))
                    jsonResult += threadRes;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(jsonResult.equals(""))
            return new Result(1, null, null);
        else
            return new Result(0, jsonResult, null);
    }

    @RequestMapping(path = {"/dgs/integrity/updateItemsByInstance"}, method = RequestMethod.POST)
    @ResponseBody
    public Result updateItemsByInstance(@RequestBody UpdateItemsByInstanceParam updateItemsByInstanceParam) {
        String dataSourceId = updateItemsByInstanceParam.getDataSourceId();
        String tableName = updateItemsByInstanceParam.getTableName();
        Map<String,String> valueMap = updateItemsByInstanceParam.getIntanceValues();
        ArrayList<String> trueColumnNames = commonService.getTableColumnList(dataSourceId, tableName);
        String columnClause = "(";
        String valueClause  = "(";
        for(String key:valueMap.keySet()){
            if(trueColumnNames.contains(key)) {
                columnClause += key + ",";
                valueClause += valueMap.get(key) + ",";
            }else
                return new Result(0,key+" 属性不存在",null);
        }
        columnClause = columnClause.substring(0,columnClause.length()-1);
        columnClause += ") ";
        //System.out.println(columnClause);
        valueClause = valueClause.substring(0,valueClause.length()-1);
        valueClause += ") ";
        //System.out.println(valueClause);


        try {
            boolean res = commonService.doReplace(dataSourceId,tableName,columnClause,valueClause);
            if(res)
                return new Result(1, null, null);
            else
                return new Result(0, "出现内部错误", null);
        } catch (Exception e) {
            return new Result(0, "出现内部错误", null);
        }

    }

    private String rowSetToJson(SqlRowSet sqlRowSet) {
        // json数组
        SqlRowSetMetaData sqlRsmd = sqlRowSet.getMetaData();
        ArrayList<String> trueColumnNames = new ArrayList<>();
        for(int i=1;i<=sqlRsmd.getColumnCount();i++){
            trueColumnNames.add(sqlRsmd.getColumnName(i));

        }

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
        //System.out.println(array.toString());
        return array.toString();


    }


}
