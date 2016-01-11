package com.teddy.jfinal.handler.resolve;

import com.teddy.jfinal.annotation.*;
import com.teddy.jfinal.exceptions.Lc4eAutoSetterException;
import com.teddy.jfinal.exceptions.Lc4eException;
import com.teddy.jfinal.tools.CustomTool;
import com.teddy.jfinal.tools.ReflectTool;
import com.teddy.jfinal.tools.WebTool;

import java.lang.reflect.Method;

/**
 * Created by teddy on 2015/8/2.
 */
public class AttributeKit {

    public static void setUIDatas(SetUIDatas uiDatas, com.jfinal.core.Controller controller) throws Lc4eException, Lc4eAutoSetterException {
        if (uiDatas == null) {
            return;
        }
        for (int i = 0, len = uiDatas.value().length; i < len; i++) {
            setUIData(uiDatas.value()[i], controller);
        }

    }

    public static void setUIData(SetUIData uiData, com.jfinal.core.Controller controller) throws Lc4eException, Lc4eAutoSetterException {
        Object returnValue;
        Method method = ReflectTool.getMethodByClassAndName(uiData.methodClass(), uiData.methodName());
        if (method == null) {
            throw new Lc4eAutoSetterException("The method [" + uiData.methodName() + "] can not found in Class [" + uiData.methodClass().toString() + "]");
        }
        try {
            Object obj = uiData.methodClass().newInstance();
            if (method.getParameterCount() > 0) {
                returnValue = method.invoke(uiData.methodClass().isAnnotationPresent(Service.class) ? CustomTool.custom(obj) : obj, controller);
            } else {
                returnValue = method.invoke(uiData.methodClass().isAnnotationPresent(Service.class) ? CustomTool.custom(obj) : obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Lc4eAutoSetterException("Call method [" + uiData.methodName() + "] failed");
        }
        if (returnValue != null) {
            controller.setAttr(uiData.attrName(), returnValue);
        } else {
            throw new Lc4eAutoSetterException("Auto Set Attribute [" + uiData.attrName() + "] failed!");
        }
    }


    public static void setPJAX(SetPJAX setPJAX, com.jfinal.core.Controller controller) {
        controller.setAttr(setPJAX.value(), WebTool.isPJAX(controller.getRequest()));
    }

    public static void setAJAX(SetAJAX setAJAX, com.jfinal.core.Controller controller) {
        controller.setAttr(setAJAX.value(), WebTool.isAJAX(controller.getRequest()));
    }
}
