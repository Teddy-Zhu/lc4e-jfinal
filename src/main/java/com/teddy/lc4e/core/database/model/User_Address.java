package com.teddy.lc4e.core.database.model;

import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/08/07.
 */
@Model(value = "user_address", pk = {"id"})
public class User_Address extends DBModel<User_Address> {
    public static final User_Address dao = new User_Address().enhancer();

}