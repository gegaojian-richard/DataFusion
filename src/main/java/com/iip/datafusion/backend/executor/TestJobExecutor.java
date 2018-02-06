package com.iip.datafusion.backend.executor;

import com.iip.datafusion.backend.channel.ChannelManager;
import com.iip.datafusion.backend.common.AbstractTerminatableThread;
import com.iip.datafusion.backend.common.TerminationToken;
import com.iip.datafusion.backend.jdbchelper.JDBCHelper;
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

            try{
                doJob(testJob);
            } catch (Exception e){
                e.printStackTrace();
            } finally {
                terminationToken.reservations.decrementAndGet();
            }
        }

        @Override
        public void doJob(TestJob job) throws Exception {
            // todo: 实现完整性检查工作

            //System.out.println("finished hahah");
            //job.setResult(new Result(0,"wawawawawa",null));

            System.out.println("here is : " + job.getPath());

        }

 }
