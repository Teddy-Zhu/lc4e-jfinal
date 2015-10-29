package com.teddy.lc4e.database.model;

import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/09/27.
 */
@Model(value = "sys_topic", pk = {"id"})
public class Sys_Topic extends DBModel<Sys_Topic> {
    public static final Sys_Topic dao = new Sys_Topic();

}