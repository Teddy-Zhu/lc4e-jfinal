package com.teddy.lc4e.web.service;

import com.teddy.jfinal.annotation.Cache;
import com.teddy.jfinal.annotation.Service;
import com.teddy.jfinal.annotation.Transaction;
import com.teddy.jfinal.exceptions.Lc4eApplicationException;
import com.teddy.lc4e.config.Key;
import com.teddy.lc4e.database.mapping.T_Sys_Common_Variable;
import com.teddy.lc4e.database.mapping.T_User;
import com.teddy.lc4e.database.mapping.T_User_Basicinfo;
import com.teddy.lc4e.database.mapping.T_Vw_User_Role_Permission;
import com.teddy.lc4e.database.model.User;
import com.teddy.lc4e.database.model.User_Basicinfo;
import com.teddy.lc4e.database.model.Vw_User_Role_Permission;
import com.teddy.lc4e.util.shiro.PassDisposer;

import java.util.List;

/**
 * Created by teddy on 2015/8/7.
 */
@Service
public class UserService {

    public static UserService service;

    @Cache(index = 0)
    public List<Vw_User_Role_Permission> findUserRolesAndPermission(String username) {
        return Vw_User_Role_Permission.dao.find("select " + T_Vw_User_Role_Permission.ALL_FIELDS + " from " + T_Vw_User_Role_Permission.TABLE_NAME + " where " + T_Vw_User_Role_Permission.ROLEAVAILABLE + "=1" + " AND " + T_Vw_User_Role_Permission.PERMISSIONAVAILABLE + "=1" + " AND " + T_Vw_User_Role_Permission.ROLEENDTIME + ">NOW()" + " AND " + T_Vw_User_Role_Permission.name + "=?", username);
    }

    @Cache(index = 0)
    public User findUserFullInfo(String username) {
        return User.dao.findFirst("select " + T_User.ALL_FIELDS + " from " + T_User.TABLE_NAME + " where " + T_User.name + "=?", username);
    }

    public boolean validateUserName(String name) {
        return User.dao.exist(name, T_User.NAME);
    }

    public boolean validateUserNick(String nick) {
        return User.dao.exist(nick, T_User.NICK);
    }

    public boolean validateUserMail(String mail) {
        return User.dao.exist(mail, T_User.MAIL);
    }

    @Transaction
    public User createUser(User user, User_Basicinfo basicinfo) throws Lc4eApplicationException {
        //validate exist
        //username
        if (validateUserName(user.getStr(T_User.name))) {
            throw new Lc4eApplicationException("User Name Has been occupied");
        }
        //usernick
        if (validateUserMail(user.getStr(T_User.mail))) {
            throw new Lc4eApplicationException("Email Has been occupied");
        }
        //usermail
        if (validateUserNick(user.getStr(T_User.nick))) {
            throw new Lc4eApplicationException("Email Has been occupied");
        }
        PassDisposer.encryptPassword(user);
        user.remove(T_User.id);
        user.set(T_User.locked, false);
        user.save();

        if (user.getStr(T_User.id) != null) {
            if (!ComVarService.service.getComVarByName(Key.SIMPLE_REGISTER).getToBoolean(T_Sys_Common_Variable.value)) {
                basicinfo.remove(T_User_Basicinfo.id);
                basicinfo.set(T_User_Basicinfo.userId, user.get(T_User.id));
                basicinfo.save();
            } else {
                new User_Basicinfo().enhancer().set(T_User_Basicinfo.userId, user.get(T_User.id)).save();
            }
        }

        return user;
    }
}
