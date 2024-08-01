package com.kyq.test.vm.classinfo;

/**
 * Description: 演示静态语句块和静态变量在clinit方法中的顺序
 *
 * @version 1.0
 * @author: kyq1024
 * @timestamp: 2018-02-05 9:51
 */
public class CLInitTest {
    public static String str1;
    public static String str2;
    static {
        str1="aaa";
        str2="bbb";
        str3="ccc";
        str4="ddd";
//        str1=str4;//静态语句块中可以访问在它之前的变量，在它之后的变量它只能赋值，不能访问。
    }
    public static String str3;
    public static String str4;

}
