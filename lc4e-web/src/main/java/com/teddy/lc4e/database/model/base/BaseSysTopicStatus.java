package com.teddy.lc4e.database.model.base;

import com.teddy.jfinal.interfaces.DBModel;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseSysTopicStatus<M extends BaseSysTopicStatus<M>> extends DBModel<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public static final String id = "sys_topic_status.id";

	public static final String ID = "ID";

	public void setAbbr(java.lang.String abbr) {
		set("abbr", abbr);
	}

	public java.lang.String getAbbr() {
		return get("abbr");
	}

	public static final String abbr = "sys_topic_status.abbr";

	public static final String ABBR = "ABBR";

	public void setName(java.lang.String name) {
		set("name", name);
	}

	public java.lang.String getName() {
		return get("name");
	}

	public static final String name = "sys_topic_status.name";

	public static final String NAME = "NAME";

	public void setPw(java.math.BigDecimal pw) {
		set("pw", pw);
	}

	public java.math.BigDecimal getPw() {
		return get("pw");
	}

	public static final String pw = "sys_topic_status.pw";

	public static final String PW = "PW";

	public void setIcon(java.lang.String icon) {
		set("icon", icon);
	}

	public java.lang.String getIcon() {
		return get("icon");
	}

	public static final String icon = "sys_topic_status.icon";

	public static final String ICON = "ICON";

	public void setCreateTime(java.util.Date createTime) {
		set("createTime", createTime);
	}

	public java.util.Date getCreateTime() {
		return get("createTime");
	}

	public static final String createtime = "sys_topic_status.createTime";

	public static final String CREATETIME = "CREATETIME";

	public void setUpdateTime(java.util.Date updateTime) {
		set("updateTime", updateTime);
	}

	public java.util.Date getUpdateTime() {
		return get("updateTime");
	}

	public static final String updatetime = "sys_topic_status.updateTime";

	public static final String UPDATETIME = "UPDATETIME";

	public static final String ALL_FIELDS = "sys_topic_status.*";
	public static final String TABLE_NAME = "sys_topic_status";}
