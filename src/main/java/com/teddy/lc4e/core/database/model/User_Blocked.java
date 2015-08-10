package com.teddy.lc4e.core.database.model;

import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/08/10.
 */
@Model(value = "user_blocked", pk = {"id"})
public class User_Blocked extends DBModel<User_Blocked> {
    public static final User_Blocked dao = new User_Blocked();

}