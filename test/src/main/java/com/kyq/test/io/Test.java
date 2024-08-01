package com.kyq.test.io;

import com.google.common.collect.ImmutableMap;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Description:
 * Copyright: ? 2017

 *
 * @version 1.0
 * @author: kyq1024
 * @timestamp: 2018-08-02 14:48
 */
public class Test {
    public static void main(String args[]){
//        ByteBuffer encode = Charset.defaultCharset().encode("Hello World!");
//        final ByteBuffer encode1 = Charset.forName("UTF-8").encode("Hello World!");
        compare();
        SimpleDateFormat sf = new SimpleDateFormat("yyMMdd");
        System.out.println(sf.format(new Date()));

        ImmutableMap.of("exists",false);
    }

    public static void compare(){
        List a = new ArrayList<>();
        List b = new ArrayList<>();
        a.contains("");
        Set c = new HashSet();
        Iterator aIt = a.iterator();
        while (aIt.hasNext()){
            String key = (String) aIt.next();
            if((c.contains(""))){
                c.remove("");
                aIt.remove();
            }
        }

        Set d = new HashSet();

        new ArrayList<>(d);


    }
}
