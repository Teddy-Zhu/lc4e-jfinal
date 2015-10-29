package com.teddy.lc4e.database.model;

import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/09/27.
 */
@Model(value = "vw_topic_status_pw")
public class Vw_Topic_Status_Pw extends DBModel<Vw_Topic_Status_Pw> {
    public static final Vw_Topic_Status_Pw dao = new Vw_Topic_Status_Pw();

}