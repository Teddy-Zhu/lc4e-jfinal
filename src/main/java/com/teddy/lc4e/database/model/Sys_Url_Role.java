package com.teddy.lc4e.database.model;

import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/09/28.
 */
@Model(value = "sys_url_role", pk = {"id"})
public class Sys_Url_Role extends DBModel<Sys_Url_Role> {
    public static final Sys_Url_Role dao = new Sys_Url_Role();

}