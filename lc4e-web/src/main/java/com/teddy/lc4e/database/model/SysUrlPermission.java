package com.teddy.lc4e.database.model;

import com.teddy.jfinal.annotation.Model;
import com.teddy.lc4e.database.model.base.BaseSysUrlPermission;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
@Model(value = "sys_url_permission", pk = {"id"})
public class SysUrlPermission extends BaseSysUrlPermission<SysUrlPermission> {
	public static final SysUrlPermission dao = new SysUrlPermission();
}