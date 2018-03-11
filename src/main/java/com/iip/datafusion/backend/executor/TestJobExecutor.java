package com.iip.datafusion.backend.executor;

import com.iip.datafusion.backend.JobRegistry;
import com.iip.datafusion.backend.channel.ChannelManager;
import com.iip.datafusion.backend.common.AbstractTerminatableThread;
import com.iip.datafusion.backend.common.TerminationToken;
import com.iip.datafusion.backend.jdbchelper.JDBCHelper;
import com.iip.datafusion.backend.job.JobStatusType;
import com.iip.datafusion.backend.job.integrity.IntegrityJob;
import com.iip.datafusion.backend.job.test.TestJob;
import com.iip.datafusion.util.dbutil.DataSourceRouterManager;
import com.iip.datafusion.util.jsonutil.Result;
import net.sf.json.JSONObject;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

/**
 * @Author Junnor.G
 * @Date 2018/1/31 下午2:26
 */
public class TestJobExecutor extends AbstractTerminatableThread implements JobExecutor<TestJob> {
        private final BlockingQueue<TestJob> workQueue;


        private final JdbcTemplate jdbcTemplate = JDBCHelper.getJdbcTemplate();




    public TestJobExecutor(TerminationToken token, BlockingQueue<TestJob> workQueue){
            super(token);
            this.workQueue = workQueue;
        }

        @Override
        protected void doRun() throws Exception {
            TestJob testJob = ChannelManager.getInstance().getTestChannel().take(workQueue);
            JobRegistry.getInstance().update(testJob, JobStatusType.EXECUTING);

            try{
                doJob(testJob);
                JobRegistry.getInstance().update(testJob, JobStatusType.SUCCESS);
            } catch (Exception e){
                e.printStackTrace();
                JobRegistry.getInstance().update(testJob, JobStatusType.ERROR);
            } finally {
                terminationToken.reservations.decrementAndGet();
            }
        }

        @Override
        public void doJob(TestJob job) throws Exception {
            // todo: 实现完整性检查工作

            System.out.println("testjob is running...");
            this.sleep(50000);
//            job.setResult(new Result(0,"wawawawawa",null));

            System.out.println("here is : " + job.getPath());

        }

 }
