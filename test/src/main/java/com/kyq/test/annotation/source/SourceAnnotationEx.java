package com.kyq.test.annotation.source;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description:
\\\". All rights reserved.

 *
 * @version 1.0
 * @author: kyq1024
 * @timestamp: 2017-12-10 17:53
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface SourceAnnotationEx {
    String source() default "source";
}
