package com.teddy.jfinal.entity;

/**
 * Created by teddy on 2015/7/29.
 */
public enum FileType {
    NONE("none"),
    GIF("gif"),
    PNG("png"),
    ZIP("zip"),
    RAR("rar");

    private final String value;

    FileType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public String toString() {
        return value;
    }
}
