package com.kyq.test.feerate;

import com.alibaba.fastjson.JSON;
import com.kyq.util.Maps;
import com.kyq.util.StringUtil;
import net.dongliu.requests.Requests;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.*;


// cqzxjf请求参数构造器
class Test {
    public static final String format_yyyy_MM_ddTHHmmss = "yyyy-MM-dd'T'HH:mm:ss";

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        //1.读取文件
        List<String> params = loadParams();
        //2.构造请求
        List<String> reqParams = generateParams(params);
        //3.调用接口
        List<String> ret = new ArrayList<>();
        for (String reqParam : reqParams) {
            String url = "http://localhost:8081/fee/feeSpecialCal2";
            String result = Requests.post(url)
                    .headers(Maps.of("Content-Type","application/json"))
                    .body(reqParam)
                    .send().readToText();
            Map resp = (Map) JSON.parse(result);
            ret.add((String) ((Map)resp.get("data")).get("debugInfo"));
        }
        System.out.println(ret.size());
    }

    private static List<String> generateParams(List<String> params) {
        List<String> ret = new ArrayList<>();
        for(String param : params){
            String[] paramArr = param.split(",");
            String enTollStationHex = paramArr[0].trim();
            if(enTollStationHex.length()==3){
                enTollStationHex="0"+enTollStationHex;
            }
            String exStationHex = paramArr[1].trim();
            if(exStationHex.length()==3){
                exStationHex="0"+exStationHex;
            }

            String oriIntervalsGroup = paramArr.length>2 ? paramArr[2].trim():"";
            ret.add(generatorCQ(enTollStationHex, exStationHex, oriIntervalsGroup));
        }
        return ret;
    }

    private static List<String> loadParams() {
        List<String> ret = new ArrayList<>();
        String csvFile = "E:\\work\\2024\\0718重门架牌识混合计费问题数据分析\\请求参数.csv";
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                // 处理每一行数据，如打印或保存到集合中
                if(!"".equals(line.trim()) && !line.contains("EN_STATION_HEX")){
                    ret.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static String generatorCQ(String enTollStationHex, String exStationHex, String oriIntervalsGroup){
//        String enTollStationHex = "0320";
//        String exStationHex = "6804";
        String vehiclePlate = "渝AG8H02_0";
        String enTime = "2024-05-16T11:29:23";
        String exTime = "2024-05-16T18:46:02";
        Integer mediaType = 1;
        Integer vehicleType = 1;
        Integer vehicleClass = 0;
        String passId = "020000410101610022908820240303205320";
        String mediaNo = "02000023010165";

//        String oriIntervalsGroup = "590309_0|594909_0|594909_0|594909_1|594909_1|594908_0|594908_0|594908_1|594908_1|594907_1|594907_1|594907_0|594907_0|584401_1|584401_1|584401_0|584401_0|584402_0|584402_1|581D02_0|581D02_1|591F08_0|591F08_1|584404_0|584404_1|584405_0|584405_1";

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
        Date curTransTime = parseDate(enTime, format_yyyy_MM_ddTHHmmss);
        int curSerial = -1;
        HashSet<String> gantrySet = new HashSet<String>();
        if(!StringUtil.isEmpty(oriIntervalsGroup)){
            for (String gxT : split) {
                String[] dx = gxT.trim().split("_");
                String gantryHex = dx[0];
                String type = dx[1];

                //每次加2分钟时间
                curTransTime = addTime(curTransTime, 1, Calendar.MINUTE);
                String curTransTimeStr = formatDate(curTransTime, format_yyyy_MM_ddTHHmmss);
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
                            curSerial++;
                            sb.append("\\\"oSa\\\": \\\"").append(curSerial).append("\\\",");
                        }else {
                            sb.append("\\\"oSa\\\": \\\"").append(curSerial).append("\\\",");
                        }
                    }

                    sb.append("\\\"tId\\\": \\\"").append(getUUID()).append("\\\"},");
                }else {
                    //牌识流水
                    sb.append("{\\\"tT\\\":\\\"").append(curTransTimeStr).append("\\\",")
                            .append("\\\"gH\\\": \\\"").append(gantryHex).append("\\\",")
                            .append("\\\"t\\\": \\\"1\\\",")
                            .append("\\\"eRT\\\": \\\"").append(curTransTimeStr).append("\\\",");
                    sb.append("\\\"pId\\\": \\\"").append(getUUID()).append("\\\"},");
                }
            }
            //去掉最后一个逗号
            sb.deleteCharAt(sb.length()-1);
        }

        sb.append("]\"}");

        return sb.toString();
    }

    public static Date parseDate(String date, String format) {
        Date result = null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if (date != null && !("".equals(date))) {
            try {
                result = sdf.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String formatDate(Date date, String format) {
        String result = "";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if (date == null) {
            throw new NullPointerException("格式化时间对象不能为null!");
        } else {
            result = sdf.format(date);
        }
        return result;
    }

    public static Date addTime(Date date, int amount, int field) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(field, amount);
        return cal.getTime();
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}