package com.kyq.test.vm.oom;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Description: -XX:PermSize=10M -XX:MaxPermSize=10M  这两个参数jdk1.8已经失效
 * -XX:MetaspaceSize=5M -XX:MaxMetaspaceSize=7M
 * 使用运行时创建类的方式将方法区填满
 *
 * @version 1.0
 * @author: kyq1024
 * @timestamp: 2018-01-16 20:10
 */
public class JavaMethodAreaOOM {
    public static void main(String args[]){
        while (true){
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(OOMOBject.class);
            enhancer.setUseCache(false);
            enhancer.setCallback(new MethodInterceptor() {
                @Override
                public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                    return methodProxy.invokeSuper(o,objects);
                }
            });
            enhancer.create();
        }
    }

    static class OOMOBject{}
}
