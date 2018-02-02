package com.iip.datafusion.backend.executor;

import com.iip.datafusion.backend.channel.ChannelManager;
import com.iip.datafusion.backend.common.AbstractTerminatableThread;
import com.iip.datafusion.backend.common.TerminationToken;
import com.iip.datafusion.backend.jdbchelper.JDBCHelper;
import com.iip.datafusion.backend.job.algorithm.TFIDFJob;
import com.iip.datafusion.backend.job.algorithm.TextRankJob;
import com.iip.datafusion.backend.textprocess.textrank.TextRank;
import com.iip.datafusion.backend.textprocess.textrank.Word;
import com.iip.datafusion.backend.textprocess.util.FileUtil;
import com.iip.datafusion.util.jsonutil.Result;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
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
        System.out.println("TFIDFExcutor path : " + job.getPath());
        System.out.println("TFIDFExcutor topK: " + job.getTopK());
        List<File> files = FileUtil.getAllFilePath(new ArrayList<>() , job.getPath());
        List<List<Word>> documents = new ArrayList<>();
        String data = "";
//        for(File file: files){
//            List<Word> words = TextRank.topKWordsFromFile(file.getPath(), job.getTopK(), 5, 0.85);
//            data += file.getPath() + ": \n";
//            for(Word word : words){
//                data +=  ";" + word.getWord();
//            }
//            data += "\n";
//            documents.add(words);
//        }

        // todo: 2. 根据文本关键词建立数据库表,并加入数据，每个文件对应的关键词
//        try {
//            // job.getTargetDataSourceId 数据库的id
//            createKeyWordsTable("primary" , job.getTableName());
//            insertKeyWordsToTable("primary" , job.getTableName() , files , documents);
//        }catch (Exception ex){
//            System.out.println(ex.getMessage());
//        }
        job.setResult(new Result(0, "good", data));
    }
}
