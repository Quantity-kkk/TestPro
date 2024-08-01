package com.kyq.test.schedule;

import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Description： com.kyq.test.schedule
 *
 * @author kyq1024
 * @version 1.0
 * @timestamp 2023-08-30 14:21
 */
public class TestService {
    private TestRunner[] testRunners;
    private static final int uploadThreadNum = 120;
    private final LinkedBlockingQueue originQueue = new LinkedBlockingQueue();

    /**
     * 测试方法中创建了一个Runner，然后马上调用Runner的start、stop方法；start方法里面启动守护线程执行Runner的run方法；
     * 测试结果：测试发现有可能守护线程没有拿到cpu执行时间，runner的stop方法就已经执行了；
     * */
    public void doRun(){
        try {
            //1.假装做了一些事情
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        originQueue.clear();
        for(int i = 0; i < 1000; i++){
            try {
                originQueue.put(new TestObject());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //2.创建Runner开始做事情
        testRunners = new TestRunner[uploadThreadNum];
        for(int i = 0; i < uploadThreadNum; i++){
            //将自己塞给线程
            testRunners[i] = new TestRunner(UUID.randomUUID().toString(), this, originQueue);
        }

        //3.线程依次启动
        for (int i = 0; i < uploadThreadNum; i++) {
            testRunners[i].start();
        }

        //4.线程依次等待执行完成
        for (int i = 0; i < uploadThreadNum; i++) {
            testRunners[i].stop();
        }
    }

    public void doSomething() {
        try {
            //休眠1000ms，假装执行了任务；
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
