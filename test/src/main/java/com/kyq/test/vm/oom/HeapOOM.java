package com.kyq.test.vm.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 * 限制java堆的大小为20M。且不可扩展（将堆的最大Xmx和最小值Xms参数设置为一样即可避免字段扩展）。
 * HeapDumpOnOutOfMemoryError可以在虚拟机出现内存溢出时dump出当前的内存堆转储快照。
 *
 * @version 1.0
 * @author: kyq1024
 * @timestamp: 2018-01-16 16:30
 */
public class HeapOOM {
    static class OOMOBject{

    }
    public static void main(String args[]){
        List<OOMOBject> list = new ArrayList<>();
        while (true){
            list.add(new OOMOBject());
        }
    }
}
