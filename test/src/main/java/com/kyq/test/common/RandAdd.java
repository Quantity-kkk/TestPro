package com.kyq.test.common;

import java.util.Random;

/**
 * Description:
\\\". All rights reserved.

 *
 * @version 1.0
 * @author: kyq1024
 * @timestamp: 2018-04-11 17:08
 */
public class RandAdd {
    public static void main(String args[]){
        add();
    }
    public static boolean check(int[] arr,int target){
        for(int i =0;i<arr.length;i++){
            if (arr[i]==target) return false;
        }
        return true;
    }

    public static void add(){
        Random r = new Random();
        int[] arr = new int[100];
        for (int i=0;i<arr.length;i++){
            int target = r.nextInt(100)+1;
            if (check(arr,target))
                arr[i]=target;
            else i--;
        }
        for (int i=0;i<arr.length;i++){
            if((i+1)%10==0)
                System.out.println(arr[i]);
            else
                System.out.print(arr[i]+",");
        }
    }
}
