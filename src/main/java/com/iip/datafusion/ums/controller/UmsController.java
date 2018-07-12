package com.iip.datafusion.ums.controller;

import com.iip.datafusion.ums.model.User;
import com.iip.datafusion.ums.service.UmsService;
import com.iip.datafusion.util.dbutil.DataSourceProperties;
import com.iip.datafusion.util.jsonutil.Result;
import com.iip.datafusion.util.userutil.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    UserManager userManager;

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
            // todo: 目前整个系统虽然分了多个子系统，但是在web应用服务器中仍然是一个应用，我认为暂时不需要全局共享的cookie - GeGaojian - 2018/01/01
            cookie.setPath("/");
            response.addCookie(cookie);
            return new Result(1,null,"注册成功");
        }else {
            return new Result(0,map.get("msg").toString(), null);
        }
    }


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
    public Result logout(@CookieValue(value = "DFU",defaultValue = "default") String ticket){
        if(!ticket.equals("default")) {
            //在数据库中将ticket置为无效
            umsService.logout(ticket);
        }
        //将UserManager置为未登录状态
        userManager.setState(false);
        return new Result(1,null,"账户已登出");
    }


    @RequestMapping(path={"/ums/autoLogin"},method = RequestMethod.POST)
    @ResponseBody
    //用户打开或者刷新页面时，应该向该地址发送请求
    public Result auto(@CookieValue(value = "DFU",defaultValue = "default") String ticket){
        Map<String,Object> map = new HashMap<>();
        if(!ticket.equals("default")) {
            //自动登录
            map =  umsService.autoLogin(ticket);
        }else{
            map.put("msg","未找到cookie");
        }

        //判断登录成功与否
        if(map.containsKey("msg")){
            return new Result(0,map.get("msg").toString(),null);
        }else {
            //成功登录，设置userManager
            userManager.setState(true);
            User user = (User) map.get("success");
            userManager.setUserName(user.getUsername());
            userManager.setUserId(user.getId());
            return new Result(1, null, "自动登录成功");
        }
    }
}
