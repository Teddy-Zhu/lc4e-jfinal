package com.teddy.lc4e.core.database.model;

import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/08/11.
 */
@Model(value = "sys_log", pk = {"id"})
public class Sys_Log extends DBModel<Sys_Log> {
    public static final Sys_Log dao = new Sys_Log();

}