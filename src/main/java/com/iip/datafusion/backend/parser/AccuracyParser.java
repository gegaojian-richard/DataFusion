package com.iip.datafusion.backend.parser;

import com.iip.datafusion.backend.job.accuracy.AccuracyJob;
import com.iip.datafusion.dgs.model.accuracy.*;

import java.util.ArrayList;
import java.util.List;

public class AccuracyParser implements Parser {

    public static AccuracyJob parser(AccuracyConfiguration accuracyConfiguration){

        AccuracyJob accuracyJob = new AccuracyJob(accuracyConfiguration.getDataSourceId(),
                                                  accuracyConfiguration.getTableName());
        List<String> paramStrings = accuracyConfiguration.getParamStrings();
        List<Param> paramList = new ArrayList<Param>();
        for(String paramString : paramStrings){
            paramList.add(parserParam(paramString));
        }
        accuracyJob.setParamList(paramList);
        return accuracyJob;
    }

    private static Param parserParam(String paramString){
        Param param;
        String[] split = paramString.split(",");
        int type = Integer.parseInt(split[0].trim());
        switch (type){
            case 1 :
                List<ColumnAttributeValue> columnAttributeValues = new ArrayList<ColumnAttributeValue>();
                for(int i = 2;i < split.length;i++){
                    String tmp = split[i].replaceAll(" ","");
                    tmp = tmp.substring(1,tmp.length() - 1);
                    String colunm = tmp.substring(0,tmp.indexOf(":"));
                    String[] avs = tmp.substring(tmp.indexOf(":") + 1).split("}");
                    List<AttributeValue> attributeValues = new ArrayList<AttributeValue>();
                    for(int j = 0;j < avs.length;j++){
                        String tmp1 = avs[j].trim().substring(1,avs[j].length());
                        String[] av = tmp1.split(":");
                        AttributeValue attributeValue = new AttributeValue(av[0],av[1]);
                        attributeValues.add(attributeValue);
                    }
                    ColumnAttributeValue columnAttributeValue = new ColumnAttributeValue(colunm,attributeValues);
                    columnAttributeValues.add(columnAttributeValue);
                }
                param = new FormulaParam(type,split[1].trim(),columnAttributeValues);
                break;
            case 2 :
                String[] cvs = split[2].replaceAll(" ","").split("}");
                List<ConditionValue> conditionValues = new ArrayList<ConditionValue>();
                for(int i = 0;i < cvs.length;i++){
                    String tmp = cvs[i].substring(1,cvs[i].length());
                    String[] cv = tmp.split(":");
                    ConditionValue conditionValue = new ConditionValue(cv[0].trim(),cv[1].trim());
                    conditionValues.add(conditionValue);
                }
                param = new ConditionParam(type,split[1].trim(),conditionValues);
                break;
            case 3 :
                param = new LengthParam(type,split[1].trim(),split[2].trim());
                break;
            case 4 :
                param = new RangeParam(type,split[1].trim(),split[2].trim());
                break;
            case 5 :
                param = new EmailParam(type,split[1].trim());
                break;
            default:
                param = new Param(0);
                break;
        }
        return param;
    }
}
