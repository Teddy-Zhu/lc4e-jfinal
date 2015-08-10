package com.teddy.lc4e.core.database.model;

import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/08/10.
 */
@Model(value = "sys_topic", pk = {"id"})
public class Sys_Topic extends DBModel<Sys_Topic> {
    public static final Sys_Topic dao = new Sys_Topic();

}