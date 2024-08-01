package com.kyq.test.vm.oom;

/**
 * Description: -Xss2M
 * 创建线程导致内存溢出。
 *
 * @version 1.0
 * @author: kyq1024
 * @timestamp: 2018-01-16 17:31
 */
public class JavaVMStackOOM {
    private void dontStop(){
        while (true){

        }
    }
    public void stackLeakByThread(){
        while (true){
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    dontStop();
                }
            });
            thread.start();
        }
    }
    public static void main(String args[]){
        JavaVMStackOOM oom = new JavaVMStackOOM();
        oom.stackLeakByThread();
    }
}
