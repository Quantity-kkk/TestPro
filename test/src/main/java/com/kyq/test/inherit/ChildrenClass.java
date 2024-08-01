package com.kyq.test.inherit;

/**
 * Description:
\\\". All rights reserved.

 *
 * @version 1.0
 * @author: kyq1024
 * @timestamp: 2018-06-11 16:10
 */
public class ChildrenClass extends FatherClass{
    static {
        conf.put("key1","keynew");
    }
    public static void main(String args[]){
//        Collections.sort();
        System.out.println(conf.get("key1"));
        new ChildrenClass().test();
        System.out.println(conf.get("key1"));
    }
    public void test(){
        FatherClass f = new FatherClass(){{
            conf.put("key1","keyn");
        }};
        System.out.println(conf.get("key1"));
    }
}
