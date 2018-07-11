package com.iip.datafusion.backend.parser;

import com.iip.datafusion.backend.job.JobType;
import com.iip.datafusion.backend.job.consistency.UpdateConsistencyJob;
import com.iip.datafusion.dgs.model.consistency.UpdateConsistencyConfiguration;
import com.iip.datafusion.dgs.model.consistency.MapEntries2;

import java.util.ArrayList;

public class UpdateConsistencyParser {
    public static UpdateConsistencyJob parse(UpdateConsistencyConfiguration UpdateConsistencyConfiguration){

        UpdateConsistencyJob  UpdateConsistencyJob=new UpdateConsistencyJob();
        String mainDataSourceId = UpdateConsistencyConfiguration.getmainDataSourceId();
        String mainTableName = UpdateConsistencyConfiguration.getmainTableName();
        String mainColumnName = UpdateConsistencyConfiguration.getmainColumnName();
        String mainPrimary_key = UpdateConsistencyConfiguration.getmainPrimary_key();
        String followDataSourceId = UpdateConsistencyConfiguration.getfollowDataSourceId();
        String followTableName = UpdateConsistencyConfiguration.getfollowTableName();
        String followColumnName = UpdateConsistencyConfiguration.getfollowColumnName();
        String followPrimary_key = UpdateConsistencyConfiguration.getfollowPrimary_key();
        ArrayList<String> sqlList = new ArrayList<>();
        String updateDataSourceId=" ";
        String updateTableName=" ";
        String updateColumnName=" ";
        String updatePrimary_key=" ";
        for (MapEntries2 m : UpdateConsistencyConfiguration.getMapEntries()) {
            String[] temp = m.getKey().split(",");
            String updatetype = temp[0];
            String[] temp2 = m.getValue().split(",");
            String updateP_key = temp2[0];
            String referValue = temp2[1];

            if(updatetype.equals("left")){
                System.out.println("111");
                updateDataSourceId=mainDataSourceId;
                updateTableName=mainTableName;
                updateColumnName=mainColumnName;
                updatePrimary_key=mainPrimary_key;
            }
            if(updatetype.equals("right")){
                updateDataSourceId=followDataSourceId;
                updateTableName=followTableName;
                updateColumnName=followColumnName;
                updatePrimary_key=followPrimary_key;
            }
            String updateClause =  updateColumnName + "=" + "'" + referValue + "'";
            String whereClause = updatePrimary_key + "=" + "'" + updateP_key + "'";
            String sql = String.format("UPDATE %s SET %s WHERE %s",updateTableName,updateClause,whereClause);
            sql = updatetype + ","+ sql;
            System.out.println(sql);
            sqlList.add(sql);


        }
        UpdateConsistencyJob.setmainDatasourceID(mainDataSourceId);
        UpdateConsistencyJob.setfollowDatasourceID(followDataSourceId);
        UpdateConsistencyJob.setSqlList(sqlList);
        UpdateConsistencyJob.setJobType(JobType.CONSISTENCY);
        UpdateConsistencyJob.setInnerJobType("update");
        return UpdateConsistencyJob;
    }
}
