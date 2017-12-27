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

    @RequestMapping(path={"/ums/login"},method = RequestMethod.POST)
    @ResponseBody
    public Result login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpServletResponse response){
        Map map = umsService.login(username,password);
        //同上
        if(map.containsKey("ticket")){
            Cookie cookie= new Cookie("DFU",map.get("ticket").toString());
            cookie.setPath("/");
            response.addCookie(cookie);
            return new Result(1,null,null);
        }else {
            return new Result(0,map.get("msg").toString(), null);
        }
    }

    @RequestMapping(path={"/ums/logout"},method = RequestMethod.POST)
    @ResponseBody
    public Result logout(@CookieValue("DFU") String ticket){
        umsService.logout(ticket);
        return new Result(1,null,"账户已登出");
    }



        @Autowired private UmsService.Count count;
        @RequestMapping("/greeting")
        @ResponseBody
        public String greetingForm() {
            Integer i;
            if(count.getI() == null) i = 0;
            else {
                i=count.getI();
                i++;
            }
            return "greeting: "+i;
        }

}