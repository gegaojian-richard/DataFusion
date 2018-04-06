package com.iip.datafusion.dgs.service;

import com.iip.datafusion.dgs.dao.AccuracyDao;
import com.iip.datafusion.dgs.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author zengc
 * @date 2018/1/17 19:43
 */
@Service
public class CommonService {

    @Autowired
    private CommonDao commonDao;

    public SqlRowSet doSelect(String dataSourceId, String tableName, String selectClause,String whereClause){
        return commonDao.doSelect(dataSourceId,tableName,selectClause,whereClause);
    }

    public ArrayList<String> getTableColumnList(String dataSourceId, String tableName){
        return commonDao.getTableColumnList(dataSourceId,tableName);
    }

    public boolean doUpdate(String dataSourceId, String tableName,String updateClause,String whereClause){
        return commonDao.doUpdate(dataSourceId,tableName,updateClause,whereClause);
    }

    public boolean doReplace(String dataSourceId, String tableName,String columnClause,String valueClause){
        return commonDao.doReplace(dataSourceId,tableName,columnClause,valueClause);
    }

    public void doExecute(String dataSourceId,String sql)throws Exception{
        commonDao.doExecute(dataSourceId,sql);
    }
}
