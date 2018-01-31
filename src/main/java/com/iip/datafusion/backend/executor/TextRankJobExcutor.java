package com.iip.datafusion.backend.executor;

import com.iip.datafusion.backend.channel.ChannelManager;
import com.iip.datafusion.backend.channel.WorkStealingEnabledChannel;
import com.iip.datafusion.backend.common.AbstractTerminatableThread;
import com.iip.datafusion.backend.common.TerminationToken;
import com.iip.datafusion.backend.jdbchelper.JDBCHelper;
import com.iip.datafusion.backend.job.algorithm.TextRankJob;
import com.iip.datafusion.nsps.process.textrank.TextRank;
import com.iip.datafusion.nsps.process.textrank.Word;
import com.iip.datafusion.util.jsonutil.Result;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.concurrent.BlockingQueue;
import java.util.List;
import java.util.ArrayList;

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
        // todo: 实现完整性检查工作

        //System.out.println("finished hahah");
        //job.setResult(new Result(0,"wawawawawa",null));

        System.out.println("TextRankExcutor path : " + job.getPath());
        System.out.println("TextRankExcutor topK: " + job.getTopK());
        List<Word> words = TextRank.topKWordsFromFile(job.getPath(), job.getTopK(), 5, 0.85);
        String data = "";
        for(Word word : words){
            data +=  ";" + word.getWord();
        }
        job.setResult(new Result(0, "good", data.substring(1)));
    }
}
