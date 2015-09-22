package com.teddy.lc4e.core.database.model;

import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/09/22.
 */
@Model(value = "user_message", pk = {"id"})
public class User_Message extends DBModel<User_Message> {
    public static final User_Message dao = new User_Message();

}