package com.kyq.test.schedule;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Description： com.kyq.test.schedule
 * CopyRight:  © 2015 CSTC. All rights reserved.
 * Company: cstc
 *
 * @author kyq1024
 * @version 1.0
 * @timestamp 2023-08-30 11:16
 */
public class TestSchedule {
    /*测试步骤：
    1.启动一个schedule任务A；
    2.任务A中创建一个runner（runner中包含有thread）；
    3.在任务A中start、stop这个runner；
    4.任务A调用垃圾回收；看runner是否被回收；
    */

    public static void main(String[] args) {
        test();
    }

    public static void test(){
        ScheduledThreadPoolExecutor schedule = new ScheduledThreadPoolExecutor(6);
        //1.创建任务A；
        Runnable taskA = null;
        try {
            taskA = (Runnable) Class.forName("com.kyq.test.schedule.TestTask").newInstance();
        } catch (Exception e) {
            System.out.println(String.format("【%s】:创建taskA异常", "TestSchedule"));
        }

        schedule.scheduleAtFixedRate(taskA, 1, 1, TimeUnit.SECONDS);
    }
}
