package com.teddy.jfinal.exceptions;

/**
 * Created by teddy on 2015/9/11.
 */
public class Lc4eApplicationException extends Exception {

    private static final long serialVersionUID = 8300447487958124998L;

    public Lc4eApplicationException() {
    }

    public Lc4eApplicationException(String message) {
        super(message);
    }

    public Lc4eApplicationException(Throwable cause) {
        super(cause);
    }
}
