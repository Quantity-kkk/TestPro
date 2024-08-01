package com.kyq.test.file;

import net.coobird.thumbnailator.Thumbnails;
import net.dongliu.requests.RawResponse;
import net.dongliu.requests.Requests;

import java.io.IOException;
import java.io.InputStream;

/**
 * Description: 采用http下载文件
\\\". All rights reserved.

 *
 * @version 1.0
 * @author: kyq1024
 * @timestamp: 2018-08-08 9:51
 */
public class HttpDownloadFile {

    public static void main(String args[]) throws IOException {
        String fileUrl = "http://222.66.158.225/filedownload/group1/M00/02/9C/ChISGltqTr-AWI3wAAClATcqHjI93..jpg?token=8a6eb25f5dc95d7f63792f79d3f565c8&ts=1533693684&filename=ff808081644a57e0016517439bb10678.jpg";
        RawResponse send = Requests.get(fileUrl).userAgent("Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)").send();
        InputStream input = send.getInput();

        Thumbnails.of(input).scale(1f)
                .outputQuality(0.25f)
                .outputFormat("jpg")
                .toFile("C:\\Users\\kyq1024\\Desktop\\work\\backup\\httpimg.jpg");
    }

    public static byte[] readFileBytes(InputStream is) {
        byte[] data = null;
        try {
            if (is.available() == 0) {//严谨起见,一定要加上这个判断,不要返回data[]长度为0的数组指针
                return data;
            }
            data = new byte[is.available()];
            is.read(data);
            is.close();
            return data;
        } catch (IOException e) {
            return data;
        }
    }

}
