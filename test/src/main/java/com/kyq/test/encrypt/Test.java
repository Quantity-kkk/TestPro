package com.kyq.test.encrypt;

import com.github.cheergoivan.totp.TOTPAuthenticator;
import com.kyq.util.Maps;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Description:
\\\". All rights reserved.

 *
 * @version 1.0
 * @author: kyq1024
 * @timestamp: 2018-07-10 14:09
 */
public class Test {
    public static void main(String args[]) throws UnsupportedEncodingException {
        BigInteger a = new BigInteger("1111111111111111111111111111111111111111111111111111111111111111",2);
//        Integer b = Integer.valueOf("1111111111111111111111111111111111111111111111111111111111111111",2);
//        Long a = Long.valueOf("0b1111111111111111111111111111111111111111111111111111111111111111");
        System.out.println(a);
//        List list = Arrays.asList("1","2","3","4","5","6","7","8","9");
//        System.out.println(list);
//        Collections.shuffle(list);
//        System.out.println(list);
//        System.out.println(list.subList(0,6));
//        String actionName="common-component-init-load";
//        String postData= "{\"menu_code\":\"ASSET_BASE_INFO_AUDIT_A\",\"combo_item_list\":[{\"cacheKey\":\"bb946fb84f8ef209b2f2910b2353daba\",\"cacheKeyWithMenuCode\":\"28010f6e6ae22fa428c967cb0902f633\",\"cacheObj\":{\"comboType\":\"scdp_fmcode\",\"codeType\":\"INSTITUTION_TYPE\",\"filterMap\":{},\"menuCode\":\"ASSET_BASE_INFO_AUDIT_A\"}},{\"cacheKey\":\"5e183c504420a295daeb8fdccc3e4cc8\",\"cacheKeyWithMenuCode\":\"0fd0b0f3faf4bbb4c9da115ac6cf6f5a\",\"cacheObj\":{\"comboType\":\"query_locate_type\",\"codeType\":\"\",\"filterMap\":{\"locationExpandValue\":\"\"},\"menuCode\":\"ASSET_BASE_INFO_AUDIT_A\"}},{\"cacheKey\":\"5e9ac850eadc8efc6f992e6523218e21\",\"cacheKeyWithMenuCode\":\"63159507d85251cb59893b56da1063b3\",\"cacheObj\":{\"comboType\":\"query_locate_desc_type\",\"codeType\":\"\",\"filterMap\":{\"locateType\":\"\",\"locationExpandValue\":\"\",\"locationCode\":\"\"},\"menuCode\":\"ASSET_BASE_INFO_AUDIT_A\"}},{\"cacheKey\":\"eb984b70dbb853737ff518acfacb35fe\",\"cacheKeyWithMenuCode\":\"11f1bc264a79cc1dbc743910020afc79\",\"cacheObj\":{\"comboType\":\"query_knowledge_company\",\"codeType\":\"\",\"filterMap\":{},\"menuCode\":\"ASSET_BASE_INFO_AUDIT_A\"}},{\"cacheKey\":\"0a40ecc952df40f42bd349d00247170f\",\"cacheKeyWithMenuCode\":\"402a1a1112695463bbc3a3a3ce3942a1\",\"cacheObj\":{\"comboType\":\"query_knowledge_company_model\",\"codeType\":\"\",\"filterMap\":{\"producer\":\"\"},\"menuCode\":\"ASSET_BASE_INFO_AUDIT_A\"}},{\"cacheKey\":\"3d6cdd2d1aa16edf7b8a785d1c912ce3\",\"cacheKeyWithMenuCode\":\"f189908879d1e14d5829b63961ee7a0e\",\"cacheObj\":{\"comboType\":\"scdp_fmcode\",\"codeType\":\"ASSET_AUDIT_STATUS\",\"filterMap\":{},\"menuCode\":\"ASSET_BASE_INFO_AUDIT_A\"}},{\"cacheKey\":\"62977d75a1143363c676152701a52489\",\"cacheKeyWithMenuCode\":\"60c9fed8d5b35386a44f9748157a20b1\",\"cacheObj\":{\"comboType\":\"scdp_fmcode\",\"codeType\":\"ASSET_SOURCE\",\"filterMap\":{},\"menuCode\":\"ASSET_BASE_INFO_AUDIT_A\"}},{\"cacheKey\":\"77346a0b38562dfeeff2cb31b3f25ffe\",\"cacheKeyWithMenuCode\":\"4c68e95fd17178f66220aa162a6f2e4e\",\"cacheObj\":{\"comboType\":\"scdp_fmcode\",\"codeType\":\"ASSET_STATUS\",\"filterMap\":{},\"menuCode\":\"ASSET_BASE_INFO_AUDIT_A\"}},{\"cacheKey\":\"2bfa3a139d17af35e0fd4febe6ef219b\",\"cacheKeyWithMenuCode\":\"a6f64881a0dc7c5bac80b33b9e46632c\",\"cacheObj\":{\"comboType\":\"scdp_fmcode\",\"codeType\":\"ASSET_DEPRECIATION\",\"filterMap\":{},\"menuCode\":\"ASSET_BASE_INFO_AUDIT_A\"}}],\"tree_item_list\":[{\"cacheKey\":\"93887772911bbb351956bcd694fa1963\",\"cacheKeyWithMenuCode\":\"9fa28806126b0025306f38abd7412274\",\"cacheObj\":{\"comboType\":\"sys-org-treeload\",\"filterMap\":{},\"itemId\":\"manageUnit\"}},{\"cacheKey\":\"c1db9b805d02ddd4904e812adda75582\",\"cacheKeyWithMenuCode\":\"1924bc110083ef538c3f528f0a186a24\",\"cacheObj\":{\"comboType\":\"devicetype-treeload\",\"filterMap\":{},\"itemId\":\"deviceTypeCode\"}},{\"cacheKey\":\"9d9ecf30d945a05a7b8fc6eacd42d42c\",\"cacheKeyWithMenuCode\":\"ff5bce6be5497ab3c9741aeb162d4d87\",\"cacheObj\":{\"comboType\":\"managementorgtree-treeload\",\"filterMap\":{\"orgType\":\"C|D\"},\"itemId\":\"locationCode\"}},{\"cacheKey\":\"d86debeab0646bf532eb726f1997aff9\",\"cacheKeyWithMenuCode\":\"e175225f4780b3eafb303787cd3a0d78\",\"cacheObj\":{\"comboType\":\"manage-org-load-by-user-org\",\"filterMap\":{},\"itemId\":\"manageUnit\"}}],\"userId\":\"1\",\"userLocaleId\":\"zh_CN\",\"timestamp\":1545102354428,\"network\":0,\"menuCode\":\"ASSET_BASE_INFO_AUDIT_A\"}\"";
//        String userToken = "e85dfc4bbdb941a08a4701262aca4eeb";
//        String signature = CryptHelper.getSign(actionName,postData,userToken);
//        System.out.println(signature);
        //c0d98ba196de2c772b178974f870a6593732261312d951e72cd3e39c5bbf2434
        //3644689b4361322a75ae824a3496b910a7f2b764e71fdfdc3f76f5e616e881da
//            String key = CryptHelper.encode("977069".getBytes("UTF8"));
//            System.out.println(key);
//            String key = CryptHelper.encode("sysadmin".getBytes("UTF8"));
//            System.out.println(key);
//===
//        String otp = CryptHelper.getTOTP("KNRUO42KIRUU4VDFKJTECY2FEE","sysadmin");
//        System.out.println(otp);
//        boolean v = CryptHelper.verifyTOTP("sysadmin",otp);
//        System.out.println(v);
//        System.out.println(new String(CryptHelper.decode("NBCWYTDPK5XVE3CEEE")));
//        System.out.println(new String(CryptHelper.decode("KNRUO42KIRUU4VDFKJTECY2FEE")));
//        String key1 = CryptHelper.encode("sCgSDsj".getBytes("UTF8"));
//        String key2 = key1+"iAutORhIzAtoIn!";
//        System.out.println(CryptHelper.encode(key2.getBytes("UTF8")));
//        System.out.println(new String(CryptHelper.decode("INTERFACE")));
        //====
        //KNRUO42KIRUU4VDFKJTECY2FEE
        //GSNT44D7Q2O72EC6
//        org.apache.commons.codec.binary.Base64 codec64 = new org.apache.commons.codec.binary.Base64();
//        System.out.println(new String(codec64.decode("GSNT44D7Q2O72EC6")));
//        testGit();
//        testMap();
    }

    public static void testMap(){
        //测试同样的数据放到不同map对象中，顺序是否一致
        Map map = Maps.of("key1","value1","key2","value2","key3","value3","key4","value2","key5","value1","key6","value2","key7","value1","key8","value2");
        List keys = Arrays.asList(map.keySet().toArray(new String[]{}));

        Map map2 = Maps.of("key1","value1","key2","value2","key3","value3","key4","value2","key5","value1","key6","value2","key7","value1","key8","value2");
        List keys2 = Arrays.asList(map2.keySet().toArray(new String[]{}));
        System.out.println(keys);

    }
    public static void testGit(){
        TOTPAuthenticator au = TOTPAuthenticator.builder().timeStepSize(60).build();
        try {
            String key = au.generateTOTP(CryptHelper.encode("sysadmin".getBytes("UTF8")));
            System.out.println(key);

            boolean v = au.validateTOTP(CryptHelper.encode("sysadmin".getBytes("UTF8")),"872819");
            System.out.println(v);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
