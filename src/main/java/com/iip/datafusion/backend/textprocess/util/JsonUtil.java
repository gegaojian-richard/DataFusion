package com.iip.datafusion.backend.textprocess.util;

/**
 * @Author Junnor.G
 * @Date 2018/2/2 下午8:04
 */

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The class JacksonUtil
 *
 * json字符与对像转换
 *
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
public final class JsonUtil {

    public static ObjectMapper objectMapper = new ObjectMapper();

    public static void writeEntity2Json(Object object) throws IOException {
        System.out.println(objectMapper.writeValueAsString(object));
    }


    public static void main(String [] args) throws Exception{
        List<A> As = new ArrayList<>();
        As.add(new A(1, "a"));
        As.add(new A(2, "b"));
        As.add(new A(3, "c"));

        writeEntity2Json(new A(1, "a"));
    }
}

class A{
    int a;
    String b;
    public A(int a , String b){
        this.a = a;
        this.b = b;
    }
    @Override
    public String toString() {
        return "UserBean [userId=" + a + ", userName=" + b
                + ", password=" + a + ", email=" + b + "]";
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }
}