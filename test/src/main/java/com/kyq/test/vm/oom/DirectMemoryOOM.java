package com.kyq.test.vm.oom;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Description: -Xmx20M -XX:MaxDirectMemorySize=10M
 * 使用unsafe分配本机内存
 *
 * @version 1.0
 * @author: kyq1024
 * @timestamp: 2018-01-16 20:59
 */
public class DirectMemoryOOM {
    private static final int _1MB = 1024*1024;
    public static void main(String args[]) throws Exception {
        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafeField.get(null);
        while (true){
            unsafe.allocateMemory(_1MB);
        }
    }
}
