package com.teddy.lc4e.core.entity;

/**
 * Created by teddy on 2015/6/18.
 */
public class Data {

    private String name;
    private Object Data;

    public Data() {
    }

    public Data(String name, Object data) {
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
