package com.teddy.lc4e.database.model;

import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/09/27.
 */
@Model(value = "sys_area_status", pk = {"id"})
public class Sys_Area_Status extends DBModel<Sys_Area_Status> {
    public static final Sys_Area_Status dao = new Sys_Area_Status();

}