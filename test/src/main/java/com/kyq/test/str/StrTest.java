package com.kyq.test.str;

/**
 * Description： com.kyq.test.str
 * CopyRight:  © 2015 CSTC. All rights reserved.
 * Company: cstc
 *
 * @author kyq1024
 * @version 1.0
 * @timestamp 2023-07-14 14:53
 */
public class StrTest {
    public static void main(String[] args) {
        testIndexOf();
    }

    public static void testIndexOf(){
        String s = "592A0C_0";
        int i = s.indexOf("_");
        String sb = s.substring(i+1);
    }
}
