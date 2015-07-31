package com.teddy.lc4e.core.database.model;

import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/07/29.
 */
@Model(value = "sys_topic_comment_status", pk = {"id"})
public class Sys_Topic_Comment_Status extends DBModel<Sys_Topic_Comment_Status> {
    public static final Sys_Topic_Comment_Status dao = new Sys_Topic_Comment_Status().enhancer();

}