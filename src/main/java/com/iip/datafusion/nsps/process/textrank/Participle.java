package com.iip.datafusion.nsps.process.textrank;

/**
 * Created by ganjun on 2018/1/3.
 */

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

public class Participle {
    private static Set<String> stopWords = new HashSet<>();

    /*
    从路径为path的文件中加载停词表。
     */
    public static boolean loadStopWords(String path){
        if(stopWords.isEmpty() == false) return true; // 已经加载过停词表，不必重复加载。
        try{
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            while((line = br.readLine()) != null) {
                stopWords.add(line);
            }
            return true;
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public static List<String> sentenceParticiple(String path){
        List<String> sentences = new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            String tail = "";
            while((line = br.readLine()) != null){
                String [] strs = line.split("？|。|\\?|\\.|,|：|:"); // 设置分隔符，可扩展
                List<String> doc = new ArrayList<>();
                for(String str: strs){
                    doc.add(str);
                }
                sentences.add(tail + doc.get(0));
                for(int i=1 ; i<doc.size()-1 ; i++) sentences.add(doc.get(i));
                if(doc.size() > 1) tail = doc.get(doc.size()-1);
                else tail = "";
            }
            if(tail.length() != 0){
                sentences.add(tail);
            }
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return sentences;
    }

    /*
    将词性不重要的词去除,只保留名词、动词、形容词、副词,以及删除停词表中的词语
    hanlp词性标注表示可参考 http://www.hankcs.com/nlp/part-of-speech-tagging.html
    */
    public static List<Term> removeInvalidWords(List<Term> sentence){
        List<Term> validSentence = new ArrayList<>();
        for(Term word : sentence){
            if(stopWords.contains(word.word)) continue;
            Nature nature = word.nature; // 词性
            if(nature.firstChar()  == 'n' || nature.firstChar() == 'v'
                    || nature.firstChar() == 'a' || nature.firstChar() == 'd'){
                validSentence.add(word);
            }
        }
        return validSentence;
    }
    /*
    每一句话对应一个List<Term> ，最后返回多句话的termList
    每句话都会将无效的词语除去
    */
    public static List<List<Term>> fileParticiple(String path){
        List<List<Term>> sentenceTerms = new ArrayList<>();
        List<String> sentences = sentenceParticiple(path);
        Segment segment= HanLP.newSegment().enableNameRecognize(true);
        for(String sentence : sentences){
            List<Term> termList = segment.seg(sentence);
            sentenceTerms.add(removeInvalidWords(termList));
        }
        return sentenceTerms;
    }



    public static void main(String [] args){

        List<Word> ret = TextRank.topKWordsFromFile("a", 20, 5, 0.85);
        for(int i=0 ; i<ret.size() ; i++){
            System.out.println(ret.get(i).getWord() + "->" + ret.get(i).getWeight());
        }
    }
}
