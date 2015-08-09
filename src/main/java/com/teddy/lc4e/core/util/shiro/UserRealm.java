package com.teddy.lc4e.core.util.shiro;

import com.teddy.lc4e.core.database.mapping.T_User;
import com.teddy.lc4e.core.database.mapping.T_Vw_User_Role_Permission;
import com.teddy.lc4e.core.database.model.User;
import com.teddy.lc4e.core.database.model.Vw_User_Role_Permission;
import com.teddy.lc4e.core.web.service.UserService;
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
            roles.add(u.getStr(T_Vw_User_Role_Permission.roleAbbr));
            permissions.add(u.getStr(T_Vw_User_Role_Permission.permissionAbbr));
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
        if (Boolean.TRUE.equals(user.getBoolean(T_Vw_User_Role_Permission.locked))) {
            throw new LockedAccountException();
        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getStr(T_Vw_User_Role_Permission.name), user.getStr(T_Vw_User_Role_Permission.password), ByteSource.Util.bytes(user.getStr(T_User.name) + user.getStr(T_Vw_User_Role_Permission.passsalt)), getName());
        return authenticationInfo;
    }

    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
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
