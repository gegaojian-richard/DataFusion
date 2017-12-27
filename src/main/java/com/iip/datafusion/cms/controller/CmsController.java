package com.iip.datafusion.cms.controller;

import com.iip.datafusion.cms.service.CmsService;
import com.iip.datafusion.ums.service.UmsService;
import com.iip.datafusion.util.dbutil.DataSourceProperties;
import com.iip.datafusion.util.jsonutil.Result;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by jingwei on 2017/12/12.
 */
@org.springframework.stereotype.Controller
public class CmsController {

    @Autowired
    CmsService service;
    @Autowired
    UmsService umsService;

    @RequestMapping("test")
    @ResponseBody
    public Result test(HttpSession session,
                       @CookieValue(value = "DFU",defaultValue = "111") String ticket){
        Result res = umsService.getUserNameByTicket(ticket);
        if(res.getStatus()==0){
            return new Result(0,res.getMsg(),null);
        }
        else {
            return new Result(1, null, "test, this sessionid is: " + session.getId() +
                    ";the user name is "+res.getData()
            );
        }
    }

    //创建连接
    @RequestMapping(path={"/cms/creationDataBase"},method = RequestMethod.POST)
    @ResponseBody
    public Result setCon(@RequestBody DataSourceProperties c){ return service.creCon(c); }


    //删除连接
    @RequestMapping(path={"/cms/deletionDataBase"},method = RequestMethod.POST)
    @ResponseBody
    public Result delCon(@RequestParam(value = "nick") String nick){ return service.delCon(nick); }


    //获取当前连接的所有数据库
    @RequestMapping(path={"/cms/currentDataBase"},method = RequestMethod.GET)
    @ResponseBody
    public Result getCur(String nick){
        return service.getCurrentConnection();
    }

    //获取某个数据库的具体信息
    @RequestMapping(path={"/cms/descriptionDataBase"},method = RequestMethod.GET)
    @ResponseBody
    public Result desCon(@RequestParam("nick") String nick){
        return service.desCon(nick);
    }

    //获取某个表的预览信息
    @RequestMapping(path = "/cms/preview",method = RequestMethod.GET)
    @ResponseBody
    public Result previewCon(@RequestParam("display") String display,
                             @RequestParam("table") String table,
                             @RequestParam(value = "num",defaultValue = "10")String  num){
        return service.previewCon(display,table,num);
    }

}
