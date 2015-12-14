package com.teddy.jfinal.annotation;

import java.lang.annotation.*;

/**
 * Created by teddyzhu on 15/12/12.
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface CustomAnnotation {
}
