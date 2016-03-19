package com.teddy.lc4e.util.plugins;

import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.teddy.jfinal.annotation.ValidateComVar;
import com.teddy.jfinal.annotation.ValidateComVars;
import com.teddy.jfinal.exceptions.Lc4eException;
import com.teddy.jfinal.exceptions.Lc4eValidateException;
import com.teddy.jfinal.handler.resolve.ValidateKitI;
import com.teddy.jfinal.tools.ReflectTool;
import com.teddy.jfinal.tools.StringTool;
import com.teddy.lc4e.database.model.SysCommonVariable;
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
    public void resolveComVars(ValidateComVars comVars, Controller controller) throws Lc4eValidateException, Lc4eException {
        if (comVars == null) {
            return;
        }
        Map<String, ValidateComVar> comVarMap = validateComVarsBefore(comVars);
        Set<String> keys = comVarMap.keySet();
        List<SysCommonVariable> variables = ComVarService.service.getComVarsByNames(keys);
        if (variables.size() != keys.size()) {
            throw new Lc4eValidateException("May lost some ComVars");
        }
        if (variables.size() == 0) {
            throw new Lc4eValidateException("No ComVar Record Found in Database or Cache");
        }

        for (SysCommonVariable variable : variables) {
            ValidateComVar comVar = comVarMap.get(variable.getName());
            validate(comVar, variable);
        }

    }

    private void validate(ValidateComVar comVar, SysCommonVariable variable) throws Lc4eValidateException {
        Class type = ReflectTool.wrapper(comVar.type());
        if (comVar.validateEqual()) {
            if (!ReflectTool.wrapperObject(type, variable.getValue()).equals(ReflectTool.wrapperObject(type, comVar.value()))) {
                throw new Lc4eValidateException(variable.getError());
            }
        } else {
            if (ReflectTool.wrapperObject(type, variable.getValue()).equals(ReflectTool.wrapperObject(type, comVar.value()))) {
                throw new Lc4eValidateException(variable.getError());
            }
        }
    }

    @Override
    public void resolveComVar(ValidateComVar comVar, Controller controller) throws Lc4eValidateException {
        if (comVar == null) {
            return;
        }
        if (StringTool.equalEmpty(comVar.name()) || StringTool.equalEmpty(comVar.value())) {
            throw new Lc4eValidateException("Parameter field in invalid!");
        }
        SysCommonVariable variable = ComVarService.service.getComVarByName(comVar.name());
        if (variable == null) {
            throw new Lc4eValidateException("No ComVar Record Found in Database or Cache");
        } else {
            validate(comVar, variable);
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
