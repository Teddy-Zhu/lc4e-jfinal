package com.teddy.lc4e.web.service;

import com.teddy.jfinal.annotation.Cache;
import com.teddy.jfinal.annotation.Service;
import com.teddy.jfinal.tools.SQLTool;
import com.teddy.lc4e.database.mapping.T_Sys_Common_Variable;
import com.teddy.lc4e.database.model.Sys_Common_Variable;

import java.util.Collection;
import java.util.List;

/**
 * Created by teddy on 2015/7/29.
 */
@Service
public class ComVarService {

    public static ComVarService service;


    @Cache(index = 0)
    public Sys_Common_Variable getComVarByName(String name) {
        SQLTool sql = new SQLTool().select(T_Sys_Common_Variable.ALL_FIELDS)
                .from(T_Sys_Common_Variable.TABLE_NAME)
                .where(T_Sys_Common_Variable.NAME + " = ? ");
        sql.addParam(name);
        return Sys_Common_Variable.dao.findFirst(sql);
    }


    @Cache(index = 0)
    public List<Sys_Common_Variable> getComVarsByNames(String[] name) {
        return Sys_Common_Variable.dao.findInByColumn(name, T_Sys_Common_Variable.NAME);
    }

    @Cache(index = 0)
    public List<Sys_Common_Variable> getComVarsByNames(Collection<String> name) {
        return Sys_Common_Variable.dao.findInByColumn(name, T_Sys_Common_Variable.NAME);
    }

    @Cache(index = 0)
    public String getComVarValueByName(String name) {
        Sys_Common_Variable commonVariable = getComVarByName(name);
        if (commonVariable != null) {
            return commonVariable.getStr(T_Sys_Common_Variable.value);
        } else {
            return null;
        }
    }
}
