package com.iip.datafusion.dgs.dao;

import com.iip.datafusion.util.dbutil.DataSourceRouterManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * @author zengc
 * @date 2018/1/17 18:30
 */
@Repository
public class CommonDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public SqlRowSet doSelect(String dataSourceId, String tableName, String selectClause,String whereClause){
        DataSourceRouterManager.setCurrentDataSourceKey(dataSourceId);

        String sql = String.format("SELECT %s FROM %s where %s",selectClause,tableName,whereClause);

        return jdbcTemplate.queryForRowSet(sql);

    }

    public boolean doUpdate(String dataSourceId,String tableName,String updateClause,String whereClause){

        DataSourceRouterManager.setCurrentDataSourceKey(dataSourceId);

        String sql = String.format("update %s SET %s where %s",tableName,updateClause,whereClause);
        //System.out.println(sql);
        try {
            jdbcTemplate.execute(sql);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean doReplace(String dataSourceId, String tableName,String columnClause,String valueClause){
        DataSourceRouterManager.setCurrentDataSourceKey(dataSourceId);

        String sql = String.format("REPLACE INTO %s  %s VALUES %s",tableName,columnClause,valueClause);
        //System.out.println(sql);
        try {
            jdbcTemplate.execute(sql);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public ArrayList<String> getTableColumnList(String dataSourceId, String tableName){

        ArrayList<String> result = new ArrayList<>();
        DataSourceRouterManager.setCurrentDataSourceKey(dataSourceId);

        String sql = String.format("SELECT * FROM %s limit 0",tableName);

        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql);
        SqlRowSetMetaData sqlRsmd = sqlRowSet.getMetaData();
        for(int i=1;i<=sqlRsmd.getColumnCount();i++){
            result.add(sqlRsmd.getColumnName(i));
            //System.out.println(sqlRsmd.getColumnTypeName(i));
        }
        return result;
    }

    public void doExecute(String dataSourceId,String sql)throws Exception{
        DataSourceRouterManager.setCurrentDataSourceKey(dataSourceId);
        jdbcTemplate.execute(sql);
    }
}
