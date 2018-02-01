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
        List<List<String>> entities = NameRecognition.getNameFromDir(files);
        for(List<String> list : entities){
            for(String str : list){
                System.out.print(str + " " );
            }
            System.out.println("");
        }
        System.out.println("NameRecognition path : " + job.getPath());


        // todo: 2. 根据每个文件的人名实体建立数据库表，并加入数据，每个文件的路径作为对应的关键词
        try{
            createEntitiesTable("primary", job.getTableName());
            insertEntitiesToTable("primary" , job.getTableName() , files , entities);
            job.setResult(new Result(0, "good", "success"));
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            job.setResult(new Result(0, "bad", "fail"));
        }


    }

    // create table in the database of dataSourceKey
    public void createEntitiesTable(String dataSourceKey , String tableName){
        // todo
        DataSourceRouterManager.setCurrentDataSourceKey(dataSourceKey);
        StringBuilder sql = new StringBuilder("create table if not exists `" + tableName +
        "` (`filepath` varchar(1000) CHARACTER SET utf8 NOT NULL, `entities` varchar(5000) " +
        "CHARACTER SET utf8, primary key (`filepath`))");
        jdbcTemplate.execute(sql.toString());
    }

    // insert keywords data to table in the database of dataSourceKey
    public void insertEntitiesToTable(String dataSourceKey , String tableName
            , List<File> files , List<List<String>> entities){
       // todo
        DataSourceRouterManager.setCurrentDataSourceKey(dataSourceKey);
        try{
            StringBuilder sql = new StringBuilder("insert into " + tableName + " (`filepath`,`entities`)"
            + " values");
            for(int i=0 ; i<files.size() ; i++){
                if(i>0) sql.append(',');
                String data = "";
                for(String str : entities.get(i)){
                    data += ";" + str;
                }
                if(data.length() > 0) sql.append("('" + files.get(i).getPath() + "', '" + data.substring(1) + "')");
                else sql.append("('" + files.get(i).getPath() + "', '')"); // 不存在命名实体
            }
            System.out.println("insert here: " + sql.toString());
            int changes = jdbcTemplate.update(sql.toString());
            if(changes == 0){
                System.out.println(sql.toString() + " sql add no data");
            }
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}
