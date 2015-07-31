package com.teddy.jfinal.Exceptions;

import com.teddy.lc4e.core.entity.Message;

import java.lang.annotation.Annotation;

/**
 * Created by teddy on 2015/7/29.
 */
public class ValidateException extends Exception {
    private static final long serialVersionUID = -7936021541494071097L;

    public ValidateException(String message) {
        super(message);
    }

    public ValidateException() {
    }


}
