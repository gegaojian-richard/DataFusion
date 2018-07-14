package com.iip.datafusion.dgs.model;

import com.iip.datafusion.backend.JobRegistry;
import com.iip.datafusion.backend.job.integrity.IntegrityJob;
import com.iip.datafusion.dgs.controller.UpdateIntegrityController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zengc
 * @date 2018/1/19 15:51
 */
public class UpdateIntegrityParser {
    protected static Logger logger= LoggerFactory.getLogger(UpdateIntegrityParser.class);

    public static UpdateIntegrityJob parse(UpdateIntegrityConfiguration updateIntegrityConfiguration)throws Exception{
        logger.info("enter parse updateIntegrityJob");
        IntegrityJob job = (IntegrityJob) JobRegistry.getInstance().getJob(updateIntegrityConfiguration.getUserId(),updateIntegrityConfiguration.getJobId());
        String dataSourceId = job.getDataSourceId();
        String tableName = job.getTableName();
        List<Map<String,String>> valueMaps = updateIntegrityConfiguration.getMapEntries();

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
