package com.iip.datafusion.dgs.integrity;

import com.iip.datafusion.backend.job.integrity.IntegrityJob;

import org.springframework.stereotype.Service;

@Service
public class IntegrityService {
    /*
    @Autowired
    private IntegrityDao integrityDao;
    @Autowired
    private CommonDao commonDao;
    */
    public String commitJob(IntegrityConfiguration integrityConfiguration)throws Exception{
        IntegrityJob integrityJob = IntegrityParser.parse(integrityConfiguration);
        return "hello";
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
