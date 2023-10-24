package com.kyq.test.schedule;

/**
 * Description： com.kyq.test.schedule
 * CopyRight:  © 2015 CSTC. All rights reserved.
 * Company: cstc
 *
 * @author kyq1024
 * @version 1.0
 * @timestamp 2023-08-30 14:26
 */
public class TestTask implements Runnable{
    private TestService service = new TestService();

    @Override
    public void run() {
        service.doRun();

        //主动触发一下垃圾回收
        System.gc();
    }
}
