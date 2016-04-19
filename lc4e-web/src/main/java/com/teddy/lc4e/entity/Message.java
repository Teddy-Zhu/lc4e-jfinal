package com.teddy.lc4e.entity;

import com.teddy.jfinal.entity.ReturnData;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Message implements Serializable {

    private static final long serialVersionUID = 9061175142113661835L;

    private boolean result;

    private String message;

    private Map<String, Object> data;

    public Message(boolean result, String message, Map<String, Object> data) {
        this.result = result;
        this.message = message;
        this.data = data;
    }

    public Message(boolean result, String message, ReturnData... data) {
        this.result = result;
        this.message = message;
        this.data = ConvertToMap(data);
    }

    public Message(boolean result, ReturnData... data) {
        this.result = result;
        this.message = "";
        this.data = ConvertToMap(data);
    }

    private Map<String, Object> ConvertToMap(ReturnData... data) {
        Map<String, Object> maps = new HashMap<String, Object>();
        for (ReturnData d : data) {
            maps.put(d.getName(), d.getData());
        }
        return maps;
    }

    public Message(boolean result, String message) {
        this.result = result;
        this.message = message;
    }

    public Message(boolean result, Map<String, Object> data) {
        this.result = result;
        this.data = data;
    }

    public Message(String message) {
        this.result = false;
        this.message = message;
        this.data = null;
    }

    public Message() {
        this.result = false;
        this.message = "";
        this.data = null;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

}
