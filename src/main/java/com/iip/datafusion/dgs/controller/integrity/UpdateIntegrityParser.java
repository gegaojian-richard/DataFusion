package com.iip.datafusion.dgs.controller.integrity;

import com.iip.datafusion.backend.job.JobType;
import com.iip.datafusion.backend.job.integrity.IntegrityJob;
import com.iip.datafusion.dgs.model.integrity.UpdateIntegrityConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zengc
 * @date 2018/1/19 15:51
 */
public class UpdateIntegrityParser  {

    public static IntegrityJob parse(UpdateIntegrityConfiguration updateIntegrityConfiguration)throws Exception{

        String dataSourceId = updateIntegrityConfiguration.getDataSourceId();
        String tableName = updateIntegrityConfiguration.getTableName();
        List<Map<String,String>> valueMaps = updateIntegrityConfiguration.getIntanceValues();
        if (valueMaps == null || valueMaps.size() <= 0) {
            throw new Exception("传入记录不能为空");
            //return null;
        }

        ArrayList<String> sqlList = new ArrayList<>();
        for(int i = 0;i<valueMaps.size();i++) {
            Map<String,String> valueMap = valueMaps.get(i);
            String columnClause = "(";
            String valueClause = "(";
            for (String key : valueMap.keySet()) {
                if (key != null && key.trim() !="") {
                    columnClause += key + ",";
                    valueClause += valueMap.get(key) + ",";
                } else{
                    throw new Exception("属性不能为空");
                }
            }
            columnClause = columnClause.substring(0, columnClause.length() - 1);
            columnClause += ") ";

            valueClause = valueClause.substring(0, valueClause.length() - 1);
            valueClause += ") ";

            String sql = String.format("REPLACE INTO %s  %s VALUES %s",tableName,columnClause,valueClause);
            sqlList.add(sql);
        }
        IntegrityJob integrityJob = new IntegrityJob();
        integrityJob.setDataSourceId(dataSourceId);
        integrityJob.setTableName(tableName);
        integrityJob.setSqlList(sqlList);
        integrityJob.setJobType(JobType.INTEGRITY);

        return new IntegrityJob();
    }
}
