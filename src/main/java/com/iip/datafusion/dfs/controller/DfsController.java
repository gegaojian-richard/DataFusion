package com.iip.datafusion.dfs.controller;

import com.iip.datafusion.dfs.model.JoinConfiguration;
import com.iip.datafusion.dfs.service.DataFusionService;
import com.iip.datafusion.util.dbutil.DataSourceRouterManager;
import com.iip.datafusion.util.jsonutil.Result;
import com.iip.datafusion.util.userutil.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by GeGaojian on 2017/12/22.
 */

@Controller
@RequestMapping("/dfs")
public class DfsController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    DataFusionService dataFusionService;
    @Autowired
    UserManager userManager;

    @RequestMapping(path = {"/commitjob"}, method = RequestMethod.POST)
    @ResponseBody
    public Result commitJob(@RequestBody JoinConfiguration joinConfiguration) {
        try {
            Map map = dataFusionService.commitJob(joinConfiguration, userManager.getUserId());
            Result res = new Result(1, "Task Submitted successfully", null);

            return res;
        } catch (Exception e) {
            return new Result(0, e.getMessage(), null);
        }
    }

    // todo: 测试
    @RequestMapping(path = {"/initdata"}, method = {RequestMethod.GET})
    @ResponseBody
    public String initdata() {
        String sql = "INSERT INTO t1 " +
                "(id, f11, f12, f13) VALUES (?, ?, ?, ?)";
        DataSourceRouterManager.setCurrentDataSourceKey("primary");
        List<Object[]> parameters = new ArrayList<Object[]>();
        for (int i = 0; i < 1000; i++) {
            parameters.add(new Object[]{i,
                    "f11" + i, "f12" + i, "f13" + i}
            );
        }
        jdbcTemplate.batchUpdate(sql, parameters);
        return "ok";
    }

    // todo: 测试
    @RequestMapping(path = {"/testjoin"}, method = {RequestMethod.GET})
    @ResponseBody
    public String testjoin() {
        String sql = "select * from t1";
        DataSourceRouterManager.setCurrentDataSourceKey("primary");
        SqlRowSet resultSet = jdbcTemplate.queryForRowSet(sql);


        return "ok";
    }
}
