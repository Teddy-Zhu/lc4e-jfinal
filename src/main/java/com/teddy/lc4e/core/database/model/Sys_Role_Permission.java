package com.teddy.lc4e.core.database.model;

import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/09/27.
 */
@Model(value = "sys_role_permission", pk = {"id"})
public class Sys_Role_Permission extends DBModel<Sys_Role_Permission> {
    public static final Sys_Role_Permission dao = new Sys_Role_Permission();

}