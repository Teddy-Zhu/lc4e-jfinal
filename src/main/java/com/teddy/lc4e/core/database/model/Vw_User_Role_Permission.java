package com.teddy.lc4e.core.database.model;

import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/09/27.
 */
@Model(value = "vw_user_role_permission")
public class Vw_User_Role_Permission extends DBModel<Vw_User_Role_Permission> {
    public static final Vw_User_Role_Permission dao = new Vw_User_Role_Permission();

}