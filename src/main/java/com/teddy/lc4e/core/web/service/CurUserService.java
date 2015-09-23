package com.teddy.lc4e.core.web.service;

import com.jfinal.plugin.ehcache.CacheKit;
import com.teddy.jfinal.annotation.Service;
import com.teddy.lc4e.core.database.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * Created by teddy on 2015/9/23.
 */
@Service
public class CurUserService {
    public static CurUserService service;

    public String getSessionId() {
        Subject user = SecurityUtils.getSubject();
        if (user == null) {
            return null;
        }
        return user.getSession().getId().toString();
    }

    public User getCurrentUser() {
        Subject user = SecurityUtils.getSubject();
        if (user == null) {
            return null;
        }
        User curUser = null;
        if (user.isAuthenticated()) {
            curUser = CacheKit.get("users", user.getSession().getId().toString());
        }
        return curUser;
    }

    public boolean isLogin() {
        Subject user = SecurityUtils.getSubject();
        return user != null && user.isAuthenticated();
    }
}
