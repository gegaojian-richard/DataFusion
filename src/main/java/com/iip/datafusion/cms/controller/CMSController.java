package com.iip.datafusion.cms.controller;

import com.iip.datafusion.cms.service.Service;
import com.iip.datafusion.util.dbutil.DataSourceProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by jingwei on 2017/12/12.
 */
@org.springframework.stereotype.Controller
public class CmsController {

    @Autowired
    Service service;

    //创建连接
    @RequestMapping(path={"/cms/creationDataBase"},method = RequestMethod.POST)
    @ResponseBody
    public String setCon(@RequestBody DataSourceProperties c){ return service.creCon(c); }


    //删除连接
    @RequestMapping(path={"/cms/deletionDataBase"},method = RequestMethod.POST)
    @ResponseBody
    public String delCon(@RequestParam(value = "nick") String nick){ return service.delCon(nick); }


    //获取当前连接的所有数据库
    @RequestMapping(path={"/cms/currentDataBase"},method = RequestMethod.GET)
    @ResponseBody
    public String getCur(String nick){
        return service.getCurrentConnection();
    }

    //获取某个数据库的具体信息
    @RequestMapping(path={"/cms/descriptionDataBase"},method = RequestMethod.GET)
    @ResponseBody
    public String desCon(@RequestParam("nick") String nick){
        return service.desCon(nick);
    }

}
