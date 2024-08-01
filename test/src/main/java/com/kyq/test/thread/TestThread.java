package com.kyq.test.thread;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Map;

import static junit.framework.Assert.assertEquals;

/**
 * Description:
 *
 * @author kyq1024
 * @version 1.0
 * @timestamp 2018/8/31
 */
public class TestThread {
    public static void main(String args[]){
        assertEquals(1,0);
    }
    public void showBlockThread(){
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        long[] deadlockedThreads = bean.findDeadlockedThreads();
        long[] monitorDeadlockedThreads = bean.findMonitorDeadlockedThreads();
        for (long deadlockedThreadId: deadlockedThreads){
            System.out.println("[deadlockedThreads]-"+deadlockedThreadId);
        }

        for (long monitorDeadlockedThreadId: monitorDeadlockedThreads){
            System.out.println("[monitorDeadlockedThreads]-"+monitorDeadlockedThreadId);
        }
    }

    public void findThread(){
        System.out.println("Hello world!");
        Map<Thread,StackTraceElement[]> maps = Thread.getAllStackTraces();

        for(Thread thread : maps.keySet()){
            System.out.println("[ThreadID-"+thread.getId()+"],[ThreadName:"+thread.getName()+"]");
        }
    }
}
