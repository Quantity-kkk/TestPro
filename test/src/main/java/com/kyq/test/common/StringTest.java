package com.kyq.test.common;

import com.jfinal.kit.StrKit;
import com.kyq.util.DateUtil;
import com.kyq.util.StringUtil;
import org.junit.Test;

import javax.print.*;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * Description:
\\\". All rights reserved.

 *
 * @version 1.0
 * @author: kyq1024
 * @timestamp: 2017-12-05 11:06
 * -XX:+PrintGCDetails
 */
public class StringTest {
    public static void main(String args[]){
        System.out.println(String.format("%02d",33));

        String str = "1|2|3||5";
        String[] strArr = str.split("\\|");
//        List list =new ArrayList(Arrays.asList(strArr));
        List list = new LinkedList();
        list.add(1);
        list.add(3);
        list.add(4);
        list.add(5);
//        list.add(1,12);

        int len = list.size();
        for(int i=0; i < len; i++){
            if(i==2){
                list.addAll(i,Arrays.asList(strArr));
                len+=strArr.length;
            }
            System.out.print(list.get(i)+",");
        }
//        list.add("1");
//        list.add("2");
//        list.add("3");
//        list.add("4");
//        list.add("5");
//        test(list);
        System.out.println(list);
//        testPrinter();
//        testDivide();
//        System.gc();
//        cutYear();
//        "11010111 0001 110001 110000 110000 110001 110101 110000 110000 110101 110001 110100 110000 110001 110010";
//        strToBinstr("51 10015 005 14012");
//        substest();
//        Timestamp timestamp = Timestamp.valueOf("2018-06-28 17:27:58.000000");
//        System.out.println(timestamp);
//        String s = "aaaa,aaaa";
//        String t[] = s.split(",");
//        System.out.println(new HashSet<>().add(""));
//        Long l = new Long(1521);
//        long s = 2312L;
//        System.out.println(l+"");
//        System.out.println(s+"");
//        testVolatile();

//        Map map = new HashMap(){
//            {put("病假",1);}
//            {put("产假",2);}
//        };
//        List list = (List) map.entrySet().stream().map(x->{
//            Map out = new HashMap<>();
//            out.put("name",((Map.Entry) x).getKey());
//            out.put("value",((Map.Entry) x).getValue());
//            return out;
//        }).collect(Collectors.toList());
//        System.out.println(list);
    }
    public static void test(List list){
        List ret = new ArrayList();
        ret.add("t1");
        list = ret;
    }
    public static void testPrinter(){
        DocFlavor flavor = DocFlavor.INPUT_STREAM.TEXT_PLAIN_UTF_8;
//        flavor = new DocFlavor.INPUT_STREAM("application/octet-stream; charset=UTF-8");
        HashDocAttributeSet pras = new HashDocAttributeSet();
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(flavor,pras);

        System.out.println(1);
        System.setProperty("jna.encoding", "UTF-8");
        System.setProperty("file.encoding", "UTF-8");
//        for(PrintService service : printServices){
//            if(service.getName().contains("M701n")){
//                System.out.println(service.getName());
//                System.out.println(DocFlavor.hostEncoding);
//                print(service);
//            }
//        }

        PrintService service1 = PrintServiceLookup.lookupDefaultPrintService();
        Class<?>[] supportedAttributeCategories = service1.getSupportedAttributeCategories();
        DocFlavor[] supportedDocFlavors = service1.getSupportedDocFlavors();
        System.out.println(1);

//        print(service1);
    }
    public static void print(PrintService service){
        DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
        try {
            DocPrintJob printJob = service.createPrintJob();
//            DocFlavor[] supportedDocFlavors = printJob.getPrintService().getSupportedDocFlavors();
            Doc doc = new SimpleDoc(new FileInputStream(new File("D:\\workdocument\\加班生活费\\加班生活费记录.xlsx")),flavor, null);
            printJob.print(doc,  new HashPrintRequestAttributeSet());


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (PrintException e) {
            e.printStackTrace();
        }
    }

    public static void testDivide(){
        Integer i = 20;
        for (int k=0;k<i;k++){
            System.out.println("i(i/10):"+(i/10));
        }
    }

    public static void substest(){
        String str = "audit_status_desc";
        System.out.println(str.substring(0,str.length()-5));

    }
    public static void cutYear(){
        Calendar a = Calendar.getInstance();
        System.out.print( (a.get(Calendar.YEAR)+"").toString().substring(2));
    }

    // 将字符串转换成二进制字符串，以空格相隔
    private static String strToBinstr(String str) {
        char[] strChar = str.toCharArray();
        String result = "";
        for (int i = 0; i < strChar.length; i++) {
            result += Integer.toBinaryString(strChar[i]) + " ";
        }
        System.out.println(result);
        return result;
    }

    private static void testVolatile(){
        List array = new ArrayList<>();
        for(int i=0;i<100000;i++){
            array.add(i);
        }
        AtomicInteger a = new AtomicInteger(0);
        long startA = System.currentTimeMillis();
        array.stream().forEach(x->a.incrementAndGet());
        long endA = System.currentTimeMillis();

        AtomicInteger b = new AtomicInteger(0);
        long startB = System.currentTimeMillis();
        array.parallelStream().forEach(x->b.incrementAndGet());
        long endB = System.currentTimeMillis();
        System.out.println("a:"+a.get()+"  cost:"+(endA-startA));
        System.out.println("b:"+b.get()+"  cost:"+(endB-startB));

    }

    @Test
    public void testSub(){
        System.out.println("S010150006000810".substring(14,15));
    }

    @Test
    public void testIndex(){
        String str = "test.aa.class";
        System.out.println(str.substring(0,str.lastIndexOf(".")));
    }

    @Test
    public void testDe(){
//        int a = 59;
//        int b = (a/5)*5;
//        System.out.println(b);
//        System.out.println(String.format("%04d",1));
        String str = "G005050004000210|G005050004000310|G005050004000410|G005050004000510|G005050004000610|G005050004000710|G500150001000920|G500150001000820|G500150001000720|G500150001000620|G500150001000520|G500150001000420|G500150001000320|G500150001000220|G500150001000120|G500150002001920|G500150002001820|G500150002001720|G009350001001220|G009350001001120|G009350001001020|G009350001000920|G009350001000820|G009350001000720|G009350001000620|G009350001000520";
        System.out.println("tollIntervalsGroup:"+str.split("\\|").length);
//        for(String val : str.split("\\|"))
//            System.out.println(val);
        str = "15|27|12|15|67|7|6|8|39|7|9|25|11|11|23|26|17|7|13|13|77|17|5|5|29|51";
        System.out.println("chargeDiscountGroup:"+str.split("\\|").length);

        str = "2019-12-05T09:37:25|2019-12-05T09:37:25|2019-12-05T09:37:25|2019-12-05T09:37:25|2019-12-05T09:37:25|2019-12-05T09:37:25|2019-12-05T09:37:25|2019-12-05T09:37:25|2019-12-05T09:37:25|2019-12-05T09:37:25|2019-12-05T09:37:25|2019-12-05T09:37:25|2019-12-05T09:37:25|2019-12-05T09:37:25|2019-12-05T09:37:25|2019-12-05T09:37:25|2019-12-05T09:37:25|2019-12-05T09:37:25|2019-12-05T09:37:25|2019-12-05T09:37:25|2019-12-05T09:37:25|2019-12-05T09:37:25|2019-12-05T09:37:25|2019-12-05T09:37:25|2019-12-05T09:37:25|2019-12-05T09:37:25";
        System.out.println("transTimeGroup:"+str.split("\\|").length);

        str = "5020200120005|5020200120005,5020200120005|5020200120005,5020200120005|5020200120005,5020200120005|5020200120005,5020200120005|5020200120005,5020200120005|5020200120005,5020200120005|5020200120005,5020200120005|5020200120005,5020200120005|5020200120005,5020200120005|5020200120005,5020200120005|5020200120005,5020200120005|5020200120005,5020200120005|5020200120005,5020200120005|5020200120005,5020200120005|5020200120005,5020200120005|5020200120005,5020200120005|5020200120005,5020200120005|5020200120005,5020200120005|5020200120005,5020200120005|5020200120005,5020200120005|5020200120005,5020200120005|5020200120005,5020200120005|5020200120005,5020200120005|5020200120005,5020200120005|5020200120005,5020200120005|5020200120005";
        System.out.println("rateVersionGroup:"+str.split(",").length);

        str = "580302|580303|580304|580305|580306|580307|592309|592308|592307|592306|592305|592304|592303|592302|592301|592413|592412|592411|591E0C|591E0B|591E0A|591E09|591E08|591E07|591E06|591E05";
        System.out.println("realGantryHexGroup:"+str.split("\\|").length);

        str = "285|523|238|285|1283|143|121|155|745|141|171|490|225|214|453|503|333|152|249|265|1466|335|100|95|570|983";
        System.out.println("chargsFeeGroup:"+str.split("\\|").length);

        str = "300|550|250|300|1350|150|127|163|784|148|180|515|236|225|476|529|350|159|262|278|1543|352|105|100|599|1034";
        System.out.println("payFeeGroup:"+str.split("\\|").length);

        System.out.println(Arrays.asList(str.split("\\|")));
    }

    @Test
    public void dateConvert(){
        Date d = new Date(1576287785000L);
        SimpleDateFormat sf =new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        System.out.println(sf.format(d));
        Date transDate = new Date(1576287906000L);

        System.out.println(sf.format(transDate));
    }

    @Test
    public void dateTest(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHH");

        System.out.println(simpleDateFormat.format(date));

        List arr = Arrays.asList("0","1","2","3","4","5","6","7");
        System.out.println(arr.subList(6+1,8));
    }

    @Test
    public void testDateStr(){
        /*
        String passId = "868800080468427420191130162043";
        String passId2 ="86b800013168e89e20191127173419";
        System.out.println(passId.substring(passId.length()-14));

        System.out.println(passId2.substring(passId2.length()-14));

        System.out.println(passId.substring(0, passId.length()-14));
        System.out.println(passId2.substring(0, passId2.length()-14));

         */

        /*
        Random r = new Random(System.currentTimeMillis());
        for(int i = 0; i <100 ; i++){
            System.out.println(r.nextInt());
        }
         */



        System.out.println(5001/100);
    }


    @Test
    public void testRepeat(){
        String str = "2019-12-30T18:06:31_2019-12-30T18:54:30_2019-12-30T18:27:41*2019-12-30T18:33:19_2019-12-30T18:41:28_2019-12-30T18:50:31_2019-12-30T18:54:30_2019-12-30T18:54:30";
        System.out.println(str);
        System.out.println(resolveRepeatedTransTime(str));
    }

    private static String resolveRepeatedTransTime(String transTimeGroup){
        String[] transTimeGroupArr = transTimeGroup.split("\\*");
        if(transTimeGroupArr.length>0){
            Set<String> transTimes = new HashSet<>();
            int addSecond = -1;
            int outerSize = transTimeGroupArr.length;
            for (int i = outerSize-1; i >= 0; i--){
                if(StrKit.notBlank(transTimeGroupArr[i])){
                    String[] innerTransTimeGroupArr = transTimeGroupArr[i].split("[_*]");
                    int innerSize = innerTransTimeGroupArr.length;
                    for(int j = innerSize-1; j >= 0; j--){
                        //修正数据
                        String transTimeStr = innerTransTimeGroupArr[j];
                        if(StringUtil.isNotEmpty(transTimeStr) && transTimeStr.contains("T")){
                            while (!transTimes.add(transTimeStr)){
                                Date transDatTmp = DateUtil.parseDate(transTimeStr, DateUtil.format_yyyy_MM_ddTHHmmss);
                                transTimeStr = DateUtil.formatDate(DateUtil.addTime(transDatTmp, addSecond, Calendar.SECOND),DateUtil.format_yyyy_MM_ddTHHmmss);
                            }
                            innerTransTimeGroupArr[j] = transTimeStr;
                        }
                    }
                    //拼接数据
                    transTimeGroupArr[i] = Stream.of(innerTransTimeGroupArr).reduce((x, y)->x+"_"+y).get();
                }
            }
            //拼接数据
            transTimeGroup = Stream.of(transTimeGroupArr).reduce((x,y)->x+"*"+y).get();
        }
        return transTimeGroup;
    }
}
