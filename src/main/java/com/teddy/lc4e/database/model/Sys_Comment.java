package com.teddy.lc4e.database.model;

import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/09/27.
 */
@Model(value = "sys_comment", pk = {"id"})
public class Sys_Comment extends DBModel<Sys_Comment> {
    public static final Sys_Comment dao = new Sys_Comment();

}