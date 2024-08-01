package com.kyq.test.feerate;

import com.kyq.util.DateUtil;
import com.kyq.util.StringUtil;
import org.apache.tika.Tika;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;

import javax.activation.MimetypesFileTypeMap;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

/**
 * Description： com.kyq.test.feerate
\\\"

 *
 * @author kyq1024
 * @version 1.0
 * @timestamp 2023-10-24 10:11
 */
public class ParamGenerator {
    /**
     * 伪造计费测试的请求数据-吉林版本
     * */

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        System.out.println(generatorCQ());
//        System.out.println("2023-10-30'T'11:07:13".compareTo(""));
//        System.out.println("2023-10-30'T'11:07:13".compareTo(null));
//        String fileName = "feerate-audit.jar";
//        System.out.println("feerate-audit.jar");
//        System.out.println(fileName.substring(fileName.lastIndexOf(".")+1));

//        String filePath = "D:\\work\\2023\\0731feerate_new\\1017-青海部署\\feerate-audit\\feerate-audit.zip";
//        System.out.println("MimetypesFileTypeMap:"+new MimetypesFileTypeMap().getContentType(filePath));
//        System.out.println("Apache Tika:"+new Tika().detect(filePath));
//        System.out.println("URLConnection:"+URLConnection.guessContentTypeFromName(filePath));
//        System.out.println("MediaTypeFactory:" + MediaTypeFactory.getMediaType(filePath).orElse(MediaType.APPLICATION_OCTET_STREAM));

    }

    //吉林版本
    public static String generator(){
        String enTollStationHex = "0454";
        String exStationHex = "044E";
        String vehiclePlate = "晋LBC001_0";
        String enTime = "2024-04-26T13:20:45";
        String exTime = "2024-04-27T22:54:24";
        Integer mediaType = 1;
        Integer vehicleType = 1;
        Integer vehicleClass = 0;
        String passId = "020000410101610022908820240303205320";
        String mediaNo = "02000023010165";

//        String oriIntervalsGroup = "1C3AB6_0|1C3AB6_1|1C3AB6_1|1C3AB2_0|1C3AB2_1|1C3AB2_1|1C3AAE_0|1C3AAE_1|1C3AAE_1|1C3AAA_0|1C3AAA_1|1C3AAA_1|1C3AA6_0|1C3AA6_1|1C3AA2_0|1C3AA2_1|1C3AA2_1|1C3B0C_0|1C3B0C_1|1C3B0C_1|1C3AA0_0|1C3AA0_1|1C3AA0_1|1C3A9C_0|1C3A9A_0|1C3A9A_1|1C3A9A_1|1C36BE_0|1C36BE_1|1C36BE_1|1D36BD_1|1D36BD_1|1C32DE_1|1C32DE_0|1C32DA_0|1C32DA_1|1C32DA_1|1C32D6_0|1C32D6_1|1C32D6_1|1C32D2_0|1C32D2_1|1C32D2_1|1C32CE_0|1C32CE_1|1C32CA_0|1C32CA_1|1C32CA_1";
        //ori_intervals_group
        String oriIntervalsGroup = "750707_1|750707_1|750704_1|750704_1|750703_1|750703_1|740703_1|740703_1|740704_1|740704_1|740707_1|740707_1|750707_1|750707_1|741101_1|741101_1";

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

    //重庆版本
    public static String generatorCQ(){
        String exStationHex = "6804";
        String enTollStationHex = "0320";
        String vehiclePlate = "渝G3F260_0";
        String enTime = "2024-05-27T16:02:46";
        String exTime = "2024-05-27T16:36:18";
        Integer mediaType = 2;
        Integer vehicleType = 0;
        Integer vehicleClass = 0;
        String passId = "020000110101610008463320240527160246";
        String mediaNo = "1101016100084633";

//        String oriIntervalsGroup = "1C3AB6_0|1C3AB6_1|1C3AB6_1|1C3AB2_0|1C3AB2_1|1C3AB2_1|1C3AAE_0|1C3AAE_1|1C3AAE_1|1C3AAA_0|1C3AAA_1|1C3AAA_1|1C3AA6_0|1C3AA6_1|1C3AA2_0|1C3AA2_1|1C3AA2_1|1C3B0C_0|1C3B0C_1|1C3B0C_1|1C3AA0_0|1C3AA0_1|1C3AA0_1|1C3A9C_0|1C3A9A_0|1C3A9A_1|1C3A9A_1|1C36BE_0|1C36BE_1|1C36BE_1|1D36BD_1|1D36BD_1|1C32DE_1|1C32DE_0|1C32DA_0|1C32DA_1|1C32DA_1|1C32D6_0|1C32D6_1|1C32D6_1|1C32D2_0|1C32D2_1|1C32D2_1|1C32CE_0|1C32CE_1|1C32CA_0|1C32CA_1|1C32CA_1";
//        String oriIntervalsGroup = "590309_0|594909_0|54909_0|54908_0|594908_0|594908_1|594908_1|594907_0|594907_0|5094907_1|594907_1|584401_0|584401_0|584401_1|584401_1|584402_0|584402_1|584403_0|584403_1|584404_0|584404_1|584405_0|584405_1";
        String oriIntervalsGroup = "590309_0_1|594909_0_2|594909_0_2|594908_0_3|594908_0_3|594908_1|594908_1|594907_0_4|594907_0_null|594907_1|594907_1|584401_0_5|584401_0_5|584401_1|584401_1|584402_0_6|584402_1|584403_0_7|584403_1|584404_0_8|584404_1|584405_0_9|584405_1";

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
                .append("\"isDiscount\":0,\"passGantryRedisInfo\":\"[")
        ;
        String[] split = oriIntervalsGroup.split("\\|");
        Date curTransTime = DateUtil.parseDate(enTime, DateUtil.format_yyyy_MM_ddTHHmmss);
        int curSerial = 0;
        HashSet<String> gantrySet = new HashSet<String>();
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
                    sb.append("{\\\"tT\\\":\\\"").append(curTransTimeStr).append("\\\",")
                            .append("\\\"gH\\\": \\\"").append(gantryHex).append("\\\",")
                            .append("\\\"t\\\": \\\"0\\\",")
                            .append("\\\"eRT\\\": \\\"").append(curTransTimeStr).append("\\\",")
                            .append("\\\"eST\\\": \\\"").append(enTime).append("\\\",")
                            .append("\\\"mT\\\": \\\"").append(mediaType).append("\\\",")
                            .append("\\\"pI\\\": \\\"").append(passId).append("\\\",")
                            .append("\\\"eSH\\\": \\\"").append(enTollStationHex).append("\\\",")
                    ;
                    boolean isDuplicateGx = gantrySet.add(gantryHex);
                    if(dx.length>2){
                        sb.append("\\\"oSa\\\": \\\"").append(dx[2]).append("\\\",");
                    }else {
                        if(isDuplicateGx){
                            sb.append("\\\"oSa\\\": \\\"").append(curSerial++).append("\\\",");
                        }else {
                            sb.append("\\\"oSa\\\": \\\"").append(curSerial).append("\\\",");
                        }
                    }

                    sb.append("\\\"tId\\\": \\\"").append(StringUtil.getUUID()).append("\\\"},");
                }else {
                    //牌识流水
                    sb.append("{\\\"tT\\\":\\\"").append(curTransTimeStr).append("\\\",")
                            .append("\\\"gH\\\": \\\"").append(gantryHex).append("\\\",")
                            .append("\\\"t\\\": \\\"1\\\",")
                            .append("\\\"eRT\\\": \\\"").append(curTransTimeStr).append("\\\",");
                    sb.append("\\\"pId\\\": \\\"").append(StringUtil.getUUID()).append("\\\"},");
                }
            }
            //去掉最后一个逗号
            sb.deleteCharAt(sb.length()-1);
        }

        sb.append("]\"}");

        return sb.toString();
    }

    public static String generatorForQH(){
        String enTollStationHex = "0454";
        String exStationHex = "044E";
        String vehiclePlate = "晋LBC001_0";
        String enTime = "2024-04-26T13:20:45";
        String exTime = "2024-04-27T22:54:24";
        Integer mediaType = 1;
        Integer vehicleType = 1;
        Integer vehicleClass = 0;
        String passId = "020000410101610022908820240303205320";
        String mediaNo = "02000023010165";

//        String oriIntervalsGroup = "1C3AB6_0|1C3AB6_1|1C3AB6_1|1C3AB2_0|1C3AB2_1|1C3AB2_1|1C3AAE_0|1C3AAE_1|1C3AAE_1|1C3AAA_0|1C3AAA_1|1C3AAA_1|1C3AA6_0|1C3AA6_1|1C3AA2_0|1C3AA2_1|1C3AA2_1|1C3B0C_0|1C3B0C_1|1C3B0C_1|1C3AA0_0|1C3AA0_1|1C3AA0_1|1C3A9C_0|1C3A9A_0|1C3A9A_1|1C3A9A_1|1C36BE_0|1C36BE_1|1C36BE_1|1D36BD_1|1D36BD_1|1C32DE_1|1C32DE_0|1C32DA_0|1C32DA_1|1C32DA_1|1C32D6_0|1C32D6_1|1C32D6_1|1C32D2_0|1C32D2_1|1C32D2_1|1C32CE_0|1C32CE_1|1C32CA_0|1C32CA_1|1C32CA_1";
        //ori_intervals_group
        String oriIntervalsGroup = "750707_1|750707_1|750704_1|750704_1|750703_1|750703_1|740703_1|740703_1|740704_1|740704_1|740707_1|740707_1|750707_1|750707_1|741101_1|741101_1";

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
                    sb.append("{\"transTime\":\"").append(curTransTimeStr).append("\",")
                            .append("\"gantryHex\": \"").append(gantryHex).append("\",")
                            .append("\"type\": \"0\",")
                            .append("\"enRedisTime\": \"").append(curTransTimeStr).append("\",")
                            .append("\"enTime\": \"").append(enTime).append("\",")
                            .append("\"mediaType\": \"").append(mediaType).append("\",")
                            .append("\"passId\": \"").append(passId).append("\",")
                            .append("\"enTollStationHex\": \"").append(enTollStationHex).append("\",")
                    ;
                    boolean isDuplicateGx = gantrySet.add(gantryHex);
                    if(isDuplicateGx){
                        sb.append("\"gantryPassSerial\": \"").append(curSerial++).append("\",");
                    }else {
                        sb.append("\"gantryPassSerial\": \"").append(curSerial).append("\",");
                    }
                    sb.append("\"tradeId\": \"").append(StringUtil.getUUID()).append("\"},");
                }else {
                    //牌识流水
                    sb.append("{\"transTime\":\"").append(curTransTimeStr).append("\",")
                            .append("\"gantryHex\": \"").append(gantryHex).append("\",")
                            .append("\"type\": \"1\",")
                            .append("\"enRedisTime\": \"").append(curTransTimeStr).append("\",");
                    sb.append("\"picId\": \"").append(StringUtil.getUUID()).append("\"},");
                }
            }
            //去掉最后一个逗号
            sb.deleteCharAt(sb.length()-1);
        }

        sb.append("]}");

        return sb.toString();
    }


    public String getFileType(String fileName) {
        if (fileName==null || "".equals(fileName.trim())) {
            return null;
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    protected String getContentType (String fileType) {
        if (fileType.equalsIgnoreCase(".bmp")) {
            return "image/bmp";
        }
        if (fileType.equalsIgnoreCase(".gif")) {
            return "image/gif";
        }
        if (fileType.equalsIgnoreCase(".jpeg") || fileType.equalsIgnoreCase(".jpg")
                || fileType.equalsIgnoreCase(".png")) {
            return "image/jpeg";
        }
        if (fileType.equalsIgnoreCase(".html")) {
            return "text/html";
        }
        if (fileType.equalsIgnoreCase(".txt")) {
            return "text/plain";
        }
        if (fileType.equalsIgnoreCase(".vsd")) {
            return "application/vnd.visio";
        }
        if (fileType.equalsIgnoreCase(".pptx") || fileType.equalsIgnoreCase(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (fileType.equalsIgnoreCase(".docx") || fileType.equalsIgnoreCase(".doc")) {
            return "application/msword";
        }
        if (fileType.equalsIgnoreCase(".xml")) {
            return "text/xml";
        }
        if (fileType.equalsIgnoreCase(".pdf")) {
            return "application/pdf";
        }
        return "application/octet-stream";
    }

}
