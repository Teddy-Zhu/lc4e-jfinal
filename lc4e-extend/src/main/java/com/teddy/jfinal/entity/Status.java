package com.teddy.jfinal.entity;

/**
 * Created by teddy on 2015/7/19.
 */
public enum Status {
    OK, ERROR;

    public Integer toInteger() {
        switch (this) {
            case OK:
                return 200;
            case ERROR:
                return 500;
            default:
                return 1000;
        }
    }
}
