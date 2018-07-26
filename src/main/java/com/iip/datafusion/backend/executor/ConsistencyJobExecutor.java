package com.iip.datafusion.backend.executor;

import com.iip.datafusion.backend.JobRegistry;
import com.iip.datafusion.backend.channel.ChannelManager;
import com.iip.datafusion.backend.common.AbstractTerminatableThread;
import com.iip.datafusion.backend.common.TerminationToken;
import com.iip.datafusion.backend.jdbchelper.JDBCHelper;
import com.iip.datafusion.backend.job.JobStatusType;
import com.iip.datafusion.backend.job.consistency.ConsistencyJob;
import com.iip.datafusion.dgs.model.consistency.MapEntries;
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
import java.util.List;
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
        String mainDisplayName=job.getmainDataSourceID();
//        mainDisplayName = DataSourceRouterManager.getDataSourceDisplayName(job.getmainDataSourceID());
        List<MapEntries> MapEntries=job.getMapEntries();
        String mainTableName=job.getmainTableName();
        try{
            if(job.getInnerJobType().equals("query")) {
                ArrayList<String> lists1 = new ArrayList<>();
                ArrayList<String> lists2 = new ArrayList<>();
                String key = job.getUserID() + "-" + job.getJobID();
                logger.info(key);
                String key_description = job.getUserID() + "-" + job.getJobID() + "-" + "description";
                logger.info(key_description);
                int countALL=0;
                for (MapEntries m : MapEntries) {
                    String[] temp = m.getKey().split(",");
                    String mainColumnName = temp[0];
                    String mainPrimary_key = temp[1];
                    String[] temp2 = m.getValue().split(",");
                    String followDataSourceID = temp2[0];
                    String followTableName = temp2[1];
                    String followColumnName = temp2[2];
                    String followPrimary_key = temp2[3];
                    String selectClause1 = mainPrimary_key + "," + mainColumnName;
                    String selectClause2 = followPrimary_key + "," + followColumnName;
                    String whereClause = "1=1";
                    String sql1 = String.format("SELECT %s FROM %s where %s", selectClause1, mainTableName, whereClause);
                    String sql2 = String.format("SELECT %s FROM %s where %s", selectClause2, followTableName, whereClause);
                    DataSourceRouterManager.setCurrentDataSourceKey(job.getmainDataSourceID());
                    SqlRowSet sqlRowSet1 = jdbcTemplate.queryForRowSet(sql1);
                    DataSourceRouterManager.setCurrentDataSourceKey(followDataSourceID);
                    String followDisplayName = followDataSourceID;
//                    followDisplayName = DataSourceRouterManager.getDataSourceDisplayName(followDataSourceID);
                    SqlRowSet sqlRowSet2 = jdbcTemplate.queryForRowSet(sql2);
                    logger.info(sql1);
                    logger.info(sql2);
                    //JSONArray json = new JSONArray();
                    int count=0;
                    while (sqlRowSet1.next()) {

                        String key1 = sqlRowSet1.getString(1);
                        String value1 = sqlRowSet1.getString(2);
                        logger.info("key1：" + key1);
                        logger.info("value1：" + value1);
                        while (sqlRowSet2.next()) {
                            String key2 = sqlRowSet2.getString(1);
                            logger.info("key2：" + key2);
                            String value2 = sqlRowSet2.getString(2);
                            logger.info("value2：" + value2);
//                            if (value1 == null || value2 == null) break;
                            if (key1.compareTo(key2) != 0) ;//相等返回0，小于返回-1，大于返回1
                            if (key1.compareTo(key2) == 0) {
                                if(value1 == null&&value2 != null){
                                    count=count+1;
                                    JSONObject jo = new JSONObject();
                                    jo.put(mainPrimary_key + " from " + mainTableName + " from " + mainDisplayName, key1);
                                    jo.put(mainColumnName + " from " + mainTableName + " from " + mainDisplayName, "NULL");
                                    jo.put(followColumnName + " from " + followTableName + " from " + followDisplayName, value2);
                                    lists1.add(jo.toString());
                                }
                                if(value1 != null&&value2 == null){
                                    count=count+1;
                                    JSONObject jo = new JSONObject();
                                    jo.put(mainPrimary_key + " from " + mainTableName + " from " + mainDisplayName, key1);
                                    jo.put(mainColumnName + " from " + mainTableName + " from " + mainDisplayName, value1);
                                    jo.put(followColumnName + " from " + followTableName + " from " + followDisplayName, "NULL");
                                    lists1.add(jo.toString());
                                }
                                if (value1 != null&&value2 != null&&value1.compareTo(value2) != 0) {
                                    count=count+1;
                                    JSONObject jo = new JSONObject();
                                    jo.put(mainPrimary_key + " from " + mainTableName + " from " + mainDisplayName, key1);
                                    jo.put(mainColumnName + " from " + mainTableName + " from " + mainDisplayName, value1);
                                    jo.put(followColumnName + " from " + followTableName + " from " + followDisplayName, value2);
                                    lists1.add(jo.toString());
                                }
                            }
                            if (sqlRowSet2.isLast()) {
                                sqlRowSet2.beforeFirst();
                                break;
                            }
                        }
                    }
                    countALL=countALL+count;
                    JSONObject jo2 = new JSONObject();
                    jo2.put("mainDataSourceId", job.getmainDataSourceID());
                    jo2.put("mainDisplayName", mainDisplayName);
                    jo2.put("mainTableName", job.getmainTableName());
                    jo2.put("mainColumnName", mainColumnName);
                    jo2.put("mainPrimary_key", mainPrimary_key);
                    jo2.put("followDatasourceID", followDataSourceID);
                    jo2.put("followDisplayName", followDisplayName);
                    jo2.put("followTableName", followTableName);
                    jo2.put("followColumnName", followColumnName);
                    jo2.put("followPrimary_key", followPrimary_key);
                    jo2.put("redisStart", countALL-count);
                    jo2.put("redisEnd", countALL-1);
                    lists2.add(jo2.toString());

                }
                Result result = new Result(0, "数据不一致", lists1.toString());
                logger.info(result.toString());
                redisTemplate.opsForList().rightPushAll(key, lists1);
                redisTemplate.opsForList().rightPushAll(key_description, lists2);
            }
        }catch (Exception e){
            e.printStackTrace();
//            return new Result(0,"出现内部错误",null);
        }
//        return new Result();
    }
}
