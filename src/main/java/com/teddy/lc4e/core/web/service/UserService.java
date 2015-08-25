package com.teddy.lc4e.core.web.service;

import com.teddy.jfinal.annotation.Cache;
import com.teddy.jfinal.annotation.Service;
import com.teddy.jfinal.annotation.Transaction;
import com.teddy.lc4e.core.database.mapping.T_User;
import com.teddy.lc4e.core.database.mapping.T_Vw_User_Role_Permission;
import com.teddy.lc4e.core.database.model.User;
import com.teddy.lc4e.core.database.model.User_Basicinfo;
import com.teddy.lc4e.core.database.model.Vw_User_Role_Permission;
import com.teddy.lc4e.core.util.shiro.PassDisposer;

import java.util.List;

/**
 * Created by teddy on 2015/8/7.
 */
@Service
public class UserService {

    public static UserService service;

    @Cache(index = 0)
    public List<Vw_User_Role_Permission> findUserRolesAndPermission(String username) {
        StringBuffer sql = new StringBuffer();
        sql.append("select ").append(T_Vw_User_Role_Permission.ALL_FIELDS).append(" from ").append(T_Vw_User_Role_Permission.TABLE_NAME)
                .append(" where ").append(T_Vw_User_Role_Permission.ROLEAVAILABLE).append("=1").append(" AND ")
                .append(T_Vw_User_Role_Permission.PERMISSIONAVAILABLE).append("=1").append(" AND ")
                .append(T_Vw_User_Role_Permission.ROLEENDTIME).append(">NOW()").append(" AND ").append(T_Vw_User_Role_Permission.name).append("=?");
        return Vw_User_Role_Permission.dao.find(sql.toString(), username);
    }

    @Cache(index = 0)
    public User findUserFullInfo(String username) {
        StringBuffer sql = new StringBuffer();
        sql.append("select ").append(T_User.ALL_FIELDS).append(" from ").append(T_User.TABLE_NAME)
                .append(" where ").append(T_User.name).append("=?");
        return User.dao.findFirst(sql.toString(), username);
    }


    @Transaction
    public User createUser(User user, User_Basicinfo basicinfo) {
        PassDisposer.encryptPassword(user);

        user.set(T_User.mail, "test@test.com").set(T_User.nick, user.getStr(T_User.name))
                .set(T_User.locked, false);
        user.save();
        return user;
    }
}
