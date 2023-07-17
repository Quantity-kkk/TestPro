package com.kyq.test.list;

import java.util.ArrayList;
import java.util.List;

/**
 * Description： com.kyq.test.list
 * CopyRight:  © 2015 CSTC. All rights reserved.
 * Company: cstc
 *
 * @author kyq1024
 * @version 1.0
 * @timestamp 2023-07-06 15:02
 */
public class ListDelTest {

    public static void removeAfter(){
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10 ; i++) {
            list.add(7);
        }
        System.out.println(list.toString());
        for (int i = list.size()-2; i >=0 ; i--) {
            if (list.get(i+1).equals(list.get(i))) {
                list.remove(i+1);
            }
        }
        System.out.println(list.toString());
    }


    public static void removeCur(){
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10 ; i++) {
            list.add(7);
        }
        System.out.println(list.toString());
        for (int i = list.size()-1; i >=0 ; i--) {
            list.remove(i);
        }
        System.out.println(list.toString());
    }

    public static void main(String[] args) {
        removeCur();
    }
}
