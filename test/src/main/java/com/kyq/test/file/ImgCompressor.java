package com.kyq.test.file;

import net.coobird.thumbnailator.Thumbnails;

import java.io.IOException;

/**
 * Description:
 * Copyright: ? 2017

 *
 * @version 1.0
 * @author: kyq1024
 * @timestamp: 2018-08-02 10:52
 */
public class ImgCompressor {

    public static void main(String args[]) throws IOException {
        Thumbnails.of("C:\\Users\\kyq1024\\Desktop\\work\\backup\\source.jpg")
                .scale(1f)
                .outputQuality(0.2f)
                .outputFormat("jpg")
                .toFile("C:\\Users\\kyq1024\\Desktop\\work\\backup\\targetimg2.jpg");
    }
}
