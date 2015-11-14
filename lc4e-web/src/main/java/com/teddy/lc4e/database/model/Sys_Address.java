package com.teddy.lc4e.database.model;

import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/09/27.
 */
@Model(value = "sys_address", pk = {"id"})
public class Sys_Address extends DBModel<Sys_Address> {
    public static final Sys_Address dao = new Sys_Address();

}