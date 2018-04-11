package com.iip.datafusion.dgs.dao;

import com.iip.datafusion.backend.jdbchelper.JDBCHelper;
import com.iip.datafusion.util.dbutil.DataSourceRouterManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.stereotype.Repository;

import javax.sql.RowSet;
import java.util.ArrayList;

@Repository
public class AccuracyDao {

    private final JdbcTemplate jdbcTemplate = JDBCHelper.getJdbcTemplate();

    public SqlRowSet doSelect(String tableName, String selectClause, String whereClause){
        String sql = String.format("SELECT %s FROM %s WHERE %s",selectClause,tableName,whereClause);
        return jdbcTemplate.queryForRowSet(sql);
    }

    public boolean doUpdate(String tableName,String updateClause,String whereClause){
        String sql = String.format("UPDATE %s SET %s WHERE %s",tableName,updateClause,whereClause);
        try {
            jdbcTemplate.execute(sql);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean doReplace(String tableName,String columnClause,String valueClause){
        String sql = String.format("REPLACE INTO %s  %s VALUES %s",tableName,columnClause,valueClause);
        try {
            jdbcTemplate.execute(sql);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public ArrayList<String> getTableColumnList(String tableName){

        ArrayList<String> result = new ArrayList<String>();
        String sql = String.format("SELECT * FROM %s LIMIT 0",tableName);
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql);
        SqlRowSetMetaData sqlRsmd = sqlRowSet.getMetaData();
        for(int i=1;i<=sqlRsmd.getColumnCount();i++){
            result.add(sqlRsmd.getColumnName(i));
        }
        return result;
    }

    public String getColumnType(String tableName,int i){
        String sql = String.format("SELECT * FROM %s LIMIT 0",tableName);
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql);
        SqlRowSetMetaData sqlRsmd = sqlRowSet.getMetaData();
        return sqlRsmd.getColumnTypeName(i);
    }

}
