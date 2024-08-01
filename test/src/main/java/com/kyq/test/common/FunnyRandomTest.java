package com.kyq.test.common;

import java.util.Random;

/**
 * Description:
\\\". All rights reserved.

 *
 * @version 1.0
 * @author: kyq1024
 * @timestamp: 2018-01-23 14:41
 */
public class FunnyRandomTest {
    public static void main(String args[]){
        System.out.println(randomString(-229985452) + " " + randomString(-147909649));
    }
    public static String randomString(int i) {
        Random ran = new Random(i);
        StringBuilder sb = new StringBuilder();
        while (true) {
            int k = ran.nextInt(27);
            System.out.println("char:" + k + ",number:" +  k);
            if (k == 0)
                break;
            k += 96;
            sb.append((char) k);
        }

        return sb.toString();
    }
}
