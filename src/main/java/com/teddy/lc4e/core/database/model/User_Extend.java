package com.teddy.lc4e.core.database.model;

import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/09/23.
 */
@Model(value = "user_extend", pk = {"id"})
public class User_Extend extends DBModel<User_Extend> {
    public static final User_Extend dao = new User_Extend();

}