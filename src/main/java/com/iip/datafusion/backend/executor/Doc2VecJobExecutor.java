package com.iip.datafusion.backend.executor;

import com.iip.datafusion.backend.channel.ChannelManager;
import com.iip.datafusion.backend.common.AbstractTerminatableThread;
import com.iip.datafusion.backend.common.TerminationToken;
import com.iip.datafusion.backend.jdbchelper.JDBCHelper;
import com.iip.datafusion.backend.job.algorithm.Doc2VecJob;
import com.iip.datafusion.nsps.process.doc2vec.jython;
import com.iip.datafusion.util.dbutil.DataSourceRouterManager;
import com.iip.datafusion.util.jsonutil.Result;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Map;
import java.util.concurrent.BlockingQueue;


/**
 * @Author YLX
 * @Date 2018/2/1
 */
public class Doc2VecJobExecutor extends AbstractTerminatableThread implements JobExecutor<Doc2VecJob> {
    private final BlockingQueue<Doc2VecJob> workQueue;

    private final JdbcTemplate jdbcTemplate = JDBCHelper.getJdbcTemplate();

    public Doc2VecJobExecutor(TerminationToken token, BlockingQueue<Doc2VecJob> workQueue){
        super(token);
        this.workQueue = workQueue;
    }

    @Override
    protected void doRun() throws Exception {
        Doc2VecJob doc2vecJob = ChannelManager.getInstance().getDoc2VecChannel().take(workQueue);
        try{
            doJob(doc2vecJob);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            terminationToken.reservations.decrementAndGet();
        }
    }

    @Override
    public void doJob(Doc2VecJob job) throws Exception {
        try{
            String tableName = "d2v";
            System.out.println("executor: " + job.getDataSourceId());
            System.out.println("executor: " + job.getPath());
            Map<String, String> r = jython.getVec(job.getPath());
            DataSourceRouterManager.setCurrentDataSourceKey(job.getDataSourceId());
            StringBuilder sql1 = new StringBuilder("create table if not exists " + tableName + " (" +
                    "filename varchar(100), vector varchar(2000))");
            jdbcTemplate.execute(sql1.toString());
            for (Map.Entry<String, String> entry : r.entrySet()) {
                StringBuilder sql2 = new StringBuilder("insert into " + tableName + " values ('" + entry.getKey() + "', '" + entry.getValue() + "')");
                jdbcTemplate.execute(sql2.toString());
                System.out.println("key: " + entry.getKey() + ", value: " + entry.getValue());
            }
        }catch (Exception e){
            e.printStackTrace();
            job.setResult(new Result(0,"出现内部错误",null));
        }
    }
}
