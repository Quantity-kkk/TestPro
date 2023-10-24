package com.kyq.test.schedule;

/**
 * Description： com.kyq.test.schedule
 * CopyRight:  © 2015 CSTC. All rights reserved.
 * Company: cstc
 *
 * @author kyq1024
 * @version 1.0
 * @timestamp 2023-09-05 14:54
 */
public class TestObject {
    private String f;

    public TestObject(){
        this.f = "test";
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("【Object】我被回收了," + this.hashCode());
        super.finalize();
    }

    @Override
    public String toString() {
        return "TestObject{" +
                "f='" + hashCode() + '\'' +
                '}';
    }
}
