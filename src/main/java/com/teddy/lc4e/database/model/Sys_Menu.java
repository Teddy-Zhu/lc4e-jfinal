package com.teddy.lc4e.database.model;

import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/09/27.
 */
@Model(value = "sys_menu", pk = {"id"})
public class Sys_Menu extends DBModel<Sys_Menu> {
    public static final Sys_Menu dao = new Sys_Menu();

}