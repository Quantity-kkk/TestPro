package com.kyq.test.vm.classinfo.runtimeclassinfo;

/**
 * Description:

 *
 * @version 1.0
 * @author: kyq1024
 * @timestamp: 2018-02-07 16:41
 */
public class DynamicDispatch {
    static abstract class Human{
        protected abstract void sayHello();
    }
    static class Man extends Human{
        @Override
        protected void sayHello(){
            System.out.println("man say hello");
        }}
    static class Woman extends Human{
        @Override
        protected void sayHello(){
            System.out.println("woman say hello");
        }}
    public static void main(String[]args){
        Human man=new Man();
        Human woman=new Woman();

        man.sayHello();
        woman.sayHello();

        man=new Woman();
        man.sayHello();
    }
}
