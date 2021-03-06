package com.teddy.lc4e.database.model.base;

import com.teddy.jfinal.interfaces.DBModel;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseUserFollowed<M extends BaseUserFollowed<M>> extends DBModel<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public static final String id = "user_followed.id";

	public static final String ID = "ID";

	public void setUserId(java.lang.Integer userId) {
		set("userId", userId);
	}

	public java.lang.Integer getUserId() {
		return get("userId");
	}

	public static final String userid = "user_followed.userId";

	public static final String USERID = "USERID";

	public void setFollowedUserId(java.lang.Integer followedUserId) {
		set("followedUserId", followedUserId);
	}

	public java.lang.Integer getFollowedUserId() {
		return get("followedUserId");
	}

	public static final String followeduserid = "user_followed.followedUserId";

	public static final String FOLLOWEDUSERID = "FOLLOWEDUSERID";

	public void setCreateTime(java.util.Date createTime) {
		set("createTime", createTime);
	}

	public java.util.Date getCreateTime() {
		return get("createTime");
	}

	public static final String createtime = "user_followed.createTime";

	public static final String CREATETIME = "CREATETIME";

	public void setUpdateTime(java.util.Date updateTime) {
		set("updateTime", updateTime);
	}

	public java.util.Date getUpdateTime() {
		return get("updateTime");
	}

	public static final String updatetime = "user_followed.updateTime";

	public static final String UPDATETIME = "UPDATETIME";

	public static final String ALL_FIELDS = "user_followed.*";
	public static final String TABLE_NAME = "user_followed";}
