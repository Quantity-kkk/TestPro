package com.kyq.test.proxy;

/**
 * Description:
 *
 * @version 1.0
 * @author: kyq1024
 * @timestamp: 2018-01-12 13:56
 */
public class HelloImpl implements HelloIntf {
    @Override
    public void sayHello(String name) {
        System.out.printf("Hello,%s. \n",name);
    }
}
