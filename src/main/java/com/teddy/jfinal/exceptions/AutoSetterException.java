package com.teddy.jfinal.exceptions;

/**
 * Created by teddy on 2015/8/2.
 */
public class AutoSetterException extends Exception {

    private static final long serialVersionUID = 2795672073610135174L;

    public AutoSetterException() {
    }

    public AutoSetterException(String message) {
        super(message);
    }
}
