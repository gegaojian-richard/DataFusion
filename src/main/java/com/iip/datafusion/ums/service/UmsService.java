package com.iip.datafusion.ums.service;

import com.iip.datafusion.ums.dao.LoginTicketDao;
import com.iip.datafusion.ums.dao.UserDao;
import com.iip.datafusion.ums.model.LoginTicket;
import com.iip.datafusion.ums.model.MD5;
import com.iip.datafusion.ums.model.User;
import com.iip.datafusion.util.jsonutil.Result;
import com.iip.datafusion.util.userutil.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by jingwei on 2017/12/19.
 */
@Service
public class UmsService {
    @Autowired
    UserDao userDao;
    @Autowired
    LoginTicketDao loginTicketDao;
    @Autowired
    UserManager userManager;

    public Map<String ,Object> register(String username, String password){
        Map<String,Object> map = new HashMap();
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            map.put("msg","username/password is empty!");
            return map;
        }

        User user = userDao.getUserByName(username);
        if(user!=null){
            map.put("msg","username existed!");
            return map;
        }

        //设置密码
        user = new User();
        String salt = UUID.randomUUID().toString().substring(0,5);
        String pwd = MD5.GetMD5Code(password+salt);
        user.setUsername(username);
        user.setSalt(salt);
        user.setPassword(pwd);
        Map map1 = userDao.addUser(user);
        if(map1.containsKey("msg"))
            map.put("msg",map1.get("msg").toString());

        //登录
        String ticket= addLoginTicket(userDao.getUserByName(user.getUsername()).getId());
        map.put("ticket",ticket);

        return map;
    }

    public Map<String,Object> login(String username, String password){
        Map<String,Object> map = new HashMap<>();

        User user = userDao.getUserByName(username);
        if(user==null){
            map.put("msg","用户名不存在");
            return map;
        }

        //验证密码
        if(!MD5.GetMD5Code(password+user.getSalt()).equals(user.getPassword())){
            map.put("msg","密码错误");
        }

        //得到ticket，写入数据库，并返回前段的response
        String ticket= addLoginTicket(user.getId());
        map.put("ticket",ticket);

        //为当前的session创建一个userManager存储用户的id name等信息
        userManager.setUserName(username);
        userManager.setUserId(user.getId());
        map.put("username",userManager.getUserName());

        return map;
    }

    public String addLoginTicket(int userId){
        //设置过期时间
        Date now = new Date();
        LoginTicket loginTicket = new LoginTicket();
        now.setTime(now.getTime()+3600*24*10*1000);
        loginTicket.setExpired(now);

        //设置状态（0有效）用户id ticket
        loginTicket.setStatus(0);
        loginTicket.setUserId(userId);
        String ticket = UUID.randomUUID().toString().replace("-","");
        loginTicket.setTicket(ticket);

        //写入数据库
        loginTicketDao.insert(loginTicket);
        return ticket;
    }

    public Result getUserNameByTicket(String ticket) {
        LoginTicket loginTicket = loginTicketDao.getObjectByTicket(ticket);

        //判断ticket是否存在于数据库中
        if(loginTicket==null){
            return new Result(0,"请先登录",null);
        }

        //判断是否ticket过期
        Date now = new Date();
        if (loginTicket.getExpired().before(now) || loginTicket.getStatus()==1){
            //将状态改为1
            loginTicketDao.updateStatus(ticket,1);
            return new Result(0,"登录已过期，请重新登录",null);
        }

        User user = userDao.getUserById(loginTicket.getUserId());
        return new Result(1,null,user.getUsername());
    }

    public void logout(String ticket) {
        //TODO 考虑后期数据库的存储性能，可能需要删除无效的cookie，或者统一定时维护
        loginTicketDao.updateStatus(ticket, 1);
    }

    public Map autoLogin(String username, String password, String ticket) {
        Map<String ,Object> map = new HashMap<>();
        LoginTicket loginTicket = loginTicketDao.getObjectByTicket(ticket);
        if(loginTicket==null){
            map=login(username,password);
            return map;
        }
        User user = userDao.getUserById(loginTicket.getUserId());

        //如果ticket未过期，且和用户名符合，自动登录成功
        if(loginTicket.getExpired().after(new Date()) && user.getUsername().equals(username)){
            userManager.setUserName(username);
            userManager.setUserId(user.getId());
            map.put("success","自动登录成功");
            //初始化userManager
            return map;
        }
        //如果ticket超时 则用当前的用户名，密码登录，并删除原ticket
        else if(loginTicket.getExpired().before(new Date())){
            loginTicketDao.deleteTicket(ticket);
            map = login(username,password);
            return map;
        }
        //如果用户名与ticket不符合，说明换了一个用户登录，重新写入ticket，但数据库中ticket不删除
//        else if(!user.getUsername().equals(username)){
        else{
            map = login(username,password);
            return map;
        }

    }

    public Map<String,Object> autoLogin(String ticket) {
        Map<String,Object> map = new HashMap<>();
        //检查ticket是否有效
        LoginTicket loginTicket = loginTicketDao.getObjectByTicket(ticket);
        if(loginTicket==null){
            map.put("msg","cookie错误");
        }else if(loginTicket.getStatus()==1){
            map.put("msg","无效的cookie（已执行过登出）");
        }
        else if(loginTicket.getExpired().before(new Date())){
            map.put("msg","cookie已过期");
        }
        //成功，返回对应的用户名
        else {
            User user = userDao.getUserById(loginTicket.getUserId());
            map.put("success",user);
        }
        return map;
    }
}
