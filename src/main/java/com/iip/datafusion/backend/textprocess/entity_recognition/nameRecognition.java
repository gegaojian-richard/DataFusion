package com.iip.datafusion.backend.textprocess.entity_recognition;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.iip.datafusion.backend.textprocess.util.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class nameRecognition {
    public static void main(String[] args){
        System.out.println("Test github");
        //System.out.println(HanLP.segment("你好，欢迎使用HanLP汉语处理包！"));
        String[] testCase = new String[]{
                "签约仪式前，秦光荣、李纪恒、仇和等一同会见了参加签约的企业家。",
                "王国强、高峰、汪洋、张朝阳光着头、韩寒、小四",
                "张浩和胡健康复员回家了",
                "王总和小丽结婚了",
                "编剧邵钧林和稽道青说",
                "这里有关天培的有关事迹",
                "龚学平等领导,邓颖超生前",
                "12月23日，我在/南京，更新",
        };
        nameRecognition n = new nameRecognition();
        //System.out.println(n.getName("签约仪式前，秦光荣、李纪恒、仇和等一同会见了参加签约的企业家。"));
        Segment segment = HanLP.newSegment().enableNameRecognize(true);
        for (String sentence : testCase)
        {
            List<Term> termList = segment.seg(sentence);
            for (int i = 0; i < termList.size() ; i++){
                System.out.println(termList.get(i));
            }
            //List<Term> termList = NShortSegment.seg(sentence);
            //System.out.println("N-最短分词：" + NShortSegment.seg(sentence) + "\n最短路分词：" + ShortestSegment.seg(sentence));
            //System.out.println(termList);
        }
        List<String> ret = getName(testCase[0] + testCase[1] + testCase[2]+testCase[3]);
        for (String name : ret){
            System.out.println(name);
        }
    }
    // 将字符串中的所有人名实体提取出来(暂时先将整个文件所有的数据读到一起，作为一整个字符串)
    public static List<String> getName(String s){
        Segment segment = HanLP.newSegment().enableNameRecognize(true);
        List<Term> termList = segment.seg(s);
        List<String> nameList = new ArrayList<>();
        for (int i = 0 ; i < termList.size() ; i++){
            String[] tmp = termList.get(i).toString().split("/");
            if (tmp[tmp.length-1].equals("nr")) nameList.add(tmp[0]);
        }
        return nameList;
    }
    // 从所有文件中提取实体，每一个List<String>代表一个文件中的
    public static List<List<String>> getNameFromDir(List<File> files){
        List<List<String>> entities = new ArrayList<>();
        for(File file : files){
            String doc = FileUtil.readFileAsString(file.getPath());
            entities.add(getName(doc));
        }
        return entities;
    }
}
