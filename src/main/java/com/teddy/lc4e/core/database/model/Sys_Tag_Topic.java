package com.teddy.lc4e.core.database.model;

import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/08/11.
 */
@Model(value = "sys_tag_topic", pk = {"id"})
public class Sys_Tag_Topic extends DBModel<Sys_Tag_Topic> {
    public static final Sys_Tag_Topic dao = new Sys_Tag_Topic();

}