package com.kyq.test.redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * Description:
 * Copyright: © 2019 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author kyq
 * @version 1.0
 * @Date 2019/12/10 14:52
 */
public class RedisTest {

    @Test
    public void testGet(){
        Jedis jedis = new Jedis("10.50.4.76",6380);
        try{
            String str = "86b80003349ee8b820191205093725," +
                    "86bf00246f692fd920191205092343," +
                    "868801120681675020191205113055," +
                    "370101192644635620191205110331," +
                    "86be0008532e081820191205095204," +
                    "870500248068b3d320191205110750," +
                    "868800030278332620191205105656," +
                    "86be002453275edd20191205095514," +
                    "86b800013167d94c20191205092219," +
                    "450119150825704020191205102459," +
                    "870500248069f91720191205091236," +
                    "510417420224410420191205104132," +
                    "86bf00246f7d3b1c20191205091426," +
                    "360117061854498420191204205425," +
                    "110112011141557720191205104400," +
                    "86bf00246f89ebbf20191205094346," +
                    "86bf01246f81d41720191205094647," +
                    "86b8000334a10df520191205092646," +
                    "510417420327009520191205021407";
//            for(String key : str.split(",")){
//                System.out.println(jedis.llen("CO:"+key));
//            }
            System.out.println(jedis.lrange("CO:86b80003349ee8b820191205093725",0,-1));
            

        }finally {
            jedis.close();
        }
    }


}
