package com.iip.datafusion.backend.textprocess.util;

/**
 * @Author Junnor.G
 * @Date 2018/1/31 下午9:34
 */
import java.util.List;
import java.util.ArrayList;
import java.io.File;

public class FileUtil {
    // 获得某个路径下的所有文件，包括所有子文件下的文件，递归查找，使用方法就是getAllFilePath(new ArrayList<>() , dir_path)
    public static List<File> getAllFilePath(List<File> files , String dirPath){
        File file = new File(dirPath);  //File can represent as file or directory
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

    public static void test(){
        List<File> files = getAllFilePath(new ArrayList<>() , "ganjun_testdata");
        for(File file : files){
            System.out.println(file.getPath());
        }
    }

    public static void main(String [] args){
        test();
    }
}
