package com.teddy.lc4e.database.model;

import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/09/27.
 */
@Model(value = "sys_operate_type", pk = {"id"})
public class Sys_Operate_Type extends DBModel<Sys_Operate_Type> {
    public static final Sys_Operate_Type dao = new Sys_Operate_Type();

}