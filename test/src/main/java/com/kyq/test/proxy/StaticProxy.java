package com.kyq.test.proxy;

/**
 * Description:
 *
 * @version 1.0
 * @author: kyq1024
 * @timestamp: 2018-01-12 13:59
 */
public class StaticProxy implements HelloIntf{
    private HelloIntf helloIntf;

    public StaticProxy(){
        helloIntf = new HelloImpl();
    }
    @Override
    public void sayHello(String name) {
        //before
        before();
        helloIntf.sayHello(name);
        //after
        after();
    }
    private void before(){
        System.out.printf("receive sayhello command...\n");
    }

    private void after(){
        System.out.printf("sayhello command ended...\n");
    }
}
