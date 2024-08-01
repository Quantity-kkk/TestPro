package com.kyq.test.common;

import net.dongliu.requests.RawResponse;
import net.dongliu.requests.Requests;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Description:
\\\". All rights reserved.

 *
 * @version 1.0
 * @author: kyq1024
 * @timestamp: 2018-06-15 10:29
 */
public class RequestsTest {
    public static void main(String[] args){
        //20-14=
        System.out.println("511000806020404001194".length());
        String s = new String("06020404".toCharArray(),0,4);
        System.out.println(s);
//        testF();
    }

    public static void testF(){
        ///getHeapUpInfo
        RawResponse send = Requests.get("http://localhost:8080/picture_monitor_war_exploded/picture/getHeapUpInfo").send();
        Map ret = send.readToJson(Map.class);
        System.out.println(ret);
    }

    public void request(){
        String url = "http://localhost:8080/controller/json.action";
        Map params = new HashMap<>();
        params.put("actionName","complaint-external-query");
        params.put("postData","{}");
        params.put("start",1);
        params.put("limit",5);
        String s = (String) Optional.ofNullable(params).map(x->x.get("aaa")).orElse("didi");
//        Requests.post(url)
//                .multiPartBody(
//                        Part.file("uploadFile",new File("C:\\Users\\kyq1024\\Desktop\\work\\sign1.png")),
//                        Part.file("uploadFile",new File("C:\\Users\\kyq1024\\Desktop\\work\\sign2.png")),
//                        Part.text("actionName","complaint-external-query"),
//                        Part.text("postData","{}")
//                ).send();

        RawResponse send = Requests.post(url).body(params).send();
        String resp = send.readToText();
        System.out.println(resp);
    }
}
