package com.teddy.lc4e.util.plugins;

import com.jfinal.aop.Invocation;
import com.teddy.jfinal.annotation.SetComVar;
import com.teddy.jfinal.annotation.SetComVars;
import com.teddy.jfinal.common.Const;
import com.teddy.jfinal.exceptions.Lc4eAutoSetterException;
import com.teddy.jfinal.handler.resolve.AttributeKitI;
import com.teddy.jfinal.tools.ReflectTool;
import com.teddy.jfinal.tools.StringTool;
import com.teddy.lc4e.database.mapping.T_Sys_Common_Variable;
import com.teddy.lc4e.database.model.Sys_Common_Variable;
import com.teddy.lc4e.web.service.ComVarService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by teddy on 2015/10/25.
 */
public class AttributeKit implements AttributeKitI {
    @Override
    public void setComVar(SetComVar comVar, Invocation ai) throws Lc4eAutoSetterException {
        if (comVar == null) {
            return;
        }
        if (StringTool.equalEmpty(comVar.value())) {
            throw new Lc4eAutoSetterException("ComVar Field must be not empty!");
        }
        Sys_Common_Variable variable = ComVarService.service.getComVarByName(comVar.value());
        if (variable == null) {
            throw new Lc4eAutoSetterException("No ComVar Record Found in Database or Cache");
        } else {
            ai.getController().setAttr(Const.DEFAULT_NONE.equals(comVar.attrName()) ? comVar.value() : comVar.attrName(), ReflectTool.wrapperObject(comVar
                    .type(), variable.getStr(T_Sys_Common_Variable.value)));
        }
    }

    private Map<String, SetComVar> setComVarsBefore(SetComVars comVars) throws Lc4eAutoSetterException {
        Map<String, SetComVar> comVarMap = new HashMap<>();
        for (SetComVar comVar : comVars.value()) {
            if (StringTool.equalEmpty(comVar.value())) {
                throw new Lc4eAutoSetterException("ComVar Field must be not empty!");
            }
            comVarMap.put(comVar.value(), comVar);
        }
        return comVarMap;
    }

    @Override
    public void setComVars(SetComVars comVars, Invocation ai) throws Lc4eAutoSetterException {
        if (comVars == null) {
            return;
        }
        Map<String, SetComVar> comVarMap = setComVarsBefore(comVars);
        Set<String> keys = comVarMap.keySet();
        List<Sys_Common_Variable> variables = ComVarService.service.getComVarsByNames(keys);
        if (variables.size() != keys.size()) {
            throw new Lc4eAutoSetterException("mismatch vaiables");
        }
        if (variables.size() == 0) {
            throw new Lc4eAutoSetterException("No ComVar Record Found in Database or Cache");
        }
        for (Sys_Common_Variable variable : variables) {
            SetComVar comVar = comVarMap.get(variable.getStr(T_Sys_Common_Variable.name));
            ai.getController().setAttr(Const.DEFAULT_NONE.equals(comVar.attrName()) ? comVar.value() : comVar.attrName(), ReflectTool.wrapperObject(comVar
                    .type(), variable.getStr(T_Sys_Common_Variable.value)));
        }
    }

}
