package com.teddy.lc4e.core.database.model;

import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/09/23.
 */
@Model(value = "user_tag", pk = {"id"})
public class User_Tag extends DBModel<User_Tag> {
    public static final User_Tag dao = new User_Tag();

}