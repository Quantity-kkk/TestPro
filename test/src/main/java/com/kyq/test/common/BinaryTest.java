package com.kyq.test.common;

/**
 * Description:
\\\". All rights reserved.

 *
 * @version 1.0
 * @author: kyq1024
 * @timestamp: 2018-07-13 10:47
 */
public class BinaryTest {

    public static void main(String[] args){
        int number = 10;
        printInfo(number);
        number = number<<1;
        printInfo(number);
        number = number>>1;
        printInfo(number);
        number = -3>>>1;
        System.out.println("number:"+-3+" ,binary:"+Integer.toBinaryString(-3));
        int num = Integer.parseInt(Integer.toBinaryString(-3),2);
        System.out.println("number:"+num+" ,binary:"+Integer.toBinaryString(num));
        printInfo(number);
    }

    private static void printInfo(int number){
        System.out.println("number:"+number+" ,binary:"+Integer.toBinaryString(number));
    }
}
