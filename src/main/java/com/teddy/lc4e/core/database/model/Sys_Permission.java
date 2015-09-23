package com.teddy.lc4e.core.database.model;

import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/09/23.
 */
@Model(value = "sys_permission", pk = {"id"})
public class Sys_Permission extends DBModel<Sys_Permission> {
    public static final Sys_Permission dao = new Sys_Permission();

}