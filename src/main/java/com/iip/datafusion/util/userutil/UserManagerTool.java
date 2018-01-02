package com.iip.datafusion.util.userutil;

import com.iip.datafusion.ums.model.User;
import com.iip.datafusion.util.dbutil.DataSourceRouterManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UserManagerTool {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    DataSourceRouterManager dataSourceRouterManager;

    public int getUserIDByTicket(String ticket){
        dataSourceRouterManager.setCurrentDataSourceKey("primary");
        int result;
        try {
            String sql = "SELECT userId FROM ticket WHERE ticket = ?";

            result = jdbcTemplate.queryForObject(sql, new Object[] { ticket }, Integer.class);

            return result;
        }
        catch (EmptyResultDataAccessException e){
            return 0;
        }
    }
}
