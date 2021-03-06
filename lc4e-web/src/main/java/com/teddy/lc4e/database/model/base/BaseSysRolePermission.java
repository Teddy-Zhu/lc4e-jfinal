package com.teddy.lc4e.database.model.base;

import com.teddy.jfinal.interfaces.DBModel;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseSysRolePermission<M extends BaseSysRolePermission<M>> extends DBModel<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public static final String id = "sys_role_permission.id";

	public static final String ID = "ID";

	public void setRoleId(java.lang.Integer roleId) {
		set("roleId", roleId);
	}

	public java.lang.Integer getRoleId() {
		return get("roleId");
	}

	public static final String roleid = "sys_role_permission.roleId";

	public static final String ROLEID = "ROLEID";

	public void setPermissionId(java.lang.Integer permissionId) {
		set("permissionId", permissionId);
	}

	public java.lang.Integer getPermissionId() {
		return get("permissionId");
	}

	public static final String permissionid = "sys_role_permission.permissionId";

	public static final String PERMISSIONID = "PERMISSIONID";

	public void setCreateTime(java.util.Date createTime) {
		set("createTime", createTime);
	}

	public java.util.Date getCreateTime() {
		return get("createTime");
	}

	public static final String createtime = "sys_role_permission.createTime";

	public static final String CREATETIME = "CREATETIME";

	public void setUpdateTime(java.util.Date updateTime) {
		set("updateTime", updateTime);
	}

	public java.util.Date getUpdateTime() {
		return get("updateTime");
	}

	public static final String updatetime = "sys_role_permission.updateTime";

	public static final String UPDATETIME = "UPDATETIME";

	public static final String ALL_FIELDS = "sys_role_permission.*";
	public static final String TABLE_NAME = "sys_role_permission";}
