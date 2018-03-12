package com.iip.datafusion.dgs.Consistency.parser;

import com.iip.datafusion.backend.job.JobType;
import com.iip.datafusion.backend.job.consistency.ConsistencyJob;
import com.iip.datafusion.dgs.Consistency.model.CheckBasicConsistencyConfiguration;

import java.util.ArrayList;

public class CheckBasicConsistencyParser {
    public static ConsistencyJob parse(CheckBasicConsistencyConfiguration checkBasicConsistencyConfiguration){

        String dataSourceId1 = checkBasicConsistencyConfiguration.getDataSourceId1();
        String dataSourceId2 = checkBasicConsistencyConfiguration.getDataSourceId2();
        String tableName1 = checkBasicConsistencyConfiguration.getTableName1();
        String tableName2 = checkBasicConsistencyConfiguration.getTableName2();
        String columnname1 = checkBasicConsistencyConfiguration.getColumnName1();
        String columnname2 = checkBasicConsistencyConfiguration.getColumnName2();
        String primary_key1 = checkBasicConsistencyConfiguration.getPrimary_key1();
        String primary_key2 = checkBasicConsistencyConfiguration.getPrimary_key2();

        String selectClause1 = primary_key1 + "," + columnname1;
        String selectClause2 = primary_key2 + "," + columnname2;
        String whereClause = "1=1";

        String sql1 = String.format("SELECT %s FROM %s where %s",selectClause1,tableName1,whereClause);
        String sql2 = String.format("SELECT %s FROM %s where %s",selectClause2,tableName2,whereClause);

        ConsistencyJob ConsistencyJob = new ConsistencyJob();
        ConsistencyJob.setmainDatasourceID(dataSourceId1);
        ConsistencyJob.setmainTableName(tableName1);
        ConsistencyJob.setmainColumnName(columnname1);
        ConsistencyJob.setPrimary_key1(primary_key1);
        ConsistencyJob.setPrimary_key2(primary_key2);
        ConsistencyJob.setfollowDatasourceID(dataSourceId2);
        ConsistencyJob.setfollowTableName(tableName2);
        ConsistencyJob.setfollowColumnName(columnname2);
        ArrayList<String> sqlList = new ArrayList<>();
        sqlList.add(sql1);
        sqlList.add(sql2);
        ConsistencyJob.setSqlList(sqlList);
//        ConsistencyJob ConsistencyJob = new ConsistencyJob();
//        ConsistencyJob.setDataSourceId(dataSourceId1);
//        ConsistencyJob.setTableName(tableName1);
//        ConsistencyJob.setColumnName(columnname1);
//        ArrayList<String> sqlList = new ArrayList<>();
//        sqlList.add(sql1);
//        sqlList.add(sql2);
//        ConsistencyJob.setSqlList(sqlList);
//        ConsistencyJob.setJobType("consistency");

        return ConsistencyJob;

    }
}
