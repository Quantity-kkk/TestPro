package com.kyq.test.str;

/**
 * Description： com.kyq.test.str

 *
 * @author kyq1024
 * @version 1.0
 * @timestamp 2023-07-14 14:53
 */
public class StrTest {
    public static void main(String[] args) {
        testCompare();
//        testIndexOf();
//        int stationId = 3075;
//        System.out.println(convert2Hex(stationId));
//        int roadId = stationId/256;
//        System.out.println(roadId);
//        System.out.println(convert2Hex(roadId));
//        String passId = "015201210203000393917320231031235509";
//        System.out.println(passId.substring(0, passId.length()-14));
    }

    public static void testIndexOf(){
        String s = "592A0C_0";
        int i = s.indexOf("_");
        String sb = s.substring(i+1);
    }

    public static void testCompare(){
        String str1 = "590307|590306|590305|59030C|590304|590303";
        String str2 = "590307|590306|590305|59030C";
        System.out.println(str1.compareTo(str2)>0);
    }

    public static String convert2Hex(Integer num){
        return String.format("%04x", num).toUpperCase();
    }
}
