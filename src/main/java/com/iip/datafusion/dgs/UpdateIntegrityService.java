package com.iip.datafusion.dgs;

import com.iip.datafusion.backend.JobRegistry;
import com.iip.datafusion.backend.UpdateIntegrityManager;
import com.iip.datafusion.backend.job.JobType;
import com.iip.datafusion.dgs.model.UpdateIntegrityConfiguration;
import com.iip.datafusion.dgs.model.UpdateIntegrityJob;
import com.iip.datafusion.dgs.model.UpdateIntegrityParser;
import org.springframework.stereotype.Service;

/**
 * @author zengc
 * @date 2018/6/26 16:07
 */
@Service
public class UpdateIntegrityService {

    public UpdateIntegrityJob commitJob(UpdateIntegrityConfiguration updateIntegrityConfiguration)throws Exception{
        UpdateIntegrityJob updateIntegrityJob = UpdateIntegrityParser.parse(updateIntegrityConfiguration);
        updateIntegrityJob.setJobType(JobType.INTEGRITY);
        updateIntegrityJob.setUserID(updateIntegrityConfiguration.getUserId());
        JobRegistry.getInstance().regist(updateIntegrityJob);
        UpdateIntegrityManager.getInstance().commitJob(updateIntegrityJob);

        return updateIntegrityJob;
    }


}
