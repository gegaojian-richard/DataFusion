package com.iip.datafusion.backend.executor;

import com.iip.datafusion.backend.channel.ChannelManager;
import com.iip.datafusion.backend.common.AbstractTerminatableThread;
import com.iip.datafusion.backend.common.TerminationToken;
import com.iip.datafusion.backend.jdbchelper.JDBCHelper;
import com.iip.datafusion.backend.job.algorithm.NameRecognitionJob;
import com.iip.datafusion.backend.job.algorithm.TextRankJob;
import com.iip.datafusion.backend.textprocess.entity_recognition.NameRecognition;
import com.iip.datafusion.backend.textprocess.textrank.TextRank;
import com.iip.datafusion.backend.textprocess.textrank.Word;
import com.iip.datafusion.backend.textprocess.util.FileUtil;
import com.iip.datafusion.nsps.dao.MySqlDAO;
import com.iip.datafusion.util.dbutil.DataSourceRouterManager;
import com.iip.datafusion.util.jsonutil.JsonParse;
import com.iip.datafusion.util.jsonutil.Result;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        if(job.getPath() == null || job.getTableName() == null || job.getDataSourceId() == null){
            job.setResult(new Result(-1, "error", "some parameters doesn't exist: " +
                    "'path', 'tableName', 'dataSourceId'"));
        }
        else {
            // todo: 1. 找到文件目录路径job.path下所有文本的实体
            Map<String, List<String>> words = NameRecognition.getNameFromDir(job.getPath());
            // todo: 2. 根据每个文件的人名实体建立数据库表，并加入数据，每个文件的路径作为对应的关键词
            int status = MySqlDAO.createWordsTable("name_entities" , jdbcTemplate , job.getDataSourceId() , job.getTableName());
            if(status == -1){
                job.setResult(new Result(-1, "error", "create table error"));
            }
            else{
                status = MySqlDAO.insertWordsToTable("name_entities" , jdbcTemplate , job.getDataSourceId() , job.getTableName() , words);
                if(status == -1) job.setResult(new Result(-1, "error", "create table error"));
                else job.setResult(new Result(0, "right", JsonParse.getMapper().writeValueAsString(words)));
            }
        }

    }

}
