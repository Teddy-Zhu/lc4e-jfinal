package com.teddy.lc4e.core.database.model;

import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/09/23.
 */
@Model(value = "sys_actionKey_annotation", pk = {"id"})
public class Sys_ActionKey_Annotation extends DBModel<Sys_ActionKey_Annotation> {
    public static final Sys_ActionKey_Annotation dao = new Sys_ActionKey_Annotation();

}