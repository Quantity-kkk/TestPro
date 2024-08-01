package com.kyq.test.vm.classinfo.runtimeclassinfo;

/**
 * Description:

 *
 * @version 1.0
 * @author: kyq1024
 * @timestamp: 2018-02-07 15:41
 */
public class StaticDispatch {
    public String name;
    private static String sex;
    static abstract class Human{
    }
    static class Man extends Human{
    }
    static class Woman extends Human{
        void t(){
//            System.out.println(name);//error,静态内部类只能访问外部类的静态变量。
        }
    }
    public class Animal {
        void t(){
            System.out.println(name);//非静态内部类持有外部类的引用，可以使用name属性
        }
    }
    public void sayHello(Human guy){
        System.out.println("hello,guy!");
//        new Animal();//success,非静态内部类不能脱离外部类实体被创建

    }
    public void sayHello(Man guy){
        System.out.println("hello,gentleman!");
    }
    public void sayHello(Woman guy){
        System.out.println("hello,lady!");
    }
    public static void main(String[] args){
        Human man =  new Man();
        Human woman=new Woman();
//        Human animal = new Animal();//error,非静态内部类不能脱离外部类实体被创建。
//        new StaticDispatch().new Animal();//success，非静态内部类不能脱离外部类实体被创建。
        StaticDispatch sr=new StaticDispatch();
        sr.sayHello(man);
        sr.sayHello(woman);
    }
}
