package com.teddy.lc4e.database.model;


import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/09/27.
 */
@Model(value = "user", pk = {"id"})
public class User extends DBModel<User> {
    public static final User dao = new User();

}