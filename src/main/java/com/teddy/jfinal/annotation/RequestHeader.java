package com.teddy.jfinal.annotation;

import java.lang.annotation.*;

/**
 * Created by teddy on 2015/7/21.
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface RequestHeader {

    String[] key();

    String[] value();
}
