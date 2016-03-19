package com.teddy.lc4e.web.service;

import com.teddy.jfinal.annotation.Cache;
import com.teddy.jfinal.annotation.Service;
import com.teddy.jfinal.tools.SQLTool;
import com.teddy.lc4e.database.model.SysCommonVariable;

import java.util.Collection;
import java.util.List;

/**
 * Created by teddy on 2015/7/29.
 */
@Service
public class ComVarService {

    public static ComVarService service;


    @Cache(index = 0)
    public SysCommonVariable getComVarByName(String name) {
        SQLTool sql = new SQLTool().select(SysCommonVariable.ALL_FIELDS)
                .from(SysCommonVariable.TABLE_NAME)
                .where(SysCommonVariable.name + " = ? ");
        sql.appendParam(name);
        return SysCommonVariable.dao.findFirst(sql);
    }


    @Cache(index = 0)
    public List<SysCommonVariable> getComVarsByNames(String[] name) {
        return SysCommonVariable.dao.findInByColumn(name, SysCommonVariable.name);
    }

    @Cache(index = 0)
    public List<SysCommonVariable> getComVarsByNames(Collection<String> name) {
        return SysCommonVariable.dao.findInByColumn(name, SysCommonVariable.name);
    }

    @Cache(index = 0)
    public String getComVarValueByName(String name) {
        SysCommonVariable commonVariable = getComVarByName(name);
        if (commonVariable != null) {
            return commonVariable.getValue();
        } else {
            return null;
        }
    }
}
