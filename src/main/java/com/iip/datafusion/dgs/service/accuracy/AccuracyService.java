package com.iip.datafusion.dgs.service.accuracy;

import com.iip.datafusion.backend.AccuracyManager;
import com.iip.datafusion.backend.job.accuracy.AccuracyJob;
import com.iip.datafusion.backend.parser.AccuracyParser;
import com.iip.datafusion.dgs.model.accuracy.AccuracyConfiguration;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AccuracyService {

    public void commitJob(AccuracyConfiguration accuracyConfiguration){
        AccuracyJob accuracyJob = AccuracyParser.parser(accuracyConfiguration);
        AccuracyManager.getInstance().commitJob(accuracyJob);
    }
}
