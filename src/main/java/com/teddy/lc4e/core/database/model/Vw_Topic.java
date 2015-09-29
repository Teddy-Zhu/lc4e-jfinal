package com.teddy.lc4e.core.database.model;

import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/09/27.
 */
@Model(value = "vw_topic")
public class Vw_Topic extends DBModel<Vw_Topic> {
    public static final Vw_Topic dao = new Vw_Topic();

}