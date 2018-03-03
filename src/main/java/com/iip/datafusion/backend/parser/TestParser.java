package com.iip.datafusion.backend.parser;

import com.iip.datafusion.backend.job.integrity.IntegrityJob;
import com.iip.datafusion.backend.job.test.TestJob;
import com.iip.datafusion.dgs.model.integrity.IntegrityConfiguration;
import com.iip.datafusion.nsps.model.TestConfiguration;

import java.util.ArrayList;

/**
 * @Author Junnor.G
 * @Date 2018/1/31 下午2:33
 */
public class TestParser implements Parser{
    public static TestJob parse(TestConfiguration testConfiguration)throws Exception{

        String path = testConfiguration.getPath();
        TestJob testJob = new TestJob();
        testJob.setPath(path);

        return testJob;

    }
}
