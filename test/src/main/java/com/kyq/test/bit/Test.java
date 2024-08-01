package com.kyq.test.bit;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Description:
 * Copyright: © 2018

 *
 * @author kyq1024
 * @version 1.0
 * @timestamp 2018/9/12
 */
public class Test {

//    private static final Unsafe unsafe = Unsafe.getUnsafe();

    public static void main(String args[]){
        int a = 2;//0010;
        System.out.println(a);
        System.out.println(Integer.toBinaryString(a));
        System.out.println(~a);
        System.out.println(Integer.toBinaryString(~a));
        System.out.println(Integer.toBinaryString(~a));
        System.out.println("=====================================");
        /*
        ------------------------------------------------
        移位操作：
        <<      :     左移运算符，num << 1,相当于num乘以2
        >>      :     右移运算符，num >> 1,相当于num除以2，对于负数，有符号位移，空位以1补齐
        >>>     :     无符号右移，忽略符号位，空位都以0补齐
        ------------------------------------------------
        与（&）、非（~）、或（|）、异或（^）
        &       ：    两个操作数中位都为1，结果才为1，否则为0
        |       ：    两个操作数中位只要有一个为1，结果为1，否则为0
        ~       ：    位为0，则为1，位为1，则为0
        ^       ：    位相同为0，不同为1；
        -------------------------------------------------
        private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0))
        private static int runStateOf(int c)     { return c & ~CAPACITY; }
        private static int workerCountOf(int c)  { return c & CAPACITY; }
        * */
        System.out.println(Integer.toBinaryString(-1));//1111 1111 1111 1111 1111 1111 1111 1111
        System.out.println(Integer.toBinaryString(-1 << 29));//用高三位表示状态RUNNING
        System.out.println(Integer.toBinaryString(0 << 29));
        System.out.println(Integer.toBinaryString(1 << 29));
        System.out.println(Integer.toBinaryString(2 << 29));
        System.out.println(Integer.toBinaryString( 3 << 29));
        System.out.println("=====================================");
        System.out.println(Integer.toBinaryString( (-1 << 29) | 0));
        System.out.println(Integer.toBinaryString( (-1 << 29) | 5));//11100000000000000000000000000101
        System.out.println("=====================================");
        System.out.println(Integer.toBinaryString( (1 << 29) - 1));//CAPACITY:00011111111111111111111111111111
        System.out.println("=====================================");
        System.out.println(Integer.toBinaryString( ((-1 << 29) | 5) & ~((1 << 29) - 1)));//CAPACITY:00011111111111111111111111111111

        //11100000000000000000000000000000
        //00000000000000000000000000000000
        //00100000000000000000000000000000
        //01000000000000000000000000000000
        //01100000000000000000000000000000
        try {
            Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
            unsafeField.setAccessible(true);
            Unsafe unsafe = (Unsafe) unsafeField.get(null);


        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }
}
