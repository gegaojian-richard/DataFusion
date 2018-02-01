package com.iip.datafusion.backend.executor;

import com.iip.datafusion.backend.channel.ChannelManager;
import com.iip.datafusion.backend.common.AbstractTerminatableThread;
import com.iip.datafusion.backend.common.TerminationToken;
import com.iip.datafusion.backend.jdbchelper.JDBCHelper;
import com.iip.datafusion.backend.job.algorithm.NameRecognitionJob;
import com.iip.datafusion.backend.job.algorithm.TextRankJob;
import com.iip.datafusion.backend.textprocess.textrank.TextRank;
import com.iip.datafusion.backend.textprocess.textrank.Word;
import com.iip.datafusion.backend.textprocess.util.FileUtil;
import com.iip.datafusion.util.dbutil.DataSourceRouterManager;
import com.iip.datafusion.util.jsonutil.Result;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * @Author Junnor.G
 * @Date 2018/2/1 下午4:22
 */
public class NameRecognitionJobExcutor  extends AbstractTerminatableThread implements JobExecutor<NameRecognitionJob> {
    private final BlockingQueue<NameRecognitionJob> workQueue;


    private final JdbcTemplate jdbcTemplate = JDBCHelper.getJdbcTemplate();


    public NameRecognitionJobExcutor(TerminationToken token, BlockingQueue<NameRecognitionJob> workQueue) {
        super(token);
        this.workQueue = workQueue;
    }

    @Override
    protected void doRun() throws Exception {
        NameRecognitionJob job = ChannelManager.getInstance().getNameRecognitionChannel().take(workQueue);

        try {
            doJob(job);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            terminationToken.reservations.decrementAndGet();
        }
    }

    @Override
    public void doJob(NameRecognitionJob job) throws Exception {
        // todo: 1. 找到文件目录路径job.path下所有文本的实体
        List<File> files = FileUtil.getAllFilePath(new ArrayList<>() , job.getPath());
        System.out.println("NameRecognition path : " + job.getPath());

        job.setResult(new Result(0, "good", "success"));
    }

    // create table in the database of dataSourceKey
    public void createKeyWordsTable(String dataSourceKey , String tableName){
        // todo
    }

    // insert keywords data to table in the database of dataSourceKey
    public void insertKeyWordsToTable(String dataSourceKey , String tableName
            , List<File> files , List<List<Word>> documents){
       // todo

    }
}
