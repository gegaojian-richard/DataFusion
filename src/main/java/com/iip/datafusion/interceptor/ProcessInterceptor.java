package com.iip.datafusion.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * Created by tsy on 2017/12/28.
 */
@Component
public class ProcessInterceptor implements HandlerInterceptor {
    private static Logger logger = LoggerFactory.getLogger(ProcessInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

     //   String allowedOrigin ="http://localhost:8989";
        String origin = httpServletRequest.getHeader("Origin");
    //    httpServletResponse.setHeader("Access-Control-Allow-Origin", origin.contains(allowedOrigin) ? origin : "");
 //       response.setHeader("Vary", "Origin");
 //       httpServletResponse.setHeader("Access-Control-Allow-Origin", "localhost:8989/*");
        httpServletResponse.setHeader("Access-Control-Allow-Origin", origin);

        httpServletResponse.setHeader("Access-Control-Allow-Headers", "Content-Type,Content-Length, Authorization, Accept,X-Requested-With");

        httpServletResponse.setHeader("Access-Control-Allow-Methods","PUT,POST,GET,DELETE,OPTIONS");

        httpServletResponse.setHeader("X-Powered-By","Jetty");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials","true");


        String method= httpServletRequest.getMethod();

        if (method.equals("OPTIONS")){
            httpServletResponse.setStatus(200);
            return false;
        }

        logger.info(method);

        return true;
    }
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,ModelAndView modelAndView)
            throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }  }
