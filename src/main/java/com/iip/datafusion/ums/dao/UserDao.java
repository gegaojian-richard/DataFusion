package com.iip.datafusion.ums.dao;

import com.iip.datafusion.ums.model.MD5;
import com.iip.datafusion.ums.model.User;
import com.iip.datafusion.util.dbutil.DataSourceRouterManager;
import com.iip.datafusion.util.jsonutil.Result;
import javafx.scene.input.DataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jingwei on 2017/12/19.
 */
@Service
public class UserDao {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    DataSourceRouterManager dataSourceRouterManager;

    public User getUserByName(String username) {
        dataSourceRouterManager.setCurrentDataSourceKey("primary");
//        try {
//            return (User) jdbcTemplate.queryForObject(
//                    "select id,username,password,salt from user_table where username=? ",
//                    new Object[]{username}, new RowMapper() {
//                        @Override
//                        public Object mapRow(ResultSet rs, int rowNum)
//                                throws SQLException {
//                            User user = new User();
//                            user.setUsername((rs.getString("username")));
//                            user.setPassword(rs.getString("password"));
//                            user.setSalt(rs.getString("salt"));
//                            user.setId(rs.getInt("id"));
////                            int a = rs.getInt("id");
////                            user.setId(a);
//                            return user;
//                        }
//                    }
//            );
//        }
//        catch (EmptyResultDataAccessException e){
//            return null;
//        }
        try {
            Map<String, Object> map = jdbcTemplate.queryForMap(
                    "select id,username,password,salt from user_table where username=? ",
                    new Object[]{username});
            User user = new User();
            user.setUsername(map.get("username").toString());
            user.setPassword(map.get("password").toString());
            user.setSalt(map.get("salt").toString());
            user.setId(Integer.parseInt(map.get("id").toString()));
            return user;
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public User getUserById(int userId) {
        dataSourceRouterManager.setCurrentDataSourceKey("primary");

        try {
            Map<String, Object> map = jdbcTemplate.queryForMap(
                    "select id,username,password,salt from user_table where id=? ",
                    new Object[]{userId});
            User user = new User();
            user.setUsername(map.get("username").toString());
            user.setPassword(map.get("password").toString());
            user.setSalt(map.get("salt").toString());
            user.setId(Integer.parseInt(map.get("id").toString()));
            return user;
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public Map<String ,Object> addUser(User user) {
        Map<String,Object> map = new HashMap<>();

        dataSourceRouterManager.setCurrentDataSourceKey("primary");
        boolean b = jdbcTemplate.update("INSERT INTO user_table(username,password,salt) values (?,?,?)",
                new Object[]{user.getUsername(),user.getPassword(),user.getSalt()})>0;
        if (!b){
            map.put("msg","insert user failed");
        }
        return map;
    }
}