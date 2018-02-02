package com.iip.datafusion.backend.executor;

import com.iip.datafusion.backend.channel.ChannelManager;
import com.iip.datafusion.backend.common.AbstractTerminatableThread;
import com.iip.datafusion.backend.common.TerminationToken;
import com.iip.datafusion.backend.jdbchelper.JDBCHelper;
import com.iip.datafusion.backend.job.algorithm.TFIDFJob;
import com.iip.datafusion.backend.textprocess.cheonhye.TF_IDF;
import com.iip.datafusion.nsps.dao.MySqlDAO;
import com.iip.datafusion.util.dbutil.DataSourceRouterManager;
import com.iip.datafusion.util.jsonutil.Result;
import org.springframework.jdbc.core.JdbcTemplate;
import com.iip.datafusion.util.jsonutil.JsonParse;

import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

/**
 * @Author Junnor.G
 * @Date 2018/2/1 下午9:42
 */
public class TFIDFJobExecutor  extends AbstractTerminatableThread implements JobExecutor<TFIDFJob> {
    private final BlockingQueue<TFIDFJob> workQueue;


    private final JdbcTemplate jdbcTemplate = JDBCHelper.getJdbcTemplate();


    public TFIDFJobExecutor(TerminationToken token, BlockingQueue<TFIDFJob> workQueue) {
        super(token);
        this.workQueue = workQueue;
    }

    @Override
    protected void doRun() throws Exception {
        TFIDFJob job = ChannelManager.getInstance().getTfidfChannel().take(workQueue);

        try {
            doJob(job);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            terminationToken.reservations.decrementAndGet();
        }
    }

    @Override
    public void doJob(TFIDFJob job) throws Exception {
        // todo: 1. 找到文件目录路径job.path下所有文本的关键词
//        System.out.println("TFIDFExcutor path : " + job.getPath());
//        System.out.println("TFIDFExcutor topK: " + job.getTopK());
        if(job.getPath() == null || job.getTopK() == 0 || job.getTableName() == null || job.getDataSourceId() == null){
            job.setResult(new Result(-1, "error", "some parameters doesn't exist: " +
                    "'path', 'topK'(>0), 'tableName', 'dataSourceId'"));
        }
        else {
            Map<String, List<String>> keyWords = TF_IDF.tfIdf(job.getPath(), job.getTopK());
            // todo: 2. 根据文本关键词建立数据库表,并加入数据，每个文件对应的关键词
            int status = MySqlDAO.createWordsTable("keywords" , jdbcTemplate , job.getDataSourceId() , job.getTableName());
            if(status == -1){
                job.setResult(new Result(-1, "error", "create table error"));
            }
            else{
                status = MySqlDAO.insertWordsToTable("keywords" , jdbcTemplate , job.getDataSourceId() , job.getTableName() , keyWords);
                if(status == -1) job.setResult(new Result(-1, "error", "create table error"));
                else job.setResult(new Result(0, "right", JsonParse.getMapper().writeValueAsString(keyWords)));
            }
        }
    }


}
