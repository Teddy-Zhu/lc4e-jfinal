package com.teddy.jfinal.entity;

/**
 * Created by teddy on 2015/7/21.
 */
public enum Method {
    GET,
    POST,
    DELETE,
    UPDATE;

    public Integer toInteger() {
        switch (this) {
            case GET:
                return 1;
            case POST:
                return 2;
            case DELETE:
                return 3;
            case UPDATE:
                return 4;
            default:
                return 999;
        }
    }
}
