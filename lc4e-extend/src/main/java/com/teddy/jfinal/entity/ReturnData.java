package com.teddy.jfinal.entity;

/**
 * Created by teddy on 2015/6/18.
 */
public class ReturnData {

    private String name;
    private Object Data;

    public ReturnData() {
    }

    public ReturnData(String name, Object data) {
        this.name = name;
        Data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getData() {
        return Data;
    }

    public void setData(Object data) {
        Data = data;
    }
}
