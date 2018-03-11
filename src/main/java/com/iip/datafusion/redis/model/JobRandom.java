package com.iip.datafusion.redis.model;

import java.util.Random;

/**
 * @author zengc
 * @date 2018/3/10 17:04
 */
public class JobRandom {

    public static String getRandomStr(){

        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        int length = 8;
        int randomNum;
        char randomChar;
        Random random = new Random();
        // StringBuffer类型的可以append增加字符
        StringBuffer str = new StringBuffer();

        for (int i = 0; i < length; i++) {
            // 可生成[0,n)之间的整数，获得随机位置
            randomNum = random.nextInt(base.length());
            // 获得随机位置对应的字符
            randomChar = base.charAt(randomNum);
            // 组成一个随机字符串
            str.append(randomChar);
        }
        return str.toString();

    }

    public static int getRandomInt(){
        return  (int)Math.random() * 2147483647;
    }
}
