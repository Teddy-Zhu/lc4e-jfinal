package com.teddy.lc4e.core.database.model;

import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/09/27.
 */
@Model(value = "sys_job", pk = {"id"})
public class Sys_Job extends DBModel<Sys_Job> {
    public static final Sys_Job dao = new Sys_Job();

}