package com.kyq.test.vm.classinfo;

/**
 * Description:演示异常表实现异常捕获。
 *
 * @version 1.0
 * @author: kyq1024
 * @timestamp: 2018-01-24 20:58
 */
public class ExceptionAttribute {
    public int inc(){
        int x;
        try {
            x=1;
            return x;
        }
        catch (Exception e){
            x=2;
            return x;
        }finally {
            x=3;
        }
    }
}
