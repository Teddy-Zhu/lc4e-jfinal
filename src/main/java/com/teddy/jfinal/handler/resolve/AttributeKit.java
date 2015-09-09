package com.teddy.jfinal.handler.resolve;

import com.jfinal.aop.Invocation;
import com.jfinal.log.Logger;
import com.teddy.jfinal.annotation.*;
import com.teddy.jfinal.common.Const;
import com.teddy.jfinal.exceptions.AutoSetterException;
import com.teddy.jfinal.exceptions.Lc4eException;
import com.teddy.jfinal.tools.CustomTool;
import com.teddy.jfinal.tools.ReflectTool;
import com.teddy.jfinal.tools.StringTool;
import com.teddy.jfinal.tools.WebTool;
import com.teddy.lc4e.core.database.mapping.T_Sys_Common_Variable;
import com.teddy.lc4e.core.database.model.Sys_Common_Variable;
import com.teddy.lc4e.core.web.service.ComVarService;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.jfinal.log.Logger.getLogger;

/**
 * Created by teddy on 2015/8/2.
 */
public class AttributeKit {

    private static final Logger log = getLogger(AttributeKit.class);

    public static void setUIDatas(SetUIDatas uiDatas, Invocation ai) throws Lc4eException, AutoSetterException {
        if (uiDatas == null) {
            return;
        }
        for (int i = 0, len = uiDatas.value().length; i < len; i++) {
            setUIData(uiDatas.value()[i], ai);
        }

    }

    public static void setUIData(SetUIData uiData, Invocation ai) throws Lc4eException, AutoSetterException {
        if (uiData == null) {
            return;
        }
        Object returnValue;
        Method method = ReflectTool.getMethodByClassAndName(uiData.methodClass(), uiData.methodName());
        if (method == null) {
            throw new AutoSetterException("The method [" + uiData.methodName() + "] can not found in Class [" + uiData.methodClass().toString() + "]");
        }
        try {
            Object obj = uiData.methodClass().newInstance();
            if (method.getParameterCount() > 0) {
                returnValue = method.invoke(uiData.methodClass().isAnnotationPresent(Service.class) ? CustomTool.custom(obj) : obj, ai);
            } else {
                returnValue = method.invoke(uiData.methodClass().isAnnotationPresent(Service.class) ? CustomTool.custom(obj) : obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AutoSetterException("Call method [" + uiData.methodName() + "] failed");
        }
        if (returnValue != null) {
            ai.getController().setAttr(uiData.attrName(), returnValue);
        } else {
            throw new AutoSetterException("Auto Set Attribute [" + uiData.attrName() + "] failed!");
        }
    }

    public static void setComVar(SetComVar comVar, Invocation ai) throws AutoSetterException {
        if (comVar == null) {
            return;
        }
        if (StringTool.equalEmpty(comVar.value())) {
            throw new AutoSetterException("ComVar Field must be not empty!");
        }
        Sys_Common_Variable variable = ComVarService.service.getComVarByName(comVar.value());
        if (variable == null) {
            throw new AutoSetterException("No ComVar Record Found in Database or Cache");
        } else {
            ai.getController().setAttr(Const.DEFAULT_NONE.equals(comVar.attrName()) ? comVar.value() : comVar.attrName(), ReflectTool.wrapperObject(comVar
                    .type(), variable.getStr(T_Sys_Common_Variable.value)));
        }
    }

    public static Map<String, SetComVar> setComVarsBefore(SetComVars comVars) throws AutoSetterException {
        Map<String, SetComVar> comVarMap = new HashMap<>();
        for (SetComVar comVar : comVars.value()) {
            if (StringTool.equalEmpty(comVar.value())) {
                throw new AutoSetterException("ComVar Field must be not empty!");
            }
            comVarMap.put(comVar.value(), comVar);
        }
        return comVarMap;
    }

    public static void setComVars(SetComVars comVars, Invocation ai) throws AutoSetterException {
        if (comVars == null) {
            return;
        }
        Map<String, SetComVar> comVarMap = setComVarsBefore(comVars);
        Set<String> keys = comVarMap.keySet();
        List<Sys_Common_Variable> variables = ComVarService.service.getComVarsByNames(keys);
        if (variables.size() != keys.size()) {
            log.warn("May lost some ComVars");
        }
        if (variables.size() == 0) {
            throw new AutoSetterException("No ComVar Record Found in Database or Cache");
        }
        for (Sys_Common_Variable variable : variables) {
            SetComVar comVar = comVarMap.get(variable.getStr(T_Sys_Common_Variable.name));
            ai.getController().setAttr(Const.DEFAULT_NONE.equals(comVar.attrName()) ? comVar.value() : comVar.attrName(), ReflectTool.wrapperObject(comVar
                    .type(), variable.getStr(T_Sys_Common_Variable.value)));
        }
    }

    public static void setPJAX(SetPJAX setPJAX, Invocation ai) {
        if (setPJAX == null) {
            return;
        }
        ai.getController().setAttr(setPJAX.value(), WebTool.isPJAX(ai.getController().getRequest()));
    }

    public static void setAJAX(SetAJAX setAJAX, Invocation ai) {
        if (setAJAX == null) {
            return;
        }
        ai.getController().setAttr(setAJAX.value(), WebTool.isAJAX(ai.getController().getRequest()));
    }
}
