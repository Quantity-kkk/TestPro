package com.kyq.test.common;

import net.dongliu.requests.RawResponse;
import net.dongliu.requests.Requests;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Description:
\\\". All rights reserved.

 *
 * @version 1.0
 * @author: kyq1024
 * @timestamp: 2018-07-13 9:42
 */
public class TestSmsInf {

    public static void main(String[] args){
        String msg =  "%s 针对设备 %s 的工单已经于 %s 撤销。";
        msg = String.format(msg, "李响", "监控收费器", new SimpleDateFormat("yyyy-MM-dd H:m:s").format(new Date()));

        String url = "http://172.16.10.66:9991/v1/messageService/sendBatch";
        String token = "3ac8d0ba-0e53-4914-b768-01843c3c3454";
        String target = "13508366029,15215036201";
        Map params = new HashMap<>();
        params.put("smss",target);
        params.put("content",msg);
        params.put("token",token);//TODO unknown token...
        final RawResponse resp = Requests.get(url).params(params).send();

        System.out.println("SmsHelper:Sms Message push result,"+resp.readToText());
    }
}
