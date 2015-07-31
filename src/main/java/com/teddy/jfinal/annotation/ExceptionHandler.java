package com.teddy.jfinal.annotation;

import java.lang.annotation.*;

/**
 * Created by teddy on 2015/7/19.
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface ExceptionHandler {
    Class<? extends Exception>[] value();
}
