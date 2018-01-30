package com.iip.datafusion.dgs.model.accuracy;

import com.iip.datafusion.backend.jdbchelper.JDBCHelper;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class AccuracyCheckHelper implements ApplicationContextAware {

    private static AccuracyCheckUnit accuracyCheckUnit;

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(AccuracyCheckHelper.applicationContext == null) {
            AccuracyCheckHelper.applicationContext = applicationContext;
        }
    }

    public static AccuracyCheckUnit getAccuracyCheckUnit() {
        return applicationContext.getBean("accuracyCheckUnit",AccuracyCheckUnit.class);
    }
}
