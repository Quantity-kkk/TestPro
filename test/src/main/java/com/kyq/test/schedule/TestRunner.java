package com.kyq.test.schedule;

import com.jfinal.kit.Kv;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Description： com.kyq.test.schedule
 * CopyRight:  © 2015 CSTC. All rights reserved.
 * Company: cstc
 *
 * @author kyq1024
 * @version 1.0
 * @timestamp 2023-08-30 13:54
 */
public class TestRunner implements Runnable{

    private volatile boolean running = false;
    private Thread processThread = null;
    private final String token;
    private final TestService service;
    private final LinkedBlockingQueue originQueue;

    public TestRunner(String token, TestService testService, LinkedBlockingQueue originQueue){
        this.token = token;
        this.service = testService;
        this.originQueue = originQueue;
    }

    @Override
    public void run() {
        if(running){
            Object o;
            try {
                while ((o = originQueue.poll(100, TimeUnit.MILLISECONDS)) != null){
                    System.out.println(o);
                }
//                service.doSomething();
            }catch (Exception e){
                System.out.println(String.format("【TestRunner-%s】：系统异常，中断当前线程", token));
            }
        }else {
            System.out.println(String.format("【TestRunner-%s】：调度异常", token));
        }
    }

    public synchronized void start(){
        if (!running) {
            running = true;
            processThread = new Thread(this, "TestRunner" );
            processThread.setDaemon(true);
            processThread.start();
        }
    }

    public synchronized void stop() {
        if (running) {


            running = false;

            try {
                //等待守护线程结束
                processThread.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println(String.format("【TestRunner-%s】：我要被回收了。", token));
        super.finalize();
    }
}
