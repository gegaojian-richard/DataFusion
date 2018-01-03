package com.iip.datafusion.configuration;

import com.iip.datafusion.interceptor.ProcessInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by tsy on 2017/12/28.
 */
@Component
public class DatafusionConfiguration extends WebMvcConfigurerAdapter {
    @Autowired
    ProcessInterceptor processInterceptor;
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(processInterceptor);
    }
}
