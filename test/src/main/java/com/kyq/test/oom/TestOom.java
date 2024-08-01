package com.kyq.test.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * Description： com.kyq.test.oom
 *
 * @author kyq1024
 * @version 1.0
 * @timestamp 2023-08-31 17:02
 */
public class TestOom {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        while (true){
            list.add(new Integer(4568));
        }
    }
}
