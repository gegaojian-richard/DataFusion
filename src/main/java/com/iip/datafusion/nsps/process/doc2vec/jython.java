package com.iip.datafusion.nsps.process.doc2vec;

import org.python.core.PyFunction;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

import java.util.Map;


/**
 * @Author YLX
 * @Date 2018/2/1
 */
public class jython {

    public static Map<String, String> getVec(String path){
        System.out.println("hello");
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.execfile("src/main/java/com/iip/datafusion/nsps/process/doc2vec/test.py");
        PyFunction pyFunction = interpreter.get("hello", PyFunction.class);
        PyObject pyObject = pyFunction.__call__(new PyString(path));
        System.out.println("process: " + pyObject);
        System.out.println("output: " + ((Map<String, String>)pyObject).get("f3"));
        return (Map<String, String>)pyObject;
    }

}
