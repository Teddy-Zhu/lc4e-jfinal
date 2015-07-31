package com.teddy.jfinal.entity;

import com.jfinal.core.Controller;
import com.teddy.jfinal.common.Const;

/**
 * Created by teddy on 2015/7/22.
 */
public class Route {

    public String controllerKey;
    public Class<? extends Controller> controllerClass;
    public String viewPath;

    public Route() {
    }

    public Route(String controllerKey, Class<? extends Controller> controllerClass) {
        this.controllerKey = controllerKey;
        this.controllerClass = controllerClass;
        this.viewPath= Const.DEFAULT_NONE;
    }

    public Route(String controllerKey, Class<? extends Controller> controllerClass, String viewPath) {
        this.controllerKey = controllerKey;
        this.controllerClass = controllerClass;
        this.viewPath = viewPath;
    }

    public String getControllerKey() {
        return controllerKey;
    }

    public void setControllerKey(String controllerKey) {
        this.controllerKey = controllerKey;
    }

    public Class<? extends Controller> getControllerClass() {
        return controllerClass;
    }

    public void setControllerClass(Class<? extends Controller> controllerClass) {
        this.controllerClass = controllerClass;
    }

    public String getViewPath() {
        return viewPath;
    }

    public void setViewPath(String viewPath) {
        this.viewPath = viewPath;
    }
}
