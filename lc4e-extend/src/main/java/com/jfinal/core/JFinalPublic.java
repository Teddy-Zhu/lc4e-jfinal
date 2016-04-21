package com.jfinal.core;

import com.jfinal.config.Constants;
import com.jfinal.config.JFinalConfig;
import com.jfinal.handler.Handler;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.lang.reflect.Field;

/**
 * Created by teddyzhu on 15/11/19.
 */
public class JFinalPublic {

    public boolean init(JFinal me, JFinalConfig jfinalConfig, ServletContext servletContext) throws ServletException {

        return me.init(jfinalConfig, servletContext);
    }

    public Handler getHandler(JFinal me) {
        return me.getHandler();
    }

    public void stopPlugins(JFinal me) {
        me.stopPlugins();
    }

    public Constants getConstants() {
        return Config.getConstants();
    }
}
