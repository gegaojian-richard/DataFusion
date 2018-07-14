package com.iip.datafusion.backend.executor;

import com.iip.datafusion.backend.JobRegistry;
import com.iip.datafusion.backend.channel.ChannelManager;
import com.iip.datafusion.backend.common.AbstractTerminatableThread;
import com.iip.datafusion.backend.common.TerminationToken;
import com.iip.datafusion.backend.jdbchelper.JDBCHelper;
import com.iip.datafusion.backend.job.JobStatusType;
import com.iip.datafusion.backend.job.consistency.ConsistencyJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import com.iip.datafusion.util.dbutil.DataSourceRouterManager;
import com.iip.datafusion.util.jsonutil.Result;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.data.redis.core.RedisTemplate;
import com.iip.datafusion.redis.model.RedisTransform;
import com.iip.datafusion.redis.model.RedisHelper;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

public class ConsistencyJobExecutor extends AbstractTerminatableThread implements JobExecutor<ConsistencyJob> {
    @Autowired
    private final JdbcTemplate jdbcTemplate = JDBCHelper.getJdbcTemplate();
    private final BlockingQueue<ConsistencyJob> workQueue;
    private static Logger logger = LoggerFactory.getLogger(ConsistencyJobExecutor.class);

    private RedisTemplate<String, String> redisTemplate = RedisHelper.getRedisTemplate();


    public ConsistencyJobExecutor(TerminationToken token, BlockingQueue<ConsistencyJob> workQueue){
        super(token);
        this.workQueue = workQueue;
    }

    @Override
    protected void doRun() throws Exception {
        ConsistencyJob consistencyJob = ChannelManager.getInstance().getConsistencyChannel().take(workQueue);
        JobRegistry.getInstance().update(consistencyJob, JobStatusType.EXECUTING);
        try{
            doJob(consistencyJob);
            JobRegistry.getInstance().update(consistencyJob, JobStatusType.SUCCESS);
        } catch (Exception e){
            e.printStackTrace();
            JobRegistry.getInstance().update(consistencyJob, JobStatusType.ERROR);
        } finally {
            terminationToken.reservations.decrementAndGet();
        }
    }

    @Override
    public void doJob(ConsistencyJob job) throws Exception {
        DataSourceRouterManager.setCurrentDataSourceKey(job.getmainDataSourceID());
        logger.info(String.valueOf(job.getSqlList().size()));
        try{
            if(job.getSqlList().size()>0) {
                if(job.getInnerJobType().equals("query")) {
                    SqlRowSet sqlRowSet1 = jdbcTemplate.queryForRowSet(job.getSqlList().get(0));
                    DataSourceRouterManager.setCurrentDataSourceKey(job.getfollowDataSourceID());
                    SqlRowSet sqlRowSet2 = jdbcTemplate.queryForRowSet(job.getSqlList().get(1));
                    logger.info(job.getSqlList().get(0));
                    logger.info(job.getSqlList().get(1));
                    //JSONArray json = new JSONArray();
                    ArrayList<String> lists = new ArrayList<>();
                    String key = job.getUserID() + "-" + job.getJobID();
                    logger.info(key);

                    while (sqlRowSet1.next()) {
                        int count=0;
                        String key1=sqlRowSet1.getString(1);
                        String value1=sqlRowSet1.getString(2);
                        logger.info("key1："+key1);
                        logger.info("value1："+value1);
                        while (sqlRowSet2.next()) {
                            String key2=sqlRowSet2.getString(1);
                            logger.info("key2："+key2);
                            String value2=sqlRowSet2.getString(2);
                            logger.info("value2："+value2);
                            if(value1==null||value2==null) break;
                            if(key1.compareTo(key2)!=0) ;//相等返回0，小于返回-1，大于返回1
                            if(key1.compareTo(key2)==0){
                                if(value1.compareTo(value2)!=0){
                                    JSONObject jo = new JSONObject();
//                                    jo.put("mainDataSourceId",job.getmainDataSourceID());
//                                    jo.put("mainTableName",job.getmainTableName());
//                                    jo.put("mainPrimary_key",job.getmainPrimary_key());
//                                    jo.put("key1",key1);
//                                    jo.put("mainColumnName",job.getmainColumnName());
//                                    jo.put("value1",value1);
//                                    jo.put("followDatasourceID",job.getfollowDataSourceID());
//                                    jo.put("followTableName",job.getfollowTableName());
//                                    jo.put("followPrimary_key",job.getfollowPrimary_key());
//                                    jo.put("key2",key2);
//                                    jo.put("followColumnName",job.getfollowColumnName());
//                                    jo.put("value2",value2);
                                    jo.put(job.getmainPrimary_key()+" from "+job.getmainTableName()+" from "+job.getmainDataSourceID(),key1);
                                    jo.put(job.getmainColumnName()+" from "+job.getmainTableName()+" from "+job.getmainDataSourceID(),value1);
                                    jo.put(job.getfollowColumnName()+" from "+job.getfollowTableName()+" from "+job.getfollowDataSourceID(),value2);
                                    lists.add(jo.toString());
                                }
                            }
                            if (sqlRowSet2.isLast()) {
                                sqlRowSet2.beforeFirst();
                                break;}
                        }
                    }
                    redisTemplate.opsForList().rightPushAll(key, lists);
                    JSONObject jo2 = new JSONObject();
                    jo2.put("userid-jobid",key);
                    jo2.put("mainDataSourceId",job.getmainDataSourceID());
                    jo2.put("mainTableName",job.getmainTableName());
                    jo2.put("mainPrimary_key",job.getmainPrimary_key());
                    jo2.put("mainColumnName",job.getmainColumnName());
                    jo2.put("followDatasourceID",job.getfollowDataSourceID());
                    jo2.put("followTableName",job.getfollowTableName());
                    jo2.put("followPrimary_key",job.getfollowPrimary_key());
                    jo2.put("followColumnName",job.getfollowColumnName());
                    lists.add(jo2.toString());
                    Result result = new Result(0,"数据不一致",lists.toString());
                    logger.info(result.toString());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
//            return new Result(0,"出现内部错误",null);
        }
//        return new Result();
    }
}
