package com.kyq.test.list;

import lombok.val;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Description： com.kyq.test.list
\\\"

 *
 * @author kyq1024
 * @version 1.0
 * @timestamp 2023-06-28 10:23
 */
public class ListStreamTest {
    /**
     * 测试stram().filter保留的是哪一条数据
     * */
    public static void main(String[] args){
        List<Map> treadFlowing = generateTradeFlows();

        ArrayList<Map> collect = treadFlowing.stream()
                .collect(Collectors.collectingAndThen(Collectors.toCollection(() ->
                        new TreeSet<>(Comparator.comparing(e -> e.get("gantryHex") + ";" + e.get("transTime")))), ArrayList::new));

        List  transFlowPassStations = treadFlowing.stream()
                .collect(Collectors.collectingAndThen(Collectors.toCollection(() ->
                        new TreeSet<>(Comparator.comparing(e -> e.get("gantryHex") + ";" + e.get("transTime")))), ArrayList::new)).stream()
                .filter(distinctByKey(b -> b.get("gantryPassSerial")))
                .sorted(Comparator.comparingInt(o -> Integer.valueOf((String) o.get("gantryPassSerial"))))
                .collect(Collectors.toList());
        System.out.println("1");
    }

    private static List<Map> generateTradeFlows() {
        List<Map> ret = new ArrayList<>();
        //保留第一个门架和时间相同的数据
        ret.add(getTrade("00000001", "2023-06-28T10:37:00", "2"));
        ret.add(getTrade("00000001", "2023-06-28T10:37:00", "3"));
        ret.add(getTrade("00000001", "2023-06-28T10:37:00", "1"));
        //保留第一个门架和时间相同的数据
        ret.add(getTrade("00000002", "2023-06-28T10:37:00", "4"));
        ret.add(getTrade("00000002", "2023-06-28T10:37:00", "6"));
        //保留第一个门架和时间相同的数据
        ret.add(getTrade("00000003", "2023-06-28T10:37:00", "7"));
        ret.add(getTrade("00000002", "2023-06-28T10:37:02", "2"));
        ret.add(getTrade("00000003", "2023-06-28T10:37:00", "9"));
        //保留第一个门架和时间相同的数据
        ret.add(getTrade("00000004", "2023-06-28T10:37:00", "12"));
        ret.add(getTrade("00000004", "2023-06-28T10:37:00", "10"));
        ret.add(getTrade("00000003", "2023-06-28T10:37:00", "8"));
        ret.add(getTrade("00000004", "2023-06-28T10:37:00", "11"));
        return ret;
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object,Boolean> seen = new ConcurrentHashMap<>();
        return t->seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    private static Map getTrade(String gantryHex, String transTime, String gantryPassSerial){
        Map ret = new HashMap(4);
        ret.put("gantryHex", gantryHex);
        ret.put("transTime", transTime);
        ret.put("gantryPassSerial", gantryPassSerial);
        return ret;
    }

    private static Object apply(Object x) {
        System.out.println("i like lambda5");
        return x;
    }
}
