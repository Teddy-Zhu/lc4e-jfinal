package com.teddy.lc4e.core.util.plugins;

import com.jfinal.aop.Invocation;
import com.teddy.jfinal.annotation.ExceptionHandler;
import com.teddy.jfinal.annotation.ExceptionHandlers;
import com.teddy.jfinal.annotation.ResponseStatus;
import com.teddy.jfinal.common.Dict;
import com.teddy.jfinal.entity.Status;
import com.teddy.jfinal.exceptions.Lc4eException;
import com.teddy.jfinal.plugin.PropPlugin;
import com.teddy.jfinal.tools.WebTool;
import com.teddy.lc4e.core.entity.Message;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;

/**
 * Created by teddy on 2015/7/19.
 */
@ExceptionHandlers
public class ExceptionHandle {

    @ExceptionHandler({NullPointerException.class})
    @ResponseStatus(Status.ERROR)
    public void handle(NullPointerException e, Invocation ai) {
        ai.getController().getResponse().setCharacterEncoding(PropPlugin.getValue(Dict.ENCODING, "utf-8"));
        String a = "异常测试";
        ai.getController().renderText(a + e.getMessage());
    }

    @ExceptionHandler({UnauthenticatedException.class, AuthorizationException.class})
    @ResponseStatus(Status.ERROR)
    public void handle(Exception e, Invocation ai) {
        ai.getController().getResponse().setCharacterEncoding(PropPlugin.getValue(Dict.ENCODING, "utf-8"));
        String a = "Shiro异常测试";
        ai.getController().renderText(a + e.getMessage());
    }

    @ExceptionHandler({AuthenticationException.class, UnauthenticatedException.class})
    public void auth(Exception e, Invocation ai) {
        ai.getController().setAttr("message", new Message(e.getMessage()));
        ai.getController().render("pages/exception");
    }

    @ExceptionHandler({UnknownAccountException.class})
    public void unknown(Exception e, Invocation ai) {
        ai.getController().renderJson(new Message("Username is not match password,please check your info"));
    }

    @ExceptionHandler({Lc4eException.class})
    public void unknown(Lc4eException e, Invocation ai) {
        if (WebTool.isAJAX(ai.getController().getRequest())) {
            ai.getController().renderJson(new Message(e.getMessage() == null ? e.toString() : e.getMessage()));
        } else {
            ai.getController().render("pages/exception");
        }
    }
}
