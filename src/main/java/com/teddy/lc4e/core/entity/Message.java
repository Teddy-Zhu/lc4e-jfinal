package com.teddy.lc4e.core.entity;

import java.util.HashMap;
import java.util.Map;

public class Message {

	private boolean result;

	private String message;

	private Map<String,Object> data;

	public Message(boolean result, String message,Map<String,Object> data) {
		this.result = result;
		this.message = message;
		this.data = data;
	}

	public Message(boolean result, String message,Data... data) {
		this.result = result;
		this.message = message;
		this.data = ConvertToMap(data);
	}
	public Message(boolean result,Data... data) {
		this.result = result;
		this.message = "";
		this.data = ConvertToMap(data);
	}

	private Map<String,Object> ConvertToMap(Data... data){
		Map<String,Object> maps = new HashMap<String,Object>();
		for (int i = 0,len = data.length; i <len ; i++) {
			maps.put(data[i].getName(),data[i].getData());
		}
		return maps;
	}
	public Message(boolean result, String message) {
		this.result = result;
		this.message = message;
	}

	public Message(boolean result,Map<String,Object> data) {
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

	public Map<String,Object> getData() {
		return data;
	}

	public void setData(Map<String,Object> data) {
		this.data = data;
	}

}
