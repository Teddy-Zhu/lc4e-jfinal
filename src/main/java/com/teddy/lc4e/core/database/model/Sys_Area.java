package com.teddy.lc4e.core.database.model;

import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/09/23.
 */
@Model(value = "sys_area", pk = {"id"})
public class Sys_Area extends DBModel<Sys_Area> {
    public static final Sys_Area dao = new Sys_Area();

}