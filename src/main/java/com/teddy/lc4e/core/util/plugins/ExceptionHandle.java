package com.teddy.lc4e.core.util.plugins;

import com.jfinal.aop.Invocation;
import com.teddy.jfinal.annotation.ExceptionHandler;
import com.teddy.jfinal.annotation.ExceptionHandlers;
import com.teddy.jfinal.annotation.ResponseStatus;
import com.teddy.jfinal.common.Dict;
import com.teddy.jfinal.entity.Status;
import com.teddy.jfinal.exceptions.Lc4eApplicationException;
import com.teddy.jfinal.exceptions.Lc4eException;
import com.teddy.jfinal.plugin.PropPlugin;
import com.teddy.jfinal.tools.WebTool;
import com.teddy.lc4e.core.entity.Message;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthenticatedException;

/**
 * Created by teddy on 2015/7/19.
 */
@ExceptionHandlers
public class ExceptionHandle {

    @ExceptionHandler({AuthenticationException.class, UnauthenticatedException.class})
    public void auth(Exception e, Invocation ai) {
        ai.getController().setAttr("message", new Message(e.getMessage()));
        ai.getController().render("/pages/exception");
    }

    @ExceptionHandler({Exception.class, Lc4eException.class, Lc4eApplicationException.class})
    public void common(Throwable e, Invocation ai) {
        Message error = new Message(e.getMessage() == null ? e.toString() : e.getMessage());
        if (WebTool.isAJAX(ai.getController().getRequest())) {
            ai.getController().renderJson(error);
        } else {
            ai.getController().setAttr("message", error);
            ai.getController().render("/pages/exception");
        }
    }

}
