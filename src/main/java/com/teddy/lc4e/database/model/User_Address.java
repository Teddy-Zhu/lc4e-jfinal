package com.teddy.lc4e.database.model;

import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/09/27.
 */
@Model(value = "user_address", pk = {"id"})
public class User_Address extends DBModel<User_Address> {
    public static final User_Address dao = new User_Address();

}