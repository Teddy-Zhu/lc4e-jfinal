package com.teddy.lc4e.util.shiro;

import com.jfinal.plugin.ehcache.CacheKit;
import com.teddy.lc4e.database.model.User;
import com.teddy.lc4e.database.model.Vw_User_Role_Permission;
import com.teddy.lc4e.web.service.CurUserService;
import com.teddy.lc4e.web.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class UserRealm extends AuthorizingRealm {

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        List<Vw_User_Role_Permission> user = UserService.service.findUserRolesAndPermission(username);
        Set<String> roles = new HashSet<>(), permissions = new HashSet<>();
        user.forEach(u -> {
            roles.add(u.getStr(Vw_User_Role_Permission.S_ROLEABBR));
            permissions.add(u.getStr(Vw_User_Role_Permission.S_PERMISSIONABBR));
        });

        authorizationInfo.setRoles(roles);
        authorizationInfo.setStringPermissions(permissions);

        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String username = (String) token.getPrincipal();
        User user = UserService.service.findUserFullInfo(username);

        if (user == null) {
            throw new UnknownAccountException();
        }

        if (Boolean.TRUE.equals(user.getBoolean(Vw_User_Role_Permission.S_LOCKED))) {
            throw new LockedAccountException();
        }

        CacheKit.put("users", CurUserService.service.getSessionId(), user);

        return new SimpleAuthenticationInfo(user.getStr(Vw_User_Role_Permission.S_NAME), user.getStr(Vw_User_Role_Permission.S_PASSSALT), ByteSource.Util.bytes(user.getStr(User.S_NAME) + user.getStr(Vw_User_Role_Permission.S_PASSSALT)), getName());
    }

    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        CacheKit.remove("users", CurUserService.service.getSessionId());
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        CacheKit.remove("users", CurUserService.service.getSessionId());
        super.clearCache(principals);
    }

    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }

}
