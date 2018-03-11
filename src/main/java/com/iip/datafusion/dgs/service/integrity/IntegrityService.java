package com.iip.datafusion.dgs.service.integrity;

import com.iip.datafusion.backend.IntegrityManager;
import com.iip.datafusion.backend.JobRegistry;
import com.iip.datafusion.backend.job.JobStatusType;
import com.iip.datafusion.backend.job.JobType;
import com.iip.datafusion.backend.job.integrity.IntegrityJob;

import com.iip.datafusion.backend.parser.IntegrityParser;

import com.iip.datafusion.dgs.model.integrity.IntegrityConfiguration;
import org.springframework.stereotype.Service;

import javax.swing.*;

@Service
public class IntegrityService {



    public IntegrityJob commitJob(IntegrityConfiguration integrityConfiguration,int UserID)throws Exception{
        IntegrityJob integrityJob = IntegrityParser.parse(integrityConfiguration);
        integrityJob.setJobType(JobType.INTEGRITY);
        integrityJob.setUserID(UserID);
        JobRegistry.getInstance().regist(integrityJob);
        IntegrityManager.getInstance().commitJob(integrityJob);

        return integrityJob;
    }


}
