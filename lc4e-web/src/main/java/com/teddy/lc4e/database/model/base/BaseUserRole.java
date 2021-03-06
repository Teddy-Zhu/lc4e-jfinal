package com.teddy.lc4e.database.model.base;

import com.teddy.jfinal.interfaces.DBModel;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseUserRole<M extends BaseUserRole<M>> extends DBModel<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public static final String id = "user_role.id";

	public static final String ID = "ID";

	public void setUserId(java.lang.Integer userId) {
		set("userId", userId);
	}

	public java.lang.Integer getUserId() {
		return get("userId");
	}

	public static final String userid = "user_role.userId";

	public static final String USERID = "USERID";

	public void setRoleId(java.lang.Integer roleId) {
		set("roleId", roleId);
	}

	public java.lang.Integer getRoleId() {
		return get("roleId");
	}

	public static final String roleid = "user_role.roleId";

	public static final String ROLEID = "ROLEID";

	public void setEndTime(java.util.Date endTime) {
		set("endTime", endTime);
	}

	public java.util.Date getEndTime() {
		return get("endTime");
	}

	public static final String endtime = "user_role.endTime";

	public static final String ENDTIME = "ENDTIME";

	public void setCreateTime(java.util.Date createTime) {
		set("createTime", createTime);
	}

	public java.util.Date getCreateTime() {
		return get("createTime");
	}

	public static final String createtime = "user_role.createTime";

	public static final String CREATETIME = "CREATETIME";

	public void setUpdateTime(java.util.Date updateTime) {
		set("updateTime", updateTime);
	}

	public java.util.Date getUpdateTime() {
		return get("updateTime");
	}

	public static final String updatetime = "user_role.updateTime";

	public static final String UPDATETIME = "UPDATETIME";

	public static final String ALL_FIELDS = "user_role.*";
	public static final String TABLE_NAME = "user_role";}
