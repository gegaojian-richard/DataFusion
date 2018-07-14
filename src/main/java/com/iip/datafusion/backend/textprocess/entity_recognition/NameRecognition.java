package com.iip.datafusion.backend.textprocess.entity_recognition;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.iip.datafusion.backend.textprocess.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NameRecognition {
    private static Logger logger = LoggerFactory.getLogger(NameRecognition.class);
    public static void main(String[] args){
        logger.info("Test github");
        Map<String , List<String>> ret = getNameFromDir("ganjun_testdata");
        for(String path : ret.keySet()){
            logger.info(path + "-> ");
            for(String str : ret.get(path)){
                logger.info(str + " " );
            }
            logger.info("");
        }

    }
    // 将字符串中的所有人名实体提取出来(暂时先将整个文件所有的数据读到一起，作为一整个字符串)
    public static List<String> getName(String s){
        Segment segment = HanLP.newSegment().enableNameRecognize(true);
        List<Term> termList = segment.seg(s);
        List<String> nameList = new ArrayList<>();

        for (int i = 0 ; i < termList.size() ; i++){
            String[] tmp = termList.get(i).toString().split("/");
            if (tmp[tmp.length-1].equals("nr")){
                nameList.add(tmp[0]);
            }
        }
        return nameList;
    }
    // 从文件中目录中提取实体，每一个List<String>代表一个文件中的
    public static Map<String , List<String>> getNameFromDir(String dirPath){
        Map<String , List<String>> twoDimensionsEntities = new HashMap<>();
        List<String> filePaths = FileUtil.getAllFilePath(dirPath);
        for(String path : filePaths){
            List<String> entities = new ArrayList<>();
            String doc = FileUtil.readFileAsString(path);
            twoDimensionsEntities.put(path , getName(doc));
        }

        return twoDimensionsEntities;
    }
}
