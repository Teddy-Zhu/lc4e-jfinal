package com.teddy.lc4e.core.database.model;

import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/09/23.
 */
@Model(value = "admin_log", pk = {"id"})
public class Admin_Log extends DBModel<Admin_Log> {
    public static final Admin_Log dao = new Admin_Log();

}