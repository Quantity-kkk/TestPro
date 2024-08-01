package com.kyq.test.annotation.runtime;

/**
 * Description:
\\\". All rights reserved.

 *
 * @version 1.0
 * @author: kyq1024
 * @timestamp: 2017-12-10 22:19
 */
@AnnoEx1
public class TestRuntimeMain {
    public static void main(String args[]){
//        RuntimeAnnotationEx annotationEx = TestRuntimeMain.class.getAnnotation(RuntimeAnnotationEx.class);
//        Annotation[] annotations = RuntimeUseCase.class.getAnnotations();

//        System.out.println(annotationEx.value());

        boolean annotationPresent = RuntimeUseCase.class.isAnnotationPresent(RuntimeAnnotationEx.class);
        System.out.println(annotationPresent);

    }

}
