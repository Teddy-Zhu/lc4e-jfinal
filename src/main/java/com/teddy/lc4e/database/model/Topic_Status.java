package com.teddy.lc4e.database.model;

import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/09/27.
 */
@Model(value = "topic_status", pk = {"id"})
public class Topic_Status extends DBModel<Topic_Status> {
    public static final Topic_Status dao = new Topic_Status();

}