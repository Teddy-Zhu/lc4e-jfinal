package com.teddy.lc4e.core.database.model;

import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/09/22.
 */
@Model(value = "sys_comment_attach", pk = {"id"})
public class Sys_Comment_Attach extends DBModel<Sys_Comment_Attach> {
    public static final Sys_Comment_Attach dao = new Sys_Comment_Attach();

}