package com.kyq.test.common;


import sun.misc.BASE64Encoder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Description:
 * Copyright: © 2018

 *
 * @author kyq1024
 * @version 1.0
 * @timestamp 2018/9/20
 */
public class Base64FileTest {

    public static void main(String args[]){

        getBaseStr();
    }

    public static void getBaseStr(){
        String filePath = "C:\\Users\\31746\\Pictures\\Camera Roll\\清凉用品.jpg";
        byte[] data = null;
        try (FileInputStream in = new FileInputStream(filePath)){
            data = new byte[in.available()];
            in.read(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BASE64Encoder encoder = new BASE64Encoder();
        System.out.println(encoder.encode(data));
    }

}
