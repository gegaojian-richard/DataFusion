package com.iip.datafusion.backend.executor;

import com.iip.datafusion.backend.channel.ChannelManager;
import com.iip.datafusion.backend.common.AbstractTerminatableThread;
import com.iip.datafusion.backend.common.TerminationToken;
import com.iip.datafusion.backend.jdbchelper.JDBCHelper;
import com.iip.datafusion.backend.job.algorithm.TextRankJob;
import com.iip.datafusion.backend.textprocess.cheonhye.TF_IDF;
import com.iip.datafusion.backend.textprocess.textrank.TextRank;
import com.iip.datafusion.backend.textprocess.textrank.Word;
import com.iip.datafusion.backend.textprocess.util.FileUtil;
import com.iip.datafusion.nsps.dao.MySqlDAO;
import com.iip.datafusion.util.dbutil.DataSourceRouterManager;
import com.iip.datafusion.util.jsonutil.JsonParse;
import com.iip.datafusion.util.jsonutil.Result;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.List;

/**
 * @Author Junnor.G
 * @Date 2018/1/31 下午3:24
 */
public class TextRankJobExcutor extends AbstractTerminatableThread implements JobExecutor<TextRankJob> {
    private final BlockingQueue<TextRankJob> workQueue;


    private final JdbcTemplate jdbcTemplate = JDBCHelper.getJdbcTemplate();


    public TextRankJobExcutor(TerminationToken token, BlockingQueue<TextRankJob> workQueue) {
        super(token);
        this.workQueue = workQueue;
    }

    @Override
    protected void doRun() throws Exception {
        TextRankJob textRankJob = ChannelManager.getInstance().getTextRankChannel().take(workQueue);

        try {
            doJob(textRankJob);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            terminationToken.reservations.decrementAndGet();
        }
    }

    @Override
    public void doJob(TextRankJob job) throws Exception {
        // todo: 1. 找到文件目录路径job.path下所有文本的关键词
//        System.out.println("TextRankExcutor path : " + job.getCorpusPath());
//        System.out.println("TextRankExcutor topK: " + job.getTopK());
        if(job.getCorpusPath() == null || job.getTopK() == 0 || job.getTableName() == null || job.getDataSourceId() == null){
            job.setResult(new Result(-1, "error", "some parameters doesn't exist: " +
                    "'corpusPath', 'topK'(>0), 'tableName', 'dataSourceId'"));
        }
        else {
            Map<String, List<String>> keyWords = TextRank.topKWordsFromFile(job.getCorpusPath() , job.getTopK() , 5 , 0.85);
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
