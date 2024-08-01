package com.kyq.test.common;

import com.kyq.util.Maps;
import net.dongliu.requests.Requests;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Description:
 * Copyright: © 2019

 *
 * @author kyq
 * @version 1.0
 * @Date 2019/12/6 11:34
 */
public class TestComplex {

    @Test
    public void testMap(){
        String str = "2019-08-30T20:50:30";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            System.out.println(sdf.parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testList(){
        /*
        List ls = new ArrayList();
        ls.add("a");
        ls.add("b");
        ls.add("c");
        ls.removeAll(ls.subList(0,1));
        System.out.println(ls);
         */


        String aa = "a|b|c|d";
        String[] ret = aa.split("\\|");
        for (Object o :ret){
            System.out.println(o);
        }
        List list = new ArrayList();


    }

    @Test
    public void testRate(){
        Map jsonMap = Maps.of(
                "passId","510417420327009520191205021407",
                "enTollStationHex","2312",
                "enTime","2019-12-05T02:14:07",
                "exStationHex","3507",
                "exTime","2019-12-05T08:11:49",
                "vehicleType",4,
                "vehicleClass",0,
                "mediaType",1,
                "mediaNo","5104174203270095",
                "vehiclePlate","川J33208_1"
        );
        Map headers = new HashMap(8);
        headers.put("Content-type","application/json");
        Map ret = Requests.post("http://10.50.4.79:8081/fee/queryFitPath").headers(headers).body(jsonMap).send().readToJson(Map.class);
        System.out.println(ret);
    }

    @Test
    public void testSubList(){
        List base = new LinkedList(Arrays.asList("a","b","c","d","e","f"));
        int en = 1, ex = 4;
        //期望结果,c d
        System.out.println(base.subList(en+1,ex));
        int point = 2;
        base.addAll(point,Arrays.asList("b1","b2"));
        point+=2;
        //期望 abb1b2cdef
        System.out.println(base);

        base.addAll(point,Arrays.asList("b3","b4"));
        System.out.println(base);

    }
}
