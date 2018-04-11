package com.iip.datafusion.backend.executor;

import com.iip.datafusion.backend.JobRegistry;
import com.iip.datafusion.backend.channel.ChannelManager;
import com.iip.datafusion.backend.common.AbstractTerminatableThread;
import com.iip.datafusion.backend.common.TerminationToken;
import com.iip.datafusion.backend.jdbchelper.JDBCHelper;
import com.iip.datafusion.backend.job.JobStatusType;
import com.iip.datafusion.backend.job.algorithm.TopicModelJob;
import com.iip.datafusion.backend.textprocess.cheonhye.BTM;
import com.iip.datafusion.nsps.dao.MySqlDAO;
import com.iip.datafusion.util.jsonutil.JsonParse;
import com.iip.datafusion.util.jsonutil.Result;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

/**
 * @Author Junnor.G
 * @Date 2018/2/3 上午2:52
 */
public class TopicModelExecutor  extends AbstractTerminatableThread implements JobExecutor<TopicModelJob> {
    private final BlockingQueue<TopicModelJob> workQueue;


    private final JdbcTemplate jdbcTemplate = JDBCHelper.getJdbcTemplate();


    public TopicModelExecutor(TerminationToken token, BlockingQueue<TopicModelJob> workQueue) {
        super(token);
        this.workQueue = workQueue;
    }

    @Override
    protected void doRun() throws Exception {
        TopicModelJob job = ChannelManager.getInstance().getTopicModeChannel().take(workQueue);
        JobRegistry.getInstance().update(job, JobStatusType.EXECUTING);

        try {
            doJob(job);
            JobRegistry.getInstance().update(job, JobStatusType.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            JobRegistry.getInstance().update(job, JobStatusType.ERROR);
        } finally {
            terminationToken.reservations.decrementAndGet();
        }
    }

    @Override
    public void doJob(TopicModelJob job) throws Exception {
        // todo: 1. 找到文件目录路径job.path下所有文本的关键词
//        System.out.println("TFIDFExcutor path : " + job.getPath());
//        System.out.println("TFIDFExcutor topK: " + job.getTopK());
        if(job.getCorpusPath() == null || job.getTopicNum() == 0 || job.getTableName() == null || job.getDataSourceId() == null){
            job.setResult(new Result(0, "error", "some parameters doesn't exist: " +
                    "'corpusPath', 'topicNum'(>0), 'tableName', 'dataSourceId'"));
        }
        else {
            Map<String, List<String>> keyWords = BTM.btm(job.getCorpusPath() , job.getTopicNum());
            // todo: 2. 根据文本关键词建立数据库表,并加入数据，每个文件对应的关键词
            int status = MySqlDAO.createWordsTable("topics" , jdbcTemplate , job.getDataSourceId() , job.getTableName());
            if(status == -1){
                job.setResult(new Result(0, "error", "create table error"));
            }
            else{
                status = MySqlDAO.insertWordsToTable("topics" , jdbcTemplate , job.getDataSourceId() , job.getTableName() , keyWords);
                if(status == -1) job.setResult(new Result(0, "error", "create table error"));
                else job.setResult(new Result(1, "right", JsonParse.getMapper().writeValueAsString(keyWords)));
            }
        }
    }
}
