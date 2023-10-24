package com.kyq.test.feerate;

import com.kyq.util.DateUtil;
import com.kyq.util.StringUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

/**
 * Description： com.kyq.test.feerate
 * CopyRight:  © 2015 CSTC. All rights reserved.
 * Company: cstc
 *
 * @author kyq1024
 * @version 1.0
 * @timestamp 2023-10-24 10:11
 */
public class ParamGenerator {
    /**
     * 伪造计费测试的请求数据-吉林版本
     * */

    public static void main(String[] args) {
        System.out.println(generator());
    }

    //吉林版本
    public static String generator(){
        String exStationHex = "0511";
        String enTollStationHex = "0542";
        String vehiclePlate = "吉A06M1V_0";
        String enTime = "2023-10-22T11:57:01";
        String exTime = "2023-10-22T15:08:01";
        Integer mediaType = 1;
        Integer vehicleType = 1;
        Integer vehicleClass = 0;
        String passId = "020000230101650022904120230517205509";
        String mediaNo = "02000023010165";

        String oriIntervalsGroup = "1C3AB6_0|1C3AB6_1|1C3AB6_1|1C3AB2_0|1C3AB2_1|1C3AB2_1|1C3AAE_0|1C3AAE_1|1C3AAE_1|1C3AAA_0|1C3AAA_1|1C3AAA_1|1C3AA6_0|1C3AA6_1|1C3AA2_0|1C3AA2_1|1C3AA2_1|1C3B0C_0|1C3B0C_1|1C3B0C_1|1C3AA0_0|1C3AA0_1|1C3AA0_1|1C3A9C_0|1C3A9A_0|1C3A9A_1|1C3A9A_1|1C36BE_0|1C36BE_1|1C36BE_1|1D36BD_1|1D36BD_1|1C32DE_1|1C32DE_0|1C32DA_0|1C32DA_1|1C32DA_1|1C32D6_0|1C32D6_1|1C32D6_1|1C32D2_0|1C32D2_1|1C32D2_1|1C32CE_0|1C32CE_1|1C32CA_0|1C32CA_1|1C32CA_1";
        StringBuilder sb = new StringBuilder();

        sb.append("{\"exStationHex\":\"").append(exStationHex).append("\",")
                .append("\"enTollStationHex\":\"").append(enTollStationHex).append("\",")
                .append("\"mediaType\":").append(mediaType).append(",")
                .append("\"mediaNo\":\"").append(mediaNo).append("\",")
                .append("\"vehiclePlate\":\"").append(vehiclePlate).append("\",")
                .append("\"vehicleType\":").append(vehicleType).append(",")
                .append("\"vehicleClass\":").append(vehicleClass).append(",")
                .append("\"enTime\":\"").append(enTime).append("\",")
                .append("\"exTime\":\"").append(exTime).append("\",")
                .append("\"isDiscount\":0,\"tradeinfos\":[")
        ;
        String[] split = oriIntervalsGroup.split("\\|");
        Date curTransTime = DateUtil.parseDate(enTime, DateUtil.format_yyyy_MM_ddTHHmmss);
        int curSerial = 0;
        HashSet gantrySet = new HashSet();
        if(split.length>0){
            for (String gxT : split) {
                String[] dx = gxT.trim().split("_");
                String gantryHex = dx[0];
                String type = dx[1];

                //每次加2分钟时间
                curTransTime = DateUtil.addTime(curTransTime, 1, Calendar.MINUTE);
                String curTransTimeStr = DateUtil.formatDate(curTransTime, DateUtil.format_yyyy_MM_ddTHHmmss);
                //门架交易流水
                if("0".equals(type)){
                    sb.append("{\"tT\":\"").append(curTransTimeStr).append("\",")
                            .append("\"gH\": \"").append(gantryHex).append("\",")
                            .append("\"t\": \"0\",")
                            .append("\"eRT\": \"").append(curTransTimeStr).append("\",")
                            .append("\"eST\": \"").append(enTime).append("\",")
                            .append("\"mT\": \"").append(mediaType).append("\",")
                            .append("\"pI\": \"").append(passId).append("\",")
                            .append("\"eSH\": \"").append(enTollStationHex).append("\",")
                    ;
                    boolean isDuplicateGx = gantrySet.add(gantryHex);
                    if(isDuplicateGx){
                        sb.append("\"oSa\": \"").append(curSerial++).append("\",");
                    }else {
                        sb.append("\"oSa\": \"").append(curSerial).append("\",");
                    }
                    sb.append("\"tId\": \"").append(StringUtil.getUUID()).append("\"},");
                }else {
                    //牌识流水
                    sb.append("{\"tT\":\"").append(curTransTimeStr).append("\",")
                            .append("\"gH\": \"").append(gantryHex).append("\",")
                            .append("\"t\": \"1\",")
                            .append("\"eRT\": \"").append(curTransTimeStr).append("\",");
                    sb.append("\"pId\": \"").append(StringUtil.getUUID()).append("\"},");
                }
            }
            //去掉最后一个逗号
            sb.deleteCharAt(sb.length()-1);
        }

        sb.append("]}");

        return sb.toString();
    }
}
