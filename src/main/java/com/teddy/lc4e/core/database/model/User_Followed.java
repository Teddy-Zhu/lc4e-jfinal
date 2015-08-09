package com.teddy.lc4e.core.database.model;

import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/08/07.
 */
@Model(value = "user_followed", pk = {"id"})
public class User_Followed extends DBModel<User_Followed> {
    public static final User_Followed dao = new User_Followed().enhancer();

}