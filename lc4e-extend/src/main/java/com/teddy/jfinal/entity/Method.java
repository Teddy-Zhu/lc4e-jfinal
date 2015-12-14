package com.teddy.jfinal.entity;

import com.teddy.jfinal.tools.StringTool;

/**
 * Created by teddy on 2015/7/21.
 */
public enum Method {
    GET(1),
    POST(2),
    DELETE(3),
    UPDATE(4);

    private int value;

    Method(int value) {
        this.value = value;
    }

    public Integer toInteger() {
        return value;
    }
}
