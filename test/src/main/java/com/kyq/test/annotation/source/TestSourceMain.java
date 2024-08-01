package com.kyq.test.annotation.source;


/**
 * Description:
\\\". All rights reserved.

 *
 * @version 1.0
 * @author: kyq1024
 * @timestamp: 2017-12-10 22:24
 */
public class TestSourceMain {
    public static void main(String args[]){
        SourceAnnotationEx annotationEx = TestSourceMain.class.getAnnotation(SourceAnnotationEx.class);
        System.out.println(annotationEx);
    }
}
