package com.iip.datafusion.dgs.dao;

import com.iip.datafusion.util.dbutil.DataSourceRouterManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import javax.sql.RowSet;

@Repository
public class AccuracyDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public SqlRowSet selectData(String dataSourceId,String tableName,String whereClause){
        DataSourceRouterManager.setCurrentDataSourceKey(dataSourceId);
        String sql = "SELECT * FROM " + tableName + " WHERE " + whereClause;
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql);
        return sqlRowSet;
    }

    public boolean updateData(String dataSourceId,String tableName,String columnName,String newValue,String whereClause){
        DataSourceRouterManager.setCurrentDataSourceKey(dataSourceId);
        String sql = "UPDATE " + tableName +" SET " + columnName + " = ? " + " WHERE " + whereClause;
        int rows = jdbcTemplate.update(sql,new Object[]{newValue});
        if(rows > 0) return true;
        else return false;
    }

    public boolean updateData2(String dataSourceId,String tableName,String setClause,String whereClause)
    {
        DataSourceRouterManager.setCurrentDataSourceKey(dataSourceId);
        String sql = "UPDATE " + tableName +" SET " + setClause + " WHERE " + whereClause;
        int rows = jdbcTemplate.update(sql);
        if(rows > 0) return true;
        else return false;
    }

    public SqlRowSet getPrimaryKey(String dataSourceId,String tableName){
        DataSourceRouterManager.setCurrentDataSourceKey(dataSourceId);
        String sql = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE WHERE TABLE_NAME = ?";
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql,new Object[]{tableName});
        return  sqlRowSet;
    }
}
