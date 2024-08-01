package com.kyq.test.objectcreate;

import sun.misc.Unsafe;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * Description: 该例子用于论证几种创建Java对象的方法。
 * Copyright: © 2018

 *
 * @author kyq1024
 * @version 1.0
 * @timestamp 2018/9/12
 */
public class Test implements Cloneable, Serializable {

    public Test(){
        System.out.println("Constructor invoked...");
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public static void main(String args[]){
        //第一种方法：直接使用new关键字
        Test a = new Test();
        System.out.println("Object a hashcode:"+a.hashCode());
        System.out.println("===============");
        //第二种：使用clone方法，不会调用构造方法
        try {
            Test b = (Test) a.clone();
            System.out.println("Object b hashcode:"+b.hashCode());
            System.out.println("===============");
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        //第三种方法：使用Class.forName().newInstance
        try {
            Test c = (Test) Class.forName("com.kyq.test.objectcreate.Test").newInstance();
            System.out.println("Object c hashcode:"+c.hashCode());
            System.out.println("===============");
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //第四种方法：使用Constructor类
        try {
            Test d = Test.class.getConstructor().newInstance();
            System.out.println("Object d hashcode:"+d.hashCode());
            System.out.println("===============");
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        //第五种方法：序列化
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("e.obj"))) {
            objectOutputStream.writeObject(a);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("e.obj"))){
            Test e = (Test) objectInputStream.readObject();
            System.out.println("Object e hashcode:"+e.hashCode());
            System.out.println("===============");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //第六种方法：使用Unsafe方法
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            Unsafe unsafe = (Unsafe) theUnsafe.get(null);
            Test f = (Test) unsafe.allocateInstance(Test.class);
            System.out.println("Object f hashcode:"+f.hashCode());
            System.out.println("===============");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

}
