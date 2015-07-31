package com.teddy.lc4e.core.database.model;

import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/07/29.
 */
@Model(value = "user_topic_collected", pk = {"id"})
public class User_Topic_Collected extends DBModel<User_Topic_Collected> {
    public static final User_Topic_Collected dao = new User_Topic_Collected().enhancer();

}