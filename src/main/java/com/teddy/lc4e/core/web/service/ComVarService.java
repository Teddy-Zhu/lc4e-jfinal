package com.teddy.lc4e.core.web.service;

import com.teddy.jfinal.annotation.Cache;
import com.teddy.jfinal.annotation.Service;
import com.teddy.lc4e.core.database.mapping.T_Sys_Common_Variable;
import com.teddy.lc4e.core.database.model.Sys_Common_Variable;

/**
 * Created by teddy on 2015/7/29.
 */
@Service
public class ComVarService {

    public static ComVarService service;


    @Cache(index = 0)
    public Sys_Common_Variable getComVarByName(String name) {
        Sys_Common_Variable commonVariable = Sys_Common_Variable.dao.findFirst(
                "select " + T_Sys_Common_Variable.ALL_FIELDS + " from " + T_Sys_Common_Variable.TABLE_NAME
                        + " Where " + T_Sys_Common_Variable.NAME + " = ?", name);
        return commonVariable;
    }

    @Cache(index = 0)
    public String getComVarValueByName(String name) {
        Sys_Common_Variable commonVariable = Sys_Common_Variable.dao.findFirst(
                "select " + T_Sys_Common_Variable.ALL_FIELDS + " from " + T_Sys_Common_Variable.TABLE_NAME
                        + " Where " + T_Sys_Common_Variable.NAME + " = ?", name);
        if (commonVariable != null) {
            return commonVariable.get(T_Sys_Common_Variable.value);
        } else {
            return null;
        }
    }
}
