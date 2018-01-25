package com.iip.datafusion.dgs.integrity;

import com.iip.datafusion.backend.job.integrity.IntegrityJob;
import com.iip.datafusion.backend.parser.Parser;

import java.util.ArrayList;

/**
 * @author zengc
 * @date 2018/1/21 10:21
 */
public class IntegrityParser implements Parser {

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
                whereClause += String.format(" ISNULL(%s) or", columnName);
            } else {
                throw new Exception("传入参数属性值不能为空");
                //return null;
            }
        }

        whereClause = whereClause.substring(0, whereClause.lastIndexOf("or"));
        String sql = String.format("SELECT %s FROM %s where %s"," * ",tableName,whereClause);

        //System.out.println(sql);


        IntegrityJob integrityJob = new IntegrityJob();
        integrityJob.setDataSourceId(dataSourceId);
        integrityJob.setTableName(tableName);
        ArrayList<String> sqlList = new ArrayList<>();
        sqlList.add(sql);
        integrityJob.setSqlList(sqlList);
        integrityJob.setJobType("query");

        return integrityJob;

    }


}
