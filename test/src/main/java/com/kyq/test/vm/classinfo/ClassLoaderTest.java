package com.kyq.test.vm.classinfo;

import java.io.InputStream;

/**
 * Description:
 *
 * @version 1.0
 * @author: kyq1024
 * @timestamp: 2018-02-05 15:14
 */
public class ClassLoaderTest {
    public static void main(String args[]) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        ClassLoader myloader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                try {
                    String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
                    InputStream is = getClass().getResourceAsStream(fileName);
                    if (is == null) {
                        return super.loadClass(name);
                    }
//                    return super.loadClass(name);//该句为true
                    byte[] b = new byte[is.available()];
                    is.read(b);
                    return defineClass(name, b, 0, b.length);//真正完成类加载动作的方法
                } catch (Exception e) {
                    throw new ClassNotFoundException(name);
                }
            }
        };

        Object obj = myloader.loadClass("com.kyq.test.vm.classinfo.CLInitTest").newInstance();
        System.out.println(obj.getClass());
        System.out.println(obj instanceof CLInitTest);
//        Object obj = myloader.loadClass("com.kyq.test.vm.classinfo.runtimeclassinfo.Overload").newInstance();
//        System.out.println(obj instanceof com.kyq.test.vm.classinfo.runtimeclassinfo.Overload);

    }
}
