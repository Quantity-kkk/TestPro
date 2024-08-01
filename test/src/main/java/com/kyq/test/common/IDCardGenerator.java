package com.kyq.test.common;

/**
 * Description:
 * Copyright: © 2018

 *
 * @author kyq1024
 * @version 1.0
 * @timestamp 2018/12/10
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;

public class IDCardGenerator {

    public static final Map<String, Integer> areaCode = new HashMap<String, Integer>();

    static {
        IDCardGenerator.areaCode.put("万州区", 500101);
        IDCardGenerator.areaCode.put("涪陵区", 500102);
        IDCardGenerator.areaCode.put("渝中区", 500103);
        IDCardGenerator.areaCode.put("大渡口区", 500104);
        IDCardGenerator.areaCode.put("江北区", 500105);
        IDCardGenerator.areaCode.put("沙坪坝区", 500106);
        IDCardGenerator.areaCode.put("九龙坡区", 500107);
        IDCardGenerator.areaCode.put("南岸区", 500108);
        IDCardGenerator.areaCode.put("北碚区", 500109);
        IDCardGenerator.areaCode.put("万盛区", 500110);
        IDCardGenerator.areaCode.put("双桥区", 500111);
        IDCardGenerator.areaCode.put("渝北区", 500112);
        IDCardGenerator.areaCode.put("巴南区", 500113);
        IDCardGenerator.areaCode.put("黔江区", 500114);
        IDCardGenerator.areaCode.put("长寿区", 500115);
        IDCardGenerator.areaCode.put("江津区", 500116);
        IDCardGenerator.areaCode.put("合川区", 500117);
        IDCardGenerator.areaCode.put("永川区", 500118);
        IDCardGenerator.areaCode.put("南川区", 500119);
        IDCardGenerator.areaCode.put("綦江县", 500222);
        IDCardGenerator.areaCode.put("潼南县", 500223);
        IDCardGenerator.areaCode.put("铜梁县", 500224);
        IDCardGenerator.areaCode.put("大足县", 500225);
        IDCardGenerator.areaCode.put("荣昌县", 500226);
        IDCardGenerator.areaCode.put("璧山县", 500227);
        IDCardGenerator.areaCode.put("梁平县", 500228);
        IDCardGenerator.areaCode.put("城口县", 500229);
        IDCardGenerator.areaCode.put("丰都县", 500230);
        IDCardGenerator.areaCode.put("垫江县", 500231);
        IDCardGenerator.areaCode.put("武隆县", 500232);
        IDCardGenerator.areaCode.put("忠　县", 500233);
        IDCardGenerator.areaCode.put("开　县", 500234);
        IDCardGenerator.areaCode.put("云阳县", 500235);
        IDCardGenerator.areaCode.put("奉节县", 500236);
        IDCardGenerator.areaCode.put("巫山县", 500237);
        IDCardGenerator.areaCode.put("巫溪县", 500238);
        IDCardGenerator.areaCode.put("石柱土家族自治县", 500240);
        IDCardGenerator.areaCode.put("秀山土家族苗族自治县", 500241);
        IDCardGenerator.areaCode.put("酉阳土家族苗族自治县", 500242);
        IDCardGenerator.areaCode.put("彭水苗族土家族自治县", 500243);
    }

    /**
     * 生成方法
     * @return
     */
    public String generate() {
        StringBuilder generater = new StringBuilder();
        generater.append(this.randomAreaCode());
        generater.append(this.randomBirthday());
        generater.append(this.randomCode());
        generater.append(this.calcTrailingNumber(generater.toString().toCharArray()));
        return generater.toString();
    }

    /**
     * 随机地区
     * @return
     */
    public int randomAreaCode() {
        int index = (int) (Math.random() * IDCardGenerator.areaCode.size());
        Collection<Integer> values = IDCardGenerator.areaCode.values();
        Iterator<Integer> it = values.iterator();
        int i = 0;
        int code = 0;
        while (i < index && it.hasNext()) {
            i++;
            code = it.next();
        }
        return code;
    }

    /**
     * 随机出生日期
     * @return
     */
    public String randomBirthday() {
        Calendar birthday = Calendar.getInstance();
        birthday.set(Calendar.YEAR, (int) (Math.random() * 60) + 1950);
        birthday.set(Calendar.MONTH, (int) (Math.random() * 12));
        birthday.set(Calendar.DATE, (int) (Math.random() * 31));

        StringBuilder builder = new StringBuilder();
        builder.append(birthday.get(Calendar.YEAR));
        long month = birthday.get(Calendar.MONTH) + 1;
        if (month < 10) {
            builder.append("0");
        }
        builder.append(month);
        long date = birthday.get(Calendar.DATE);
        if (date < 10) {
            builder.append("0");
        }
        builder.append(date);
        return builder.toString();
    }

    /*
     * <p>18位身份证验证</p>
     * 根据〖中华人民共和国国家标准 GB 11643-1999〗中有关公民身份号码的规定，公民身份号码是特征组合码，由十七位数字本体码和一位数字校验码组成。
     * 排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码，三位数字顺序码和一位数字校验码。
     * 第十八位数字(校验码)的计算方法为：
     * 1.将前面的身份证号码17位数分别乘以不同的系数。从第一位到第十七位的系数分别为：7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2
     * 2.将这17位数字和系数相乘的结果相加。
     * 3.用加出来和除以11，看余数是多少？
     * 4.余数只可能有0 1 2 3 4 5 6 7 8 9 10这11个数字。其分别对应的最后一位身份证的号码为1 0 X 9 8 7 6 5 4 3 2。
     * 5.通过上面得知如果余数是2，就会在身份证的第18位数字上出现罗马数字的Ⅹ。如果余数是10，身份证的最后一位号码就是2。
     */
    public char calcTrailingNumber(char[] chars) {
        if (chars.length < 17) {
            return ' ';
        }
        int[] c = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
        char[] r = { '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' };
        int[] n = new int[17];
        int result = 0;
        for (int i = 0; i < n.length; i++) {
            n[i] = Integer.parseInt(chars[i] + "");
        }
        for (int i = 0; i < n.length; i++) {
            result += c[i] * n[i];
        }
        return r[result % 11];
    }

    /**
     * 随机产生3位数
     * @return
     */
    public String randomCode() {
        int code = (int) (Math.random() * 1000);
        if (code < 10) {
            return "00" + code;
        } else if (code < 100) {
            return "0" + code;
        } else {
            return "" + code;
        }
    }

    /**
     * 将数据写入txt文件
     * @param filePath   txt文件地址
     * @param content    需要插入的内容
     */

    public static void contentToTxt(String filePath, String content) {
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filePath),true));
            writer.write("\n"+content);
            writer.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        System.out.println(new IDCardGenerator().generate());
    }
}