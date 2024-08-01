package com.kyq.test.protocal;

import com.alibaba.fastjson.JSON;
import com.kyq.util.Maps;
import net.dongliu.requests.Requests;
import org.junit.Test;

/**
 * Description:
 * Copyright: © 2019

 *
 * @author kyq
 * @version 1.0
 * @Date 2019/12/19 18:49
 */
public class HttpTest {

    @Test
    public void testFeeCal(){
        String jsonStr = "{\n" +
                "    \"passId\": \"500100040000012120201012000000\",\n" +
                "    \"exStationId\": \"G0085500020003\",\n" +
                "    \"exStationHex\": \"50010299\",\n" +
                "    \"exTime\": \"2020-10-12T01:50:00\",\n" +
                "    \"vehicleType\": 1,\n" +
                "    \"vehicleClass\": 0,\n" +
                "    \"enTollNetwork\": \"5001\",\n" +
                "    \"enTollStationHex\": \"50010206\",\n" +
                "    \"enTime\": \"2020-10-12T00:00:00\",\n" +
                "    \"mediaType\": 1,\n" +
                "    \"mediaNo\": \"5001000400000121\",\n" +
                "    \"interProvincial\": 0,\n" +
                "    \"vehiclePlate\": \"渝A99999_0\",\n" +
                "    \"issuerId\": \"500100\",\n" +
                "    \"cardNetwork\":\"5001\",\n" +
                "    \"cpuCardId\":\"5001000400000121\",\n" +
                "    \"enTollLaneId\":\"G00855000200501010010\",\n" +
                "    \"laneStatus\":1,\n" +
                "    \"lastGantryHex\":\"590204\",\n" +
                "    \"lastPasstime\":\"2020-10-12T00:20:00\",\n" +
                "    \"vehicleUserType\":1,\n" +
                "    \"axleCount\":2,\n" +
                "    \"enAxleCount\":2,\n" +
                "    \"tagType\":0,\n" +
                "    \"cardType\":1,\n" +
                "    \"cardVer\":20200809,\n" +
                "    \"enVehicleType\":1,\n" +
                "    \"feeSumLocal\":null,\n" +
                "    \"vehicleWeightLimits\":1234,\n" +
                "    \"totalWeight\":1234,\n" +
                "    \"vehicleStatusFlag\":\"1\"\n" +
                "}";

        String res = Requests.post("http://10.50.4.125:8088/fee/cal")
                .headers(Maps.of("Content-Type", "application/json"))
                .jsonBody(JSON.parse(jsonStr)).send().readToText();
        System.out.println(res);

    }
}
