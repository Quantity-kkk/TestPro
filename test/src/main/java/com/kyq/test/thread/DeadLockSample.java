package com.kyq.test.thread;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author kyq1024
 * @version 1.0
 * @timestamp 2018/8/31
 */
public class DeadLockSample extends Thread {
    private String first;
    private String second;

    public DeadLockSample(String name,String first,String second){
        super(name);
        this.first = first;
        this.second = second;
    }
    public void run(){
        synchronized (first){
            System.out.println(this.getName()+" obtained:" + first);

            try{
                Thread.sleep(1000L);
                synchronized (second){
                    System.out.println(this.getName()+" obtained: "+second);
                }
            }catch (InterruptedException e){

            }
        }
    }

    public static void main(String args[]) throws InterruptedException {
//        simpliedead();
        deadMain();
    }

    public static void simpliedead() throws InterruptedException {
        String lockA = "lockA";
        String lockB = "lockB";
        DeadLockSample t1 = new DeadLockSample("Thread1", lockA, lockB);
        DeadLockSample t2 = new DeadLockSample("Thread2", lockB, lockA);

        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }

    public static void deadMain() throws InterruptedException {
        ThreadMXBean mbean = ManagementFactory.getThreadMXBean();
        Runnable dlCheck = ()->{
            long[] threadIds = mbean.findDeadlockedThreads();
            if (threadIds != null) {
                ThreadInfo[] threadInfos = mbean.getThreadInfo(threadIds);
                System.out.println("Detected deadlock threads:");
                for (ThreadInfo threadInfo : threadInfos) {
                    System.out.println(threadInfo.getThreadName());
                }
            }
        };

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        // 稍等 5 秒，然后每 10 秒进行一次死锁扫描
        scheduler.scheduleAtFixedRate(dlCheck, 5L, 10L, TimeUnit.SECONDS);
        // 死锁样例代码…
        simpliedead();
    }
}
