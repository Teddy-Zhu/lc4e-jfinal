package com.teddy.jfinal.exceptions;

/**
 * Created by teddy on 2015/7/19.
 */
public class ReflectException extends RuntimeException {

    private static final long serialVersionUID = 168076577791343245L;

    public ReflectException(String message) {
        super(message);
    }

    public ReflectException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReflectException() {
        super();
    }

    public ReflectException(Throwable cause) {
        super(cause);
    }
}
