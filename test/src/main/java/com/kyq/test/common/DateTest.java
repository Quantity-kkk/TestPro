package com.kyq.test.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Description:
\\\". All rights reserved.

 *
 * @version 1.0
 * @author: kyq1024
 * @timestamp: 2017-11-03 15:04
 */
public class DateTest {
    public static int i = 1;
//    static {
//        System.out.println(i);
//    }
    public static void main(String args[]){
        dateTest();
    }

    public static void dateTest(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        for(int i = 9 ; i > 0 ; i-- ){
            calendar.add(Calendar.DATE, -1);
            System.out.println(calendar.getTime());
        }
    }

    public static void testGetMinuts(){
        Date time = getDate("2018-04-25 09:28:09");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        //分钟
        int minute = calendar.get(Calendar.MINUTE);
        int divide = minute/5;
        System.out.println(divide*5);
    }

    public static Date getDate(String dateStr){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void timeZoneChange(){
        //PDT时间
        String str = "2018-04-25 09:28:09";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            format.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date temp = format.parse(str);
            System.out.println(format.format(temp));
            System.out.println(temp);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public static Date parseDate(String strDate, String datePattern) {
        Date retDate = null;
        if (strDate != null) {
            if (strDate.contains("T")) {
                strDate = strDate.replace("T", " ");
            }
            if (strDate.length() <= 10) {
                strDate = strDate + " 00:00:00";
            }
            SimpleDateFormat sf = new SimpleDateFormat(datePattern);
            try {
                retDate = sf.parse(strDate);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return retDate;
    }
}
