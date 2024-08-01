package com.kyq.test.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Description: 该类测试的是反射调用的inflation机制，前15次调用使用native方式，后面使用java方式调用。
 *
 * @version 1.0
 * @author: kyq1024
 * @timestamp: 2018-06-21 16:12
 */
public class TestMethodInvoke {
    public static void main(String args[]) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class<?> clz = Class.forName("com.kyq.test.reflect.Base");
        Object o = clz.newInstance();
        Method m = clz.getMethod("invoke",String.class);
        for (int i=0;i<60;i++){
            m.invoke(o,i+"");
        }

    }
}
