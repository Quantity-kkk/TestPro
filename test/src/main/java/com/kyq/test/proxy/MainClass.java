package com.kyq.test.proxy;

import java.lang.reflect.Proxy;

/**
 * Description:
 *
 * @version 1.0
 * @author: kyq1024
 * @timestamp: 2018-01-12 14:10
 */
public class MainClass {
    public static void main(String args[]){
        dynamicProxyUseCase();

        staticProxyUseCase();

        cglibProxyUseCase();
    }

    private static void cglibProxyUseCase(){
        HelloIntf helloIntf = new CGLibProxy().getProxy(HelloImpl.class);
        helloIntf.sayHello("CGLib");

        //success...
        NoneIntfService noneIntfService = new CGLibProxy().getProxy(NoneIntfService.class);
        noneIntfService.sayHello("JiangBo");
    }

    private static void dynamicProxyUseCase(){
        HelloIntf helloIntf = new HelloImpl();
        DynamicProxy dynamicProxy = new DynamicProxy(helloIntf);
        HelloIntf helloIntfProxy = (HelloIntf) Proxy.newProxyInstance(
                helloIntf.getClass().getClassLoader(),
                helloIntf.getClass().getInterfaces(),
                dynamicProxy);

        //wrong:class type cannot cast...
        /*HelloIntf helloIntfProxy2 = (HelloIntf) Proxy.newProxyInstance(
                HelloIntf.class.getClassLoader(),
                HelloIntf.class.getInterfaces(),
                dynamicProxy);*/
        //succes
//        HelloIntf helloIntfProxy3 = (HelloIntf) Proxy.newProxyInstance(
//                HelloImpl.class.getClassLoader(),
//                HelloImpl.class.getInterfaces(),
//                dynamicProxy);


        helloIntfProxy.sayHello("Dynamic");

        HelloIntf helloIntfProxy1 = new DynamicProxy(new HelloImpl()).getProxy();
        helloIntfProxy1.sayHello("Dynamic1");

        //fail...
//        NoneIntfService noneIntfService = new DynamicProxy(new NoneIntfService()).getProxy();
//        noneIntfService.sayHello("JiangBo");
    }

    private static void staticProxyUseCase(){
        HelloIntf helloIntf = new StaticProxy();
        helloIntf.sayHello("Static");
    }
}
