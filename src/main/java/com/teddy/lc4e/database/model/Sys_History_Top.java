package com.teddy.lc4e.database.model;

import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/09/27.
 */
@Model(value = "sys_history_top", pk = {"id"})
public class Sys_History_Top extends DBModel<Sys_History_Top> {
    public static final Sys_History_Top dao = new Sys_History_Top();

}