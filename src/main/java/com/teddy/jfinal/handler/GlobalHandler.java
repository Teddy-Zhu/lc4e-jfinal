package com.teddy.jfinal.handler;

import com.jfinal.core.JFinal;
import com.jfinal.handler.Handler;
import com.teddy.jfinal.common.Const;
import com.teddy.jfinal.handler.support.GlobalHandlerKit;
import com.teddy.jfinal.plugin.ShiroPlugin;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.servlet.ShiroHttpServletResponse;
import org.apache.shiro.web.subject.WebSubject;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.Callable;

/**
 * Created by teddy on 2015/7/22.
 */
public class GlobalHandler extends Handler {

    private static final Logger logger = Logger.getLogger(GlobalHandler.class);

    @Override
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
        Throwable t = null;
        final Subject subject = createSubject(request, response);
        subject.execute(new Callable() {
            public Object call() throws Exception {
                updateSessionLastAccessTime(request, response);
                GlobalHandlerKit.handleAOPMethods(target, request, response, isHandled, Const.BEFORE_HANDLER);
                nextHandler.handle(target, request, response, isHandled);
                GlobalHandlerKit.handleAOPMethods(target, request, response, isHandled, Const.AFTER_HANDLER);
                return null;
            }
        });
        if (t != null) {
            if (t instanceof Exception) {
                t.printStackTrace();
            }
            //otherwise it's not one of the two exceptions expected by the filter method signature - wrap it in one:
            logger.error("Filtered request failed.");
        }
    }

    private WebSecurityManager getSecurityManager() {
        return ShiroPlugin.defaultWebSecurityManager;
    }

    private boolean isHttpSessions() {
        return getSecurityManager().isHttpSessionMode();
    }

    private ServletRequest wrapServletRequest(HttpServletRequest orig) {
        return new ShiroHttpServletRequest(orig, getServletContext(), isHttpSessions());
    }


    private ServletContext getServletContext() {
        return JFinal.me().getServletContext();
    }

    @SuppressWarnings({"UnusedDeclaration"})
    private ServletRequest prepareServletRequest(ServletRequest request, ServletResponse response) {
        ServletRequest toUse = request;
        if (request instanceof HttpServletRequest) {
            HttpServletRequest http = (HttpServletRequest) request;
            toUse = wrapServletRequest(http);
        }
        return toUse;
    }

    private ServletResponse wrapServletResponse(HttpServletResponse orig, ShiroHttpServletRequest request) {
        return new ShiroHttpServletResponse(orig, getServletContext(), request);
    }

    private ServletResponse prepareServletResponse(ServletRequest request, ServletResponse response) {
        ServletResponse toUse = response;
        if (!isHttpSessions() && (request instanceof ShiroHttpServletRequest) &&
                (response instanceof HttpServletResponse)) {
            //the ShiroHttpServletResponse exists to support URL rewriting for session ids.  This is only needed if
            //using Shiro sessions (i.e. not simple HttpSession based sessions):
            toUse = wrapServletResponse((HttpServletResponse) response, (ShiroHttpServletRequest) request);
        }
        return toUse;
    }

    private WebSubject createSubject(ServletRequest request, ServletResponse response) {
        return new WebSubject.Builder(getSecurityManager(), request, response).buildWebSubject();
    }

    private void updateSessionLastAccessTime(ServletRequest request, ServletResponse response) {
        if (!isHttpSessions()) { //'native' sessions
            Subject subject = SecurityUtils.getSubject();
            //Subject should never _ever_ be null, but just in case:
            if (subject != null) {
                Session session = subject.getSession(false);
                if (session != null) {
                    try {
                        session.touch();
                    } catch (Throwable t) {
                        logger.error("session.touch() method invocation has failed.  Unable to update" +
                                "the corresponding session's last access time based on the incoming request.", t);
                    }
                }
            }
        }
    }
}
