package com.kyq.test.vm.classinfo;

/**
 * Description: 用于演示类的初始化过程，对于static字段，只有直接定义这个字段的类会被初始化。
 * 因此通过子类引用父类定义的静态字段，只会触发父类的初始化。
 *
 * @version 1.0
 * @author: kyq1024
 * @timestamp: 2018-02-02 13:49
 */

public class NotInitialization {
    public static void main(String args[]){
        /*用于演示类的初始化过程，对于static字段，只有直接定义这个字段的类会被初始化。
        * 因此通过子类引用父类定义的静态字段，只会触发父类的初始化。*/
//        System.out.println(SubClass.value);
        /*用于演示通过数组定义来引用类，不会触发此类的初始化*/
        SuperClass[] sc = new SuperClass[10];
    }
}

