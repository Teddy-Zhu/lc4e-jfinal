package com.teddy.lc4e.core.database.model;

import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/09/22.
 */
@Model(value = "user_topic_blocked", pk = {"id"})
public class User_Topic_Blocked extends DBModel<User_Topic_Blocked> {
    public static final User_Topic_Blocked dao = new User_Topic_Blocked();

}