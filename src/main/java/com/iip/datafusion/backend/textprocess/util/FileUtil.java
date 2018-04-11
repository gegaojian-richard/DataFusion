package com.iip.datafusion.backend.textprocess.util;

/**
 * @Author Junnor.G
 * @Date 2018/1/31 下午9:34
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.util.Set;

public class FileUtil {
    public static void test(){
        List<File> files = getAllFilePath(new ArrayList<>() , "ganjun_testdata");
        for(File file : files){
            System.out.println(file.getPath());
        }
    }

    public static void main(String [] args){
        test();
    }

    public static Set<String> stopWords = new HashSet<>();
    public static String stopWordsPath = "src/main/java/com/iip/datafusion/backend/textprocess/resource/stopwords";
    // 获得某个路径下的所有文件，包括所有子文件下的文件，递归查找，使用方法就是getAllFilePath(new ArrayList<>() , dir_path)
    public static List<File> getAllFilePath(List<File> files , String dirPath){
        File file = new File(dirPath);  //File can represent as file or directory
        try{
            System.out.println("AbsoutePath: " + file.getAbsolutePath());
        }catch(Exception ex){
            System.out.println("AbsoutePath: error");
            ex.printStackTrace();
        }
        File[] fileList = file.listFiles();
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].isFile()) { // this is a file
                files.add(fileList[i]);
            }
            else{ // this is a directory
                files = getAllFilePath(files , fileList[i].getPath());
            }
        }

        return files;
    }

    // 获得某个路径下的所有文件，包括所有子文件下的文件，递归查找，使用方法就是getAllFilePath(new ArrayList<>() , dir_path)
    public static List<String> getAllFilePath(String dirPath){
        List<File> allFiles = getAllFilePath(new ArrayList<>() , dirPath);
        List<String> files = new ArrayList<>();
        for(File file : allFiles){
            files.add(file.getPath());
        }
        return files;
    }

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
    /*
    从默认路径的文件中加载停词表。
     */
    public static boolean loadStopWords(){
        return loadStopWords(stopWordsPath);
    }
    /*
    将一个文件中的所有数据都读取出来作为一个字符串
     */
    public static String readFileAsString(String filePath){
        try{
            String file = "";
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            while((line = br.readLine()) != null) {
                file += line;
            }
            return file;
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            return null;
        }
    }
}
