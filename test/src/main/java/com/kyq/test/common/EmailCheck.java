package com.kyq.test.common;

import java.util.regex.Pattern;

/**
 * Description:
\\\". All rights reserved.

 *
 * @version 1.0
 * @author: kyq1024
 * @timestamp: 2018-06-30 14:55
 */
public class EmailCheck {
    public static void main(String args[]){
        Pattern EMAIL_PATTERN = Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
        boolean ch1 = EMAIL_PATTERN
                .matcher("556445@qq,com")
                .matches();
        System.out.println(ch1);

    }
}
