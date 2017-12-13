package com.iip.datafusion;

import com.iip.datafusion.util.dbutil.DataSourceRouter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextListener;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class DatafusionApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatafusionApplication.class, args);
	}

//	@Bean
//	public RequestContextListener requestContextListener(){
//		return new RequestContextListener();
//	}
}
