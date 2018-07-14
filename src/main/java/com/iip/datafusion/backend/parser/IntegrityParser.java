package com.iip.datafusion.backend.parser;

import com.iip.datafusion.backend.job.JobType;
import com.iip.datafusion.backend.JobRegistry;
import com.iip.datafusion.backend.job.integrity.IntegrityJob;
import com.iip.datafusion.dgs.model.integrity.IntegrityConfiguration;
import com.iip.datafusion.redis.model.JobRandom;

import java.util.ArrayList;

/**
 * 完整性检查解析器
 * Created by GeGaojian on 2018/01/18.
 */
public class IntegrityParser implements Parser{

    public static IntegrityJob parse(IntegrityConfiguration integrityConfiguration)throws Exception{

        String dataSourceId = integrityConfiguration.getDataSourceId();
        String tableName = integrityConfiguration.getTableName();


        ArrayList<String> inputColumnNames = integrityConfiguration.getColumnNames();


        if (inputColumnNames == null || inputColumnNames.size() <= 0) {
            throw new Exception("传入参数属性值不能为空");
            //return null;
        }

        String whereClause = "";
        for (String columnName : inputColumnNames) {

            if (columnName!=null) {
                whereClause += String.format(" ISNULL(%s) or %s=\"\" or ", columnName,columnName);
            } else {
                throw new Exception("传入参数属性值不能为空");
                //return null;
            }
        }

        whereClause = whereClause.substring(0, whereClause.lastIndexOf("or"));
        String sql = String.format("SELECT %s FROM %s where %s"," * ",tableName,whereClause);



        IntegrityJob integrityJob = new IntegrityJob();
        integrityJob.setDataSourceId(dataSourceId);
        integrityJob.setTableName(tableName);
        ArrayList<String> sqlList = new ArrayList<>();
        sqlList.add(sql);
        integrityJob.setSqlList(sqlList);
        integrityJob.setJobType(JobType.INTEGRITY);
        integrityJob.setColumnNames(inputColumnNames);

        integrityJob.setInnerJobType("query");
        //JobRegistry.getInstance().regist(integrityJob);
        return integrityJob;

    }


}