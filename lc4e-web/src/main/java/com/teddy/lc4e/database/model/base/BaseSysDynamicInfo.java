package com.teddy.lc4e.database.model.base;

import com.teddy.jfinal.interfaces.DBModel;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseSysDynamicInfo<M extends BaseSysDynamicInfo<M>> extends DBModel<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public static final String id = "sys_dynamic_info.id";

	public static final String ID = "ID";

	public void setUserId(java.lang.Integer userId) {
		set("userId", userId);
	}

	public java.lang.Integer getUserId() {
		return get("userId");
	}

	public static final String userid = "sys_dynamic_info.userId";

	public static final String USERID = "USERID";

	public void setInfo(java.lang.String info) {
		set("info", info);
	}

	public java.lang.String getInfo() {
		return get("info");
	}

	public static final String info = "sys_dynamic_info.info";

	public static final String INFO = "INFO";

	public void setCreateTime(java.util.Date createTime) {
		set("createTime", createTime);
	}

	public java.util.Date getCreateTime() {
		return get("createTime");
	}

	public static final String createtime = "sys_dynamic_info.createTime";

	public static final String CREATETIME = "CREATETIME";

	public void setUpdateTime(java.util.Date updateTime) {
		set("updateTime", updateTime);
	}

	public java.util.Date getUpdateTime() {
		return get("updateTime");
	}

	public static final String updatetime = "sys_dynamic_info.updateTime";

	public static final String UPDATETIME = "UPDATETIME";

	public static final String ALL_FIELDS = "sys_dynamic_info.*";
	public static final String TABLE_NAME = "sys_dynamic_info";}
