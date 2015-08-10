package com.teddy.lc4e.core.database.model;

import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/08/10.
 */
@Model(value = "user_area_collected", pk = {"id"})
public class User_Area_Collected extends DBModel<User_Area_Collected> {
    public static final User_Area_Collected dao = new User_Area_Collected();

}