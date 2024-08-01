package com.kyq.test.vm.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: -XX:PermSize=10M -XX:MaxPermSize=10M 这两个参数在jdk1.8已经被移除。
 * 以上两个参数在jdk1.6中有效
 * -XX:MaxMetaspaceSize=2m jdk1.8有效。元空间
 * jdk1.7,1.8之后，字符串常量池已经被移到堆中实现。
 *
 * @version 1.0
 * @author: kyq1024
 * @timestamp: 2018-01-16 19:41
 */
public class RuntimeConstPoolOOM {
    public static void main(String args[]){
        List<String> list = new ArrayList<>();
        int i = 0;
        while (true){
            list.add(String.valueOf(i++).intern());
        }

    }
}
