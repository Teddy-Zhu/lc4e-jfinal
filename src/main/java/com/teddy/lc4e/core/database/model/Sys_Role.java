package com.teddy.lc4e.core.database.model;

import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/09/23.
 */
@Model(value = "sys_role", pk = {"id"})
public class Sys_Role extends DBModel<Sys_Role> {
    public static final Sys_Role dao = new Sys_Role();

}