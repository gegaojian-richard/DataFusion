package com.iip.datafusion.dgs.model;

import com.iip.datafusion.backend.job.JobType;
import com.iip.datafusion.backend.job.integrity.IntegrityJob;
import com.iip.datafusion.dgs.model.UpdateConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zengc
 * @date 2018/1/19 15:51
 */
public class UpdateParser {

    public static UpdateJob parse(UpdateConfiguration updateConfiguration)throws Exception{

        String dataSourceId = updateConfiguration.getDataSourceId();
        String tableName = updateConfiguration.getTableName();
        List<Map<String,String>> valueMaps = updateConfiguration.getIntanceValues();
        if (valueMaps == null || valueMaps.size() <= 0) {
            throw new Exception("传入记录不能为空");
            //return null;
        }

        ArrayList<String> columnList = new ArrayList<>();
        String columnClause = "(";
        String totalValueClause = "";
        ArrayList<String> sqlList = new ArrayList<>();
        for(int i = 0;i<valueMaps.size();i++) {
            Map<String,String> valueMap = valueMaps.get(i);

            String valueClause = "(";
            int index = -1;
            for (String key : valueMap.keySet()) {
                index ++;
                if (key != null && key.trim() !="") {
                    if(i == 0 ) {
                        columnList.add(key);
                        columnClause += key + ",";
                    }
                    else if(columnList.size()<=index || !columnList.get(index).equals(key)){
                        throw new Exception("传入记录属性顺序不一致");
                    }

                    valueClause += valueMap.get(key) + ",";
                } else{
                    throw new Exception("属性不能为空");
                }
            }
            if(i == 0 ) {
                columnClause = columnClause.substring(0, columnClause.length() - 1);
                columnClause += ") ";
            }
            valueClause = valueClause.substring(0, valueClause.length() - 1);
            valueClause += ")";

            totalValueClause += valueClause+",";
        }
        totalValueClause = totalValueClause.substring(0,totalValueClause.length()-1)+";";
        String total = String.format("REPLACE INTO %s  %s VALUES %s",tableName,columnClause,totalValueClause);
        sqlList.add(total);
        UpdateJob updateJob = new UpdateJob();
        updateJob.setDataSourceId(dataSourceId);
        updateJob.setTableName(tableName);
        updateJob.setSqlList(sqlList);
        return updateJob;
    }
}
