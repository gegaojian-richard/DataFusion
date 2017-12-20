package com.iip.datafusion.ums;

import com.iip.datafusion.util.dbutil.DataSourceProperties;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by jingwei on 2017/12/19.
 */

@org.springframework.stereotype.Controller
public class UmsController {


    @RequestMapping(path={"/kjb/ums/register"},method = RequestMethod.POST)
    @ResponseBody
    public String setCon(@RequestBody DataSourceProperties c){ return null; }

}
