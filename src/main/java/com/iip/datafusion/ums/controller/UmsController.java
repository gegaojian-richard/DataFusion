package com.iip.datafusion.ums.controller;

import com.iip.datafusion.ums.service.UmsService;
import com.iip.datafusion.util.dbutil.DataSourceProperties;
import com.iip.datafusion.util.jsonutil.Result;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jingwei on 2017/12/19.
 */

@org.springframework.stereotype.Controller
public class UmsController {
    @Autowired
    UmsService umsService;

    @RequestMapping(path={"/ums/register"},method = RequestMethod.POST)
    @ResponseBody
    public Result register(@RequestParam("username")String username,
                         @RequestParam("password")String password,
                           HttpServletResponse response){

        Map map = umsService.register(username,password);
        //若map中返回ticket 说明注册成功，同时向浏览器写入cookie。
        //若返回msg 说明出现问题
        if(map.containsKey("ticket")){
            Cookie cookie= new Cookie("DFU",map.get("ticket").toString());
            cookie.setPath("/");
            response.addCookie(cookie);
            return new Result(1,null,"注册成功");
        }else {
            return new Result(0,map.get("msg").toString(), null);
        }
    }

    //TODO string的==改为equal()
    @RequestMapping(path={"/ums/login"},method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Result login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @CookieValue(value="DFU", defaultValue = "default") String ticket,
                        HttpServletResponse response){
        Map map;
        if (ticket.equals("default")) {
            map = umsService.login(username, password);
        }else {
            map = umsService.autoLogin(username,password,ticket);
        }
        //同上
        if(map.containsKey("ticket")){
            Cookie cookie= new Cookie("DFU",map.get("ticket").toString());
            cookie.setPath("/");
            response.addCookie(cookie);
            return new Result(1,null,"login success.now user is "+map.get("username"));
        }else if(map.containsKey("success")){
            return new Result(1,null,map.get("success").toString());
        }
        else {
            return new Result(0,map.get("msg").toString(), null);
        }
    }

    @RequestMapping(path={"/ums/logout"},method = RequestMethod.POST)
    @ResponseBody
    public Result logout(@CookieValue("DFU") String ticket){
        umsService.logout(ticket);
        return new Result(1,null,"账户已登出");
    }

}
