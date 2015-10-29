package com.teddy.lc4e.database.model;

import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/09/27.
 */
@Model(value = "user_tag", pk = {"id"})
public class User_Tag extends DBModel<User_Tag> {
    public static final User_Tag dao = new User_Tag();

}