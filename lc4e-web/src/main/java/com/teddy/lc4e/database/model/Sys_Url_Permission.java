package com.teddy.lc4e.database.model;

import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/09/28.
 */
@Model(value = "sys_url_permission", pk = {"id"})
public class Sys_Url_Permission extends DBModel<Sys_Url_Permission> {
    public static final Sys_Url_Permission dao = new Sys_Url_Permission();

}