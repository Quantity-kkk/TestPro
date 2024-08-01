package com.kyq.test.vm.oom;

/**
 * Description: -Xss128k 缩小该参数，stack length变小。
 *虚拟机栈和本地方法栈OOM测试，加大的深度
 *
 * @version 1.0
 * @author: kyq1024
 * @timestamp: 2018-01-16 17:12
 */
public class JavaVMStackSOF {
    private int stackLength = 1;
    public void stackLeak(){
        stackLength ++;
        stackLeak();
    }

    public static void main(String args[]){
        JavaVMStackSOF oom = new JavaVMStackSOF();
        try {
            oom.stackLeak();
        }catch (Throwable e){
            System.out.println("stack length: "+oom.stackLength);
            throw  e;
        }
    }
}
