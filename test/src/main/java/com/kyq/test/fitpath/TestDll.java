package com.kyq.test.fitpath;


import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

import java.io.IOException;
import java.util.List;
/**
 * Description:
 * Copyright: © 2019

 *
 * @author kyq
 * @version 1.0
 * @Date 2019/12/22 11:58
 */


/**
 * Description:
 * Copyright: © 2019

 *
 * @author magic_z
 * @date 2019/7/3/003 10:21
 */
public class TestDll {

    public static void main(String[] args) throws IOException {

        //Memory 会自动施法内存
        DllInstancrManager.doPathFitInstance("D:\\windows_x64\\CSNT.Core.CalculatePrice.dll");

        DllInstancrManager.getFitPathInstance().Init("D:\\windows_x64\\params", "D:\\windows_x64\\params");

        System.out.println("初始化动态库完成...");

        ParamProcess process = new ParamProcess();
        List<String> strings = process.readFileByLine();
        System.out.println("初始化参数完成...");

        for (String params : strings){
            Pointer pointer = new Memory(1024 * 1024);
            boolean b = DllInstancrManager.getFitPathInstance().GetThePath(params, pointer);
            String result = pointer.getString(0);
            String[] arr = result.split("#");
            System.out.println(b);
            System.out.println(arr);
            long peer = Pointer.nativeValue(pointer);
            //手动释放内存
            //避免Memory对象被GC时重复执行Nativ.free()方法
            Native.free(peer);
            Pointer.nativeValue(pointer, 0);
        }
        System.out.println("所有参数调用完成...");
    }

}