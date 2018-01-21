package com.iip.datafusion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iip.datafusion.backend.AccuracyManager;
import com.iip.datafusion.backend.ConsistencyManager;
import com.iip.datafusion.backend.IntegrityManager;
import com.iip.datafusion.backend.JoinManager;
import com.iip.datafusion.util.dbutil.DataSourceRouter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextListener;

@SpringBootApplication
//@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class DatafusionApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatafusionApplication.class, args);

		// 初始化底层功能模块
		JoinManager.getInstance().init();
		AccuracyManager.getInstance().init();
		ConsistencyManager.getInstance().init();
		IntegrityManager.getInstance().init();
	}


//	@Bean
//	public RequestContextListener requestContextListener(){
//		return new RequestContextListener();
//	}
}
