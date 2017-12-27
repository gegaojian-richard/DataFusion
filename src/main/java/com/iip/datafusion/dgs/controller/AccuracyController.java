package com.iip.datafusion.dgs.controller;

import com.iip.datafusion.dgs.service.AccuracyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class AccuracyController {

    @Autowired
    private AccuracyService accuracyService;

    @RequestMapping(path = "/dgs/accuracy/formulaCheck",method = RequestMethod.POST)
    @ResponseBody
    public String formulaCheck(@RequestParam(value = "dataSourceId",required=false) String dataSourceId,
                               @RequestParam(value = "tableName",required=false) String tableName,
                               @RequestParam(value = "whereClause",required=false) String whereClause){
        whereClause = "NOT(" + whereClause + ")";
        SqlRowSet sqlRowSet = accuracyService.selectData(dataSourceId,tableName,whereClause);
        return "formulaCheck!";
    }

    @RequestMapping(path = "/dgs/accuracy/formulaUpdate",method = RequestMethod.POST)
    @ResponseBody
    public String formulaUpdate(@RequestParam(value = "dataSourceId",required=false) String dataSourceId,
                                @RequestParam(value = "tableName",required=false) String tableName,
                                @RequestParam(value = "setClause",required=false) String setClause,
                                @RequestParam(value = "whereClause",required=false) String whereClause){
        accuracyService.updateData2(dataSourceId,tableName,setClause,whereClause);
        return "formulaUpdate!";
    }
    @RequestMapping(path = "/dgs/accuracy/lengthCkeck",method = RequestMethod.POST)
    @ResponseBody
    public String lengthCkeck(@RequestParam(value = "dataSourceId",required=false) String dataSourceId,
                              @RequestParam(value = "tableName",required=false) String tableName,
                              @RequestParam(value = "columnName",required=false) String columnName,
                              @RequestParam(value = "length",required=false) Integer length,
                              @RequestParam(value = "fill",required=false) String fill){
        String whereClause = "length("+ columnName + ") < " + length;
        SqlRowSet sqlRowSet = accuracyService.selectData(dataSourceId,tableName,whereClause);
        return  "lengthCkeck!";
    }

    @RequestMapping(path = "/dgs/accuracy/lengthUpdate",method = RequestMethod.POST)
    @ResponseBody
    public String lengthUpdate(@RequestParam(value = "dataSourceId",required=false) String dataSourceId,
                               @RequestParam(value = "tableName",required=false) String tableName,
                               @RequestParam(value = "columnName",required=false) String columnName,
                               @RequestParam(value = "length",required=false) Integer length,
                               @RequestParam(value = "fill",required=false) String fill,
                               @RequestParam(value = "whereClause",required=false) String whereClause){
        SqlRowSet sqlRowSet = accuracyService.selectData(dataSourceId,tableName,whereClause);
        while(sqlRowSet.next()){
            String oldValue = sqlRowSet.getString(columnName);
            String newValue = oldValue;
            while(newValue.length()<length){
                newValue += fill;
            }
            accuracyService.updateData(dataSourceId,tableName,columnName,newValue,whereClause);
        }
        return "lengthUpdate!";
    }

    @RequestMapping(path = "/dgs/accuracy/conditionCheck",method = RequestMethod.POST)
    @ResponseBody
    public String conditionCheck(@RequestParam(value = "dataSourceId",required=false) String dataSourceId,
                                 @RequestParam(value = "tableName",required=false) String tableName,
                                 @RequestParam(value = "columnName",required=false) String columnName,
                                 @RequestBody Map<String,String> map){
        for(String condition : map.keySet()){
            String whereClause = "NOT(CASE WHEN " + condition +" THEN " + columnName + " = " + map.get(condition) + " END)";
            accuracyService.selectData(dataSourceId,tableName,whereClause);
            System.out.println("建议"+ columnName +"改为：" + map.get(condition));
        }
        return "conditionCheck!";
    }

    @RequestMapping(path = "/dgs/accuracy/update",method = RequestMethod.POST)
    @ResponseBody
    public String update(@RequestParam(value = "dataSourceId",required=false) String dataSourceId,
                         @RequestParam(value = "tableName",required=false) String tableName,
                         @RequestParam(value = "columnName",required=false) String columnName,
                         @RequestParam(value = "newValue",required=false) String newValue,
                         @RequestParam(value = "whereClause",required=false) String whereClause){
        accuracyService.updateData(dataSourceId,tableName,columnName,newValue,whereClause);
        return "check!";
    }

}
