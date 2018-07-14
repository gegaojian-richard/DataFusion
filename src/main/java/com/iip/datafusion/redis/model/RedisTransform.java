package com.iip.datafusion.redis.model;

import net.sf.json.JSONObject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

import java.util.ArrayList;

/**
 * @author zengc
 * @date 2018/3/11 16:11
 */
public class RedisTransform {

    public static boolean rowsetToRedis(SqlRowSet sqlRowSet, String jobId,RedisTemplate redisTemplate)throws Exception{
        SqlRowSetMetaData sqlRsmd = sqlRowSet.getMetaData();
        ArrayList<String> trueColumnNames = new ArrayList<>();
        for(int i=1;i<=sqlRsmd.getColumnCount();i++){
            trueColumnNames.add(sqlRsmd.getColumnName(i));
        }

        ArrayList<String> lists = new ArrayList<>();

        // 遍历ResultSet中的每条数据
        while (sqlRowSet.next()) {
            JSONObject jsonObj = new JSONObject();

            // 遍历每一列
            for (int i = 0; i < trueColumnNames.size(); i++) {
                String columnName =trueColumnNames.get(i);
                String value = sqlRowSet.getString(columnName);
                if(value !=null)
                    jsonObj.put(columnName, value);
                else
                    jsonObj.put(columnName, "NULL");
                //System.out.println(columnName+" "+value+"\n");
            }
            lists.add(jsonObj.toString());
        }
        //System.out.println(lists);
        String id = jobId;
        //System.out.println(id);
        redisTemplate.opsForList().rightPushAll(id, lists);
        //System.out.println(redisTemplate.opsForList().range(id,0,2));

        return true;
    }
}
