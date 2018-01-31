package com.iip.datafusion.backend.executor;

import com.iip.datafusion.backend.channel.ChannelManager;
import com.iip.datafusion.backend.common.AbstractTerminatableThread;
import com.iip.datafusion.backend.common.TerminationToken;
import com.iip.datafusion.backend.jdbchelper.JDBCHelper;
import com.iip.datafusion.backend.job.algorithm.TextRankJob;
import com.iip.datafusion.backend.textprocess.textrank.TextRank;
import com.iip.datafusion.backend.textprocess.textrank.Word;
import com.iip.datafusion.backend.textprocess.util.FileUtil;
import com.iip.datafusion.util.dbutil.DataSourceRouterManager;
import com.iip.datafusion.util.jsonutil.Result;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
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
        System.out.println("TextRankExcutor path : " + job.getPath());
        System.out.println("TextRankExcutor topK: " + job.getTopK());
        List<File> files = FileUtil.getAllFilePath(new ArrayList<>() , job.getPath());
        List<List<Word>> documents = new ArrayList<>();
        String data = "";
        for(File file: files){
            List<Word> words = TextRank.topKWordsFromFile(file.getPath(), job.getTopK(), 5, 0.85);
            data += file.getPath() + ": \n";
            for(Word word : words){
                data +=  ";" + word.getWord();
            }
            data += "\n";
            documents.add(words);
        }

        // todo: 2. 根据文本关键词建立数据库表,并加入数据，每个文件对应的关键词
        try {
            // job.getTargetDataSourceId 数据库的id
            createKeyWordsTable("primary" , "keywords");
            insertKeyWordsToTable("primary" , "keywords" , files , documents);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        job.setResult(new Result(0, "good", data));
    }

    // create table in the database of dataSourceKey
    public void createKeyWordsTable(String dataSourceKey , String tableName){
        DataSourceRouterManager.setCurrentDataSourceKey(dataSourceKey);
        StringBuilder sql = new StringBuilder("create table if not exists `" + tableName +"` (" +
                "`filepath` varchar(1000) CHARACTER SET utf8 NOT NULL, `keywords` varchar(5000) CHARACTER SET utf8, primary key (`filepath`))");
        jdbcTemplate.execute(sql.toString());
    }

    // insert keywords data to table in the database of dataSourceKey
    public void insertKeyWordsToTable(String dataSourceKey , String tableName
            , List<File> files , List<List<Word>> documents){
        DataSourceRouterManager.setCurrentDataSourceKey(dataSourceKey);
        try {
            for (int i = 0; i < files.size(); i++) {
                String data = "";
                for (Word word : documents.get(i)) {
                    data += ";" + word.getWord();
                }
                StringBuilder sql = new StringBuilder("insert into " + tableName + " (`filepath`,`keywords`)" +
                        " values('" + files.get(i).getPath() + "', '" + data.substring(1) + "')");
                int changes = jdbcTemplate.update(sql.toString());
                if(changes == 0){
                    System.out.println("insert into " + tableName + " (`filepath`,`keywords`)" +
                            " values('" + files.get(i).getPath() + "', '" + data.substring(1) + "')" + "sql add data failed");
                }
            }
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }

    }
}
