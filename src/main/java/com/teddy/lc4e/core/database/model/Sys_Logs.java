package com.teddy.lc4e.core.database.model;

import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/07/29.
 */
@Model(value = "sys_logs", pk = {"id"})
public class Sys_Logs extends DBModel<Sys_Logs> {
    public static final Sys_Logs dao = new Sys_Logs().enhancer();

}