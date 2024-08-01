package com.kyq.test.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Description:
 *
 * @version 1.0
 * @author: kyq1024
 * @timestamp: 2018-01-12 14:31
 */
public class CGLibProxy implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        before();
        Object ret = methodProxy.invokeSuper(o,args);
        after();
        return ret;
    }

    public <T> T getProxy(Class<T> cls){
        return (T) Enhancer.create(cls,this);
    }

    private void before(){
        System.out.printf("receive sayhello command...\n");
    }

    private void after(){
        System.out.printf("sayhello command ended...\n");
    }
}
