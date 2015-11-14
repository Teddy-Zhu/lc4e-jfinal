package com.teddy.lc4e.util.plugins;

import com.jfinal.aop.Invocation;
import com.teddy.jfinal.annotation.ValidateComVar;
import com.teddy.jfinal.annotation.ValidateComVars;
import com.teddy.jfinal.exceptions.Lc4eException;
import com.teddy.jfinal.exceptions.Lc4eValidateException;
import com.teddy.jfinal.handler.resolve.ValidateKitI;
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
public class ValidateKit implements ValidateKitI {

    @Override
    public void resolveComVars(ValidateComVars comVars, Invocation invocation) throws Lc4eValidateException, Lc4eException {
        if (comVars == null) {
            return;
        }
        Map<String, ValidateComVar> comVarMap = validateComVarsBefore(comVars);
        Set<String> keys = comVarMap.keySet();
        List<Sys_Common_Variable> variables = ComVarService.service.getComVarsByNames(keys);
        if (variables.size() != keys.size()) {
            throw new Lc4eValidateException("May lost some ComVars");
        }
        if (variables.size() == 0) {
            throw new Lc4eValidateException("No ComVar Record Found in Database or Cache");
        }

        for (Sys_Common_Variable variable : variables) {
            ValidateComVar comVar = comVarMap.get(variable.getStr(T_Sys_Common_Variable.name));
            Class type = ReflectTool.wrapper(comVar.type());
            if (!ReflectTool.wrapperObject(type, variable.getStr(T_Sys_Common_Variable.value)).equals(ReflectTool.wrapperObject(type, comVar.value()))) {
                throw new Lc4eValidateException(variable.get(T_Sys_Common_Variable.error));
            }
        }

    }

    @Override
    public void resolveComVar(ValidateComVar comVar, Invocation invocation) throws Lc4eValidateException {
        if (comVar == null) {
            return;
        }
        if (StringTool.equalEmpty(comVar.name()) || StringTool.equalEmpty(comVar.value())) {
            throw new Lc4eValidateException("Parameter field in invalid!");
        }
        Sys_Common_Variable variable = ComVarService.service.getComVarByName(comVar.name());
        if (variable == null) {
            throw new Lc4eValidateException("No ComVar Record Found in Database or Cache");
        } else {
            Class type = ReflectTool.wrapper(comVar.type());
            if (!ReflectTool.wrapperObject(type, variable.getStr(T_Sys_Common_Variable.value)).equals(ReflectTool.wrapperObject(type, comVar.value()))) {
                throw new Lc4eValidateException(variable.get(T_Sys_Common_Variable.error));
            }
        }
    }

    private Map<String, ValidateComVar> validateComVarsBefore(ValidateComVars comVars) throws Lc4eException {
        Map<String, ValidateComVar> comVarMap = new HashMap<>();
        for (ValidateComVar comVar : comVars.value()) {
            if (StringTool.equalEmpty(comVar.value())) {
                throw new Lc4eException("ComVar Field must be not empty!");
            }
            comVarMap.put(comVar.value(), comVar);
        }
        return comVarMap;
    }

}
