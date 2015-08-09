package com.teddy.lc4e.core.database.model;

import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/08/07.
 */
@Model(value = "sys_common_variable", pk = {"id"})
public class Sys_Common_Variable extends DBModel<Sys_Common_Variable> {
    public static final Sys_Common_Variable dao = new Sys_Common_Variable().enhancer();

}