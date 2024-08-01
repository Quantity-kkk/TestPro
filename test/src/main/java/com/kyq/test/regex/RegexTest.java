package com.kyq.test.regex;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description:
 * Copyright: © 2019

 *
 * @author kyq
 * @version 1.0
 * @Date 2019/12/2 15:48
 */
public class RegexTest {

    @Test
    public void test(){
        String content1 = "CS_KNOWLEDGEDOWNLOAD_RES_SENDER_20191202.json";
        String content2 = "CS_KNOWLEDGEDOWNLOAD_RES_50_20191202132224111_01.json";

        String pattern1 = "^CS_KNOWLEDGEDOWNLOAD_RES_(\\d{2})_(\\d{4})(\\d+)(\\d+)(\\d{3}).*_([0-9]+).json$";
        String pattern2 = "";

        Pattern r = Pattern.compile(pattern1);

        Matcher m = r.matcher(content2);
        if(m.matches()){
            System.out.println("match");
        }
    }
}
