package com.teddy.lc4e.core.database.model;

import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/09/23.
 */
@Model(value = "sys_annotation_instance", pk = {"id"})
public class Sys_Annotation_Instance extends DBModel<Sys_Annotation_Instance> {
    public static final Sys_Annotation_Instance dao = new Sys_Annotation_Instance();

}