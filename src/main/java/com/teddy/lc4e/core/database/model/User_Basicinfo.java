package com.teddy.lc4e.core.database.model;

import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/09/23.
 */
@Model(value = "user_basicinfo", pk = {"id"})
public class User_Basicinfo extends DBModel<User_Basicinfo> {
    public static final User_Basicinfo dao = new User_Basicinfo();

}