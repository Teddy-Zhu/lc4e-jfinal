package com.teddy.jfinal.handler.support;

import com.jfinal.aop.Invocation;
import com.teddy.jfinal.annotation.Service;
import com.teddy.jfinal.annotation.SetComVar;
import com.teddy.jfinal.annotation.SetUIData;
import com.teddy.jfinal.annotation.SetUIDatas;
import com.teddy.jfinal.exceptions.AutoSetterException;
import com.teddy.jfinal.exceptions.Lc4eException;
import com.teddy.jfinal.tools.CustomTool;
import com.teddy.jfinal.tools.ReflectTool;
import com.teddy.jfinal.tools.StringTool;
import com.teddy.lc4e.core.database.mapping.T_Sys_Common_Variable;
import com.teddy.lc4e.core.database.model.Sys_Common_Variable;
import com.teddy.lc4e.core.web.service.ComVarService;

import java.lang.reflect.Method;

/**
 * Created by teddy on 2015/8/2.
 */
public class AttributeKit {
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
        if (comVar.value().length == 0) {
            throw new AutoSetterException("ComVar Field must be not empty!");
        }
        if (comVar.attrName().length != 0 && comVar.value().length != comVar.attrName().length) {
            throw new AutoSetterException("ComVar AttrNames' length must be equal with field's length");
        }
        for (int i = 0, len = comVar.value().length; i < len; i++) {
            Sys_Common_Variable variable = ComVarService.service.getComVarByName(comVar.value()[i]);
            if (variable == null) {
                throw new AutoSetterException("No ComVar Record Found in Database or Cache");
            } else {
                ai.getController().setAttr(comVar.attrName().length == 0 ? comVar.value()[i] : StringTool.equalEmpty(comVar.attrName()[i]) ?
                        comVar.value()[i] : comVar.attrName()[i], variable.get(T_Sys_Common_Variable.value));
            }
        }
    }
}
