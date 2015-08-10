package com.teddy.lc4e.core.database.model;

import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/08/10.
 */
@Model(value = "sys_menu", pk = {"id"})
public class Sys_Menu extends DBModel<Sys_Menu> {
    public static final Sys_Menu dao = new Sys_Menu();

}