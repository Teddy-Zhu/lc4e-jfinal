package com.teddy.jfinal.plugin.shiro;

import com.jfinal.handler.Handler;
import com.teddy.jfinal.plugin.core.ShiroPlugin;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.ExecutionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.subject.WebSubject;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by teddyzhu on 16/6/29.
 */
public class ShiroHandler  extends Handler {
    private static final Logger logger = Logger.getLogger(ShiroHandler.class);

    @Override
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
        Throwable t = null;
        final Subject subject = createSubject(request, response);
        try {
            final String finalTarget = target;
            subject.execute(() -> {
                updateSessionLastAccessTime(request, response);
                next.handle(finalTarget, request, response, isHandled);
                return null;
            });
        } catch (ExecutionException ex) {
            t = ex.getCause();
        } catch (Throwable throwable) {
            t = throwable;
        }

        if (t != null) {
            if (t instanceof Exception) {
                t.printStackTrace();
            }
            logger.error("Filtered request failed.");
        }
    }


    private WebSecurityManager getSecurityManager() {
        return ShiroPlugin.defaultWebSecurityManager;
    }

    private boolean isHttpSessions() {
        return getSecurityManager().isHttpSessionMode();
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
