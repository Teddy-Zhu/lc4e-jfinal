package com.teddy.lc4e.core.database.model;

import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/08/11.
 */
@Model(value = "sys_tag", pk = {"id"})
public class Sys_Tag extends DBModel<Sys_Tag> {
    public static final Sys_Tag dao = new Sys_Tag();

}