package com.teddy.lc4e.core.database.model;

import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/08/07.
 */
@Model(value = "sys_dynamic_info", pk = {"id"})
public class Sys_Dynamic_Info extends DBModel<Sys_Dynamic_Info> {
    public static final Sys_Dynamic_Info dao = new Sys_Dynamic_Info().enhancer();

}