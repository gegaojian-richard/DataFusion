package com.iip.datafusion.dgs.model;

import com.iip.datafusion.backend.JobRegistry;
import com.iip.datafusion.backend.job.integrity.IntegrityJob;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zengc
 * @date 2018/1/19 15:51
 */
public class UpdateIntegrityParser {

    public static UpdateIntegrityJob parse(UpdateIntegrityConfiguration updateIntegrityConfiguration)throws Exception{
        System.out.println("here no problem");
        IntegrityJob job = (IntegrityJob) JobRegistry.getInstance().getJob(updateIntegrityConfiguration.getUserId(),updateIntegrityConfiguration.getJobId());
        String dataSourceId = job.getDataSourceId();
        String tableName = job.getTableName();
        List<Map<String,String>> valueMaps = updateIntegrityConfiguration.getMapEntries();
        System.out.println("here no problem");
        UpdateIntegrityJob updateIntegrityJob = new UpdateIntegrityJob();
        updateIntegrityJob.setDataSourceId(dataSourceId);
        updateIntegrityJob.setTableName(tableName);
        updateIntegrityJob.setMapEntries(valueMaps);
        updateIntegrityJob.setType(updateIntegrityConfiguration.getType());
        updateIntegrityJob.setUnifyMap(updateIntegrityConfiguration.getUnifyMap());
        updateIntegrityJob.setSqlList(null);
        return updateIntegrityJob;
    }
}
