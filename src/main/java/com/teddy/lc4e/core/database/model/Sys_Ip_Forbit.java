package com.teddy.lc4e.core.database.model;

import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/08/10.
 */
@Model(value = "sys_ip_forbit", pk = {"id"})
public class Sys_Ip_Forbit extends DBModel<Sys_Ip_Forbit> {
    public static final Sys_Ip_Forbit dao = new Sys_Ip_Forbit();

}