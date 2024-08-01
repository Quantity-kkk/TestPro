package com.kyq.test.common;

import java.util.*;

/**
 * Description:
 * Copyright: © 2018

 *
 * @author kyq1024
 * @version 1.0
 * @timestamp 2018/9/29
 */
public class OptionalTest {

    public static void main(String args[]){
        testObjectArray();
    }

    public static void testObjectArray(){
        List<Object[]> list = new ArrayList<>();

        for(int i=0; i<5; i++){
            Object[] param = new Object[5];
            param[0]="1";
            param[1]="2";
            param[2]="3";
            param[3]="4";
            param[4]="5";
            list.add(param);
        }

        Object[][] params = list.toArray(new Object[][]{});
        System.out.println(params);
    }

    public static void testOptional(){
        Map map = new HashMap();

        map.put("list", new ArrayList<>());

        List a = (List) map.get("lista");

        List b = Optional.ofNullable(a).orElse(new ArrayList());
        System.out.println(a);
        System.out.println(b);
    }
}
