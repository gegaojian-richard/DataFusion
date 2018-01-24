package com.iip.datafusion.backend.executor;

import com.iip.datafusion.util.dbutil.DataSourceRouterManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;

@Component
public class SQLExecutor {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    SqlRowSet doSQLTask(String sql, String datasourceid){
        DataSourceRouterManager.setCurrentDataSourceKey(datasourceid);
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql);
        return sqlRowSet;
    }
}
