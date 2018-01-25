package com.iip.datafusion.dgs.integrity;

import com.iip.datafusion.backend.IntegrityManager;
import com.iip.datafusion.backend.JoinManager;
import com.iip.datafusion.backend.job.integrity.IntegrityJob;

import com.iip.datafusion.util.jsonutil.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

@Service
public class IntegrityService {

    @Autowired
    private IntegrityDao integrityDao;
    /*
    @Autowired
    private CommonDao commonDao;
    */
    public IntegrityJob commitJob(IntegrityConfiguration integrityConfiguration)throws Exception{
        IntegrityJob integrityJob = IntegrityParser.parse(integrityConfiguration);
        IntegrityManager.getInstance().commitJob(integrityJob);

        return integrityJob;
    }

    public SqlRowSet doQuery(String sql)throws Exception{
        return  integrityDao.doQuery(sql);
    }

    public boolean doExecute(String sql){
        return integrityDao.doExecute(sql);
    }
    /*
    public SqlRowSet tableAnyEmptyCheck(String dataSourceId,String tableName, String whereClause){
        return commonDao.doSelect(dataSourceId,tableName,whereClause);
    }

    public ArrayList<String> getTableColumnList(String dataSourceId, String tableName){
        return integrityDao.getTableColumnList(dataSourceId,tableName);
    }

    public boolean updateColumns(String dataSourceId, String tableName,String updateClause,String whereClause){
        return commonDao.doUpdate(dataSourceId,tableName,updateClause,whereClause);
    }

    public boolean doReplace(String dataSourceId, String tableName,String columnClause,String valueClause){
        return commonDao.doReplace(dataSourceId,tableName,columnClause,valueClause);
    }
    */

}
