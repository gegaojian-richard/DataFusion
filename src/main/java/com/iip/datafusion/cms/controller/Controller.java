package com.iip.datafusion.cms.controller;

import com.iip.datafusion.cms.service.ConService;
import org.springframework.web.bind.annotation.*;

/**
 * Created by jingwei on 2017/12/12.
 */
@org.springframework.stereotype.Controller
public class Controller {


    //创建连接
    @RequestMapping(path={"/cms/creation"},method = RequestMethod.POST)
    @ResponseBody
    public String setCon(@RequestBody ConService.conBean c){
        return ConService.creCon(c);
    }

    //删除连接
    @RequestMapping(path={"/cms/deletion"},method = RequestMethod.POST)
    @ResponseBody
    public String delCon(@RequestParam(value = "nick") String nick){
        return ConService.delCon(nick);
    }


    //获取连接信息
    @RequestMapping(path={"/cms/description"},method = RequestMethod.GET)
    @ResponseBody
    public String desCon(@RequestParam("nick") String nick){
        return ConService.desCon(nick);
    }

}
