package com.iip.datafusion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iip.datafusion.backend.*;
import com.iip.datafusion.backend.job.algorithm.TextRankJob;
import com.iip.datafusion.backend.job.algorithm.TopicModelJob;
import com.iip.datafusion.util.dbutil.DataSourceRouter;
import com.iip.datafusion.util.dbutil.DataSourceRouterManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextListener;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
//@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class DatafusionApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatafusionApplication.class, args);

		// 初始化底层功能模块
		JoinManager.getInstance().init();
		AccuracyManager.getInstance().init();
		ConsistencyManager.getInstance().init();
		UpdateConsistencyManager.getInstance().init();
		IntegrityManager.getInstance().init();

		// ganjun Manager init()
		TestManager.getInstance().init();
		TextRankManager.getInstance().init();
		NameRecognitionManager.getInstance().init();
		TFIDFManager.getInstance().init();
		TopicModelManager.getInstance().init();
	}

}
