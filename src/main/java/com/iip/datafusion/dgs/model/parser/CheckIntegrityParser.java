package com.iip.datafusion.dgs.model.parser;

import com.iip.datafusion.backend.job.integrity.IntegrityJob;
import com.iip.datafusion.dgs.model.configuration.CheckIntegrityConfiguration;
import com.iip.datafusion.util.jsonutil.Result;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.ArrayList;

/**
 * @author zengc
 * @date 2018/1/19 15:38
 */
public class CheckIntegrityParser {

    public static IntegrityJob parse(CheckIntegrityConfiguration checkIntegrityConfiguration)throws Exception{

        String dataSourceId = checkIntegrityConfiguration.getDataSourceId();
        String tableName = checkIntegrityConfiguration.getTableName();


        ArrayList<String> inputColumnNames = checkIntegrityConfiguration.getColumnNames();

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

        System.out.println(sql);


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
