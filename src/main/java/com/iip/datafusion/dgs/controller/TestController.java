package com.iip.datafusion.dgs.controller;

import com.iip.datafusion.dgs.service.DgsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
    @Autowired
    DgsService service;

    @RequestMapping(value = {"/dgs"},method={RequestMethod.GET})
    @ResponseBody
    public String get(@RequestParam(value="table")String table,@RequestParam(value = "column") String column){
        return service.getEmptyEntity(table,column);
    }

}
