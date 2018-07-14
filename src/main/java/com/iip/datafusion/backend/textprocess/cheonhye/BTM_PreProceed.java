package com.iip.datafusion.backend.textprocess.cheonhye;

import com.iip.datafusion.backend.textprocess.util.FileUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BTM_PreProceed {

    public static List<String> toNormal(String dirc) throws IOException{


        List<String> filelist = FileUtil.getAllFilePath(dirc);
        BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(new File("./toNormal.txt")), "UTF-8"));

        for(String file : filelist){
            ArrayList<String> cutwords = TF_IDF.cutWords(file); //get cut words for one file
            for(String word: cutwords){
                bw.write(word);
                bw.write(" ");
            }
            bw.newLine();
        }
        bw.close();
        return filelist;
    }

}
