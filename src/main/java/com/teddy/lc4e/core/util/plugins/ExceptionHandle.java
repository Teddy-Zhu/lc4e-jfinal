package com.teddy.lc4e.core.util.plugins;

import com.jfinal.aop.Invocation;
import com.teddy.jfinal.annotation.ExceptionHandler;
import com.teddy.jfinal.annotation.ExceptionHandlers;
import com.teddy.jfinal.annotation.ResponseStatus;
import com.teddy.jfinal.common.Dict;
import com.teddy.jfinal.entity.Status;
import com.teddy.jfinal.plugin.PropPlugin;
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

}
