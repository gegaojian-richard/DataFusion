package com.iip.datafusion.ums.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iip.datafusion.ums.model.LoginTicket;
import com.iip.datafusion.ums.model.User;
import com.iip.datafusion.util.dbutil.DataSourceRouterManager;
import com.iip.datafusion.util.jsonutil.Result;
import javafx.scene.input.DataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by jingwei on 2017/12/25.
 */
@Service
public class LoginTicketDao {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    DataSourceRouterManager dataSourceRouterManager;

    public Result insert(LoginTicket loginTicket){
        dataSourceRouterManager.setCurrentDataSourceKey("primary");
        boolean b = jdbcTemplate.update("INSERT INTO ticket(userId,expired,ticket,status) values (?,?,?,?)",
                new Object[]{loginTicket.getUserId(),loginTicket.getExpired(),loginTicket.getTicket(),loginTicket.getStatus()})>0;
        return null;
    }

    public LoginTicket getObjectByTicket(String ticket) {
        dataSourceRouterManager.setCurrentDataSourceKey("primary");
        try {
            Map<String, Object> map = jdbcTemplate.queryForMap(
                    "select id,userID,Expired,ticket,status from ticket where ticket=? ",
                    new Object[]{ticket});
            LoginTicket loginTicket = new LoginTicket();
            loginTicket.setId(Integer.parseInt(map.get("id").toString()));
            loginTicket.setUserId(Integer.parseInt(map.get("userId").toString()));
            DateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
            loginTicket.setExpired(dataFormat.parse(map.get("Expired").toString()));
            loginTicket.setTicket(ticket);
            loginTicket.setStatus(Integer.parseInt(map.get("status").toString()));
            return loginTicket;
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
        catch (ParseException e){
            return null;
        }
    }

    public void updateStatus(String ticket, Object i) {
        dataSourceRouterManager.setCurrentDataSourceKey("primary");
        jdbcTemplate.update("update ticket set status=? where ticket=?",
                new Object[]{i,ticket});
    }

    public void deleteTicket(String ticket) {
        dataSourceRouterManager.setCurrentDataSourceKey("primary");
        jdbcTemplate.update("delete from ticket where ticket=?",
                new Object[]{ticket});
    }

}
