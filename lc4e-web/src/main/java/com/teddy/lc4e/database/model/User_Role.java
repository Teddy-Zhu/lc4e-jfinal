package com.teddy.lc4e.database.model;

import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/09/27.
 */
@Model(value = "user_role", pk = {"id"})
public class User_Role extends DBModel<User_Role> {
    public static final User_Role dao = new User_Role();

}