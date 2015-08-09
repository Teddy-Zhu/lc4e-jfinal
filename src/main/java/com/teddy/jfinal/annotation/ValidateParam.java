package com.teddy.jfinal.annotation;

import com.teddy.jfinal.common.Const;
import com.teddy.jfinal.entity.FileType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ValidateParam {
    /**
     * use only url parm
     * @return
     */
    int index() default -1;

    String value() default Const.DEFAULT_NONE;

    Class type() default String.class;

    String defaultValue() default Const.DEFAULT_NONE;

    /**
     * validate with regex only for Type String
     */
    String regex() default "";

    /**
     * validate parameter is not null
     */
    boolean required() default true;

    int minInt() default -1;

    int maxInt() default -1;

    /**
     * max length of string
     */
    int maxLen() default -1;

    /**
     * min length of String
     */
    int minLen() default -1;

    /**
     * Integer parameter's max value
     */
    double maxDouble() default -1;

    /**
     * Integer parameter's min value
     */
    double minDouble() default -1;

    long minLong() default -1;

    long maxLong() default -1;


    String minDate() default Const.DEFAULT_NONE;

    String maxDate() default Const.DEFAULT_NONE;

    String DateFormatter() default Const.FORMAT_DATE;

    /**
     * unit B
     *
     * @return
     */
    int maxSize() default -1;

    int minSize() default -1;

    FileType fileType() default FileType.NONE;
}
