package com.iip.datafusion.dgs.integrity;

import com.iip.datafusion.backend.IntegrityManager;
import com.iip.datafusion.backend.job.integrity.IntegrityJob;

import com.iip.datafusion.backend.parser.IntegrityParser;

import org.springframework.stereotype.Service;

@Service
public class IntegrityService {



    public IntegrityJob commitJob(IntegrityConfiguration integrityConfiguration)throws Exception{
        IntegrityJob integrityJob = IntegrityParser.parse(integrityConfiguration);
        IntegrityManager.getInstance().commitJob(integrityJob);

        return integrityJob;
    }


}
