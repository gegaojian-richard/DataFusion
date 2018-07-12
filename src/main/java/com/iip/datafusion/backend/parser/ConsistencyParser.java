package com.iip.datafusion.backend.parser;

import com.iip.datafusion.backend.job.JobType;
import com.iip.datafusion.backend.job.consistency.ConsistencyJob;
import com.iip.datafusion.dgs.model.consistency.ConsistencyConfiguration;
import com.iip.datafusion.dgs.model.consistency.MapEntries;

import java.util.ArrayList;
/**
 * 一致性检查工作描述解析器
 * Created by GeGaojian on 2018/01/18.
 */
public class ConsistencyParser {
    public static ArrayList<ConsistencyJob> parse(ConsistencyConfiguration ConsistencyConfiguration){

        ArrayList<ConsistencyJob>  ConsistencyJoblist=new ArrayList<>();
        for (MapEntries m : ConsistencyConfiguration.getMapEntries()) {

            String mainDataSourceId = ConsistencyConfiguration.getmainDataSourceId();
            String mainTableName = ConsistencyConfiguration.getmainTableName();
            ConsistencyJob ConsistencyJob = new ConsistencyJob();

            String[] temp = m.getKey().split(",");
            String mainColumnName = temp[0];
            String mainPrimary_key = temp[1];
            String[] temp2 = m.getValue().split(",");
            String followDataSourceID = temp2[0];
            String followTableName = temp2[1];
            String followColumnName = temp2[2];
            String followPrimary_key = temp2[3];


            String selectClause1 = mainPrimary_key + "," + mainColumnName;
            String selectClause2 = followPrimary_key + "," + followColumnName;
            String whereClause = "1=1";

            String sql1 = String.format("SELECT %s FROM %s where %s",selectClause1,mainTableName,whereClause);
            String sql2 = String.format("SELECT %s FROM %s where %s",selectClause2,followTableName,whereClause);


            ConsistencyJob.setmainDatasourceID(mainDataSourceId);
            ConsistencyJob.setmainTableName(mainTableName);
            ConsistencyJob.setmainColumnName(mainColumnName);
            ConsistencyJob.setmainPrimary_key(mainPrimary_key);
            ConsistencyJob.setfollowPrimary_key(followPrimary_key);
            ConsistencyJob.setfollowDatasourceID(followDataSourceID);
            ConsistencyJob.setfollowTableName(followTableName);
            ConsistencyJob.setfollowColumnName(followColumnName);
            ArrayList<String> sqlList = new ArrayList<>();
            sqlList.add(sql1);
            sqlList.add(sql2);
            ConsistencyJob.setSqlList(sqlList);
            ConsistencyJob.setJobType(JobType.CONSISTENCY);
            ConsistencyJob.setInnerJobType("query");
            ConsistencyJoblist.add(ConsistencyJob);

        }
//        String columnname1 = checkBasicConsistencyConfiguration.getColumnName1();
//        String columnname2 = checkBasicConsistencyConfiguration.getColumnName2();
//        String primary_key1 = checkBasicConsistencyConfiguration.getPrimary_key1();
//        String primary_key2 = checkBasicConsistencyConfiguration.getPrimary_key2();
//
//        String selectClause1 = primary_key1 + "," + columnname1;
//        String selectClause2 = primary_key2 + "," + columnname2;
//        String whereClause = "1==1";
//
//        String sql1 = String.format("SELECT %s FROM %s where %s",selectClause1,tableName1,whereClause);
//        String sql2 = String.format("SELECT %s FROM %s where %s",selectClause2,tableName2,whereClause);


        return ConsistencyJoblist;
    }
}
