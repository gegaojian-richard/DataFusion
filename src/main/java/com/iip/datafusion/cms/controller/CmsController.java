package com.iip.datafusion.cms.controller;

import com.iip.datafusion.backend.AccuracyManager;
import com.iip.datafusion.backend.job.accuracy.AccuracyJob;
import com.iip.datafusion.cms.dao.CmsDao;
import com.iip.datafusion.cms.service.CmsService;
import com.iip.datafusion.ums.service.UmsService;
import com.iip.datafusion.util.dbutil.DataSourceProperties;
import com.iip.datafusion.util.jsonutil.Result;
import com.iip.datafusion.util.userutil.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * Created by jingwei on 2017/12/12.
 */
@org.springframework.stereotype.Controller
public class CmsController {

    @Autowired
    CmsService service;
    @Autowired
    UserManager userManager;

    @Autowired
    CmsDao cmsDao;

    /*创建连接
    @return dbid
     */
    @RequestMapping(path={"/cms/creationDataBase"},method = RequestMethod.POST)
    @ResponseBody
    public Result setCon(@RequestBody DataSourceProperties c){
        c.setUrl(c.getUrl());
        return service.creCon(c);
    }


    //删除连接
    @RequestMapping(path={"/cms/deletionDataBase"},method = RequestMethod.POST)
    @ResponseBody
    public Result delCon(@RequestParam(value = "nick") String id){ return service.delCon(id); }


    //获取当前连接的所有数据库
    @RequestMapping(path={"/cms/currentDataBase"},method = RequestMethod.GET)
    @ResponseBody
    public Result getCur(){
        return service.getCurrentConnection();
    }

    //获取某个数据库的具体信息
    @RequestMapping(path={"/cms/descriptionDataBase"},method = RequestMethod.GET)
    @ResponseBody
    public Result desCon(@RequestParam("nick") String id){ return service.desCon(id);}

    //获取某个表的预览信息
    @RequestMapping(path = "/cms/preview",method = RequestMethod.GET)
    @ResponseBody
    public Result previewCon(@RequestParam("display") String id,
                             @RequestParam("table") String table,
                             @RequestParam(value = "num",defaultValue = "50")String  num){
        return service.previewCon(id,table,num);
    }
}
