package com.kyq.test.common;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Description:
 * Copyright: © 2019

 *
 * @author kyq
 * @version 1.0
 * @Date 2019/8/10 21:13
 */
public class NumberTest {
    public static void main(String[] args){
//        BigDecimal a = new BigDecimal("0.1");
//        BigDecimal b = new BigDecimal("9.2");
//        doAdd(a, b);
//        System.out.println(a);

//        System.out.println(getPicServerUrl("10.17.31.4","6300212725D51E2E000489"));
    }

    private static void getFirstDay(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        //设置为1号,当前日期既为本月第一天
        c.set(Calendar.DAY_OF_MONTH,1);
        String first = format.format(c.getTime());
        System.out.println("===============first:"+first);
    }

    private static void doAdd(BigDecimal a, BigDecimal b){
        a = a.add(b);
    }
    // 6300212725D51E2E000489
    private static String getPicServerUrl(String serverIp, String listNo) {
        // FTP服务器IP和流水号均不为空，并且流水号长度大于22
        if (listNo.length() >= 22) {
            // 1.从流水号中拆分出流水时间和车道编号
            String dateStr = listNo.substring(9, 17);
            int totalSeconds = Integer.parseInt(dateStr, 16);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date tradeTime;
            try {
                tradeTime = sdf.parse("1970-01-01");
            } catch (ParseException e) {
                throw new RuntimeException("时间转换出错");
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(tradeTime);
            calendar.add(Calendar.SECOND, totalSeconds);
            String laneIdStr = listNo.substring(7, 9);
            String laneId = padRight(String.valueOf(Integer.parseInt(laneIdStr, 16)), 3, '0');
            // 2.拼接构造图片的url
            return String.format("http://%s:10000/%s/%s/%s/%s/%s.jpg",
                    serverIp,
                    String.valueOf(calendar.get(Calendar.YEAR)),
                    padRight(String.valueOf(calendar.get(Calendar.MONTH) + 1), 2, '0'),
                    padRight(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)), 2, '0'),
                    laneId,
                    listNo);
        }
        return null;
    }
    private static String padRight(String src, int len, char ch) {
        int diff = len - src.length();
        if (diff <= 0) {
            return src;
        }
        char[] charr = new char[len];
        System.arraycopy(src.toCharArray(), 0, charr, diff, src.length());
        for (int i = 0; i < diff; i++) {
            charr[i] = ch;
        }
        return new String(charr);
    }
}
