package com.teddy.jfinal.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD, ElementType.TYPE})
public @interface ValidateParams {
    ValidateParam[] fields() default {};

    boolean select() default false;

    ValidateComVar condition() default @ValidateComVar;

    ValidateParam[] trueFields() default {};

    ValidateParam[] falseFields() default {};

}
