package com.teddy.lc4e.core.database.model;

import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/09/27.
 */
@Model(value = "topic_tag", pk = {"id"})
public class Topic_Tag extends DBModel<Topic_Tag> {
    public static final Topic_Tag dao = new Topic_Tag();

}