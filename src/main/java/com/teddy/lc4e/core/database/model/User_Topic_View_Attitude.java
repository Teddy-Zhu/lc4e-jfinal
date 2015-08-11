package com.teddy.lc4e.core.database.model;

import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/08/11.
 */
@Model(value = "user_topic_view_attitude", pk = {"id"})
public class User_Topic_View_Attitude extends DBModel<User_Topic_View_Attitude> {
    public static final User_Topic_View_Attitude dao = new User_Topic_View_Attitude();

}