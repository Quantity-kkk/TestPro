package com.kyq.test.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Description: 探索三目运算符的自动拆箱装箱
\\\". All rights reserved.

 *
 * @version 1.0
 * @author: kyq1024
 * @timestamp: 2018-01-18 11:03
 */
public class UnBoxed {
    public  static void main(String argsp[]){
        Map<String,Boolean>  map = new HashMap<>();
//        boolean a = map!=null ? map.get("boolVal") : false;
//        System.out.println(a);
        Boolean b = map!=null ? map.get("boolVal") : false;


        System.out.println(b);
    }
}
