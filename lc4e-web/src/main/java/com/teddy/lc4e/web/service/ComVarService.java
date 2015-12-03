package com.teddy.lc4e.web.service;

import com.teddy.jfinal.annotation.Cache;
import com.teddy.jfinal.annotation.Service;
import com.teddy.jfinal.tools.SQLTool;
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
        SQLTool sql = new SQLTool().select(Sys_Common_Variable.ALL_FIELDS)
                .from(Sys_Common_Variable.TABLE_NAME)
                .where(Sys_Common_Variable.F_NAME + " = ? ");
        sql.addParam(name);
        return Sys_Common_Variable.dao.findFirst(sql);
    }


    @Cache(index = 0)
    public List<Sys_Common_Variable> getComVarsByNames(String[] name) {
        return Sys_Common_Variable.dao.findInByColumn(name, Sys_Common_Variable.F_NAME);
    }

    @Cache(index = 0)
    public List<Sys_Common_Variable> getComVarsByNames(Collection<String> name) {
        return Sys_Common_Variable.dao.findInByColumn(name, Sys_Common_Variable.F_NAME);
    }

    @Cache(index = 0)
    public String getComVarValueByName(String name) {
        Sys_Common_Variable commonVariable = getComVarByName(name);
        if (commonVariable != null) {
            return commonVariable.getStr(Sys_Common_Variable.S_VALUE);
        } else {
            return null;
        }
    }
}
