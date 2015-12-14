package com.teddy.lc4e.web.service;

import com.teddy.lc4e.database.model.Sys_Common_Variable;
import com.teddy.lc4e.database.model.User;
import com.teddy.lc4e.database.model.User_Basicinfo;
import com.teddy.lc4e.database.model.Vw_User_Role_Permission;
import com.teddy.jfinal.annotation.Cache;
import com.teddy.jfinal.annotation.Service;
import com.teddy.jfinal.annotation.Transaction;
import com.teddy.jfinal.exceptions.Lc4eApplicationException;
import com.teddy.jfinal.tools.SQLTool;
import com.teddy.lc4e.config.Key;
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
        SQLTool sql = new SQLTool().select(Vw_User_Role_Permission.ALL_FIELDS)
                .from(Vw_User_Role_Permission.TABLE_NAME)
                .where(" AND ", Vw_User_Role_Permission.F_ROLEAVAILABLE + "=1",
                        Vw_User_Role_Permission.F_PERMISSIONAVAILABLE + "=1",
                        Vw_User_Role_Permission.F_ROLEENDTIME + ">NOW()",
                        Vw_User_Role_Permission.F_NAME + "=?");
        sql.addParam(username);
        return Vw_User_Role_Permission.dao.find(sql);
    }

    @Cache(index = 0)
    public User findUserFullInfo(String username) {
        SQLTool sql = new SQLTool().select(User.ALL_FIELDS)
                .from(User.TABLE_NAME).where(User.F_NAME + "=?");
        sql.addParam(username);
        return User.dao.findFirst(sql);
    }

    // to do simply
    public boolean validateUserName(String name) {
        return User.dao.exist(name, User.F_NAME);
    }

    public boolean validateUserNick(String nick) {
        return User.dao.exist(nick, User.F_NICK);
    }

    public boolean validateUserMail(String mail) {
        return User.dao.exist(mail, User.F_MAIL);
    }

    @Transaction
    public User createUser(User user, User_Basicinfo basicinfo) throws Lc4eApplicationException {
        //validate exist
        //username
        if (validateUserName(user.getStr(User.S_NAME))) {
            throw new Lc4eApplicationException("User Name Has been occupied");
        }
        //usernick
        if (validateUserMail(user.getStr(User.S_MAIL))) {
            throw new Lc4eApplicationException("Email Has been occupied");
        }
        //usermail
        if (validateUserNick(user.getStr(User.S_NICK))) {
            throw new Lc4eApplicationException("Nick Has been occupied");
        }
        PassDisposer.encryptPassword(user);
        user.remove(User.S_ID);
        user.set(User.S_LOCKED, false);
        user.save();

        if (user.getStr(User.S_ID) != null) {
            if (!ComVarService.service.getComVarByName(Key.SIMPLE_REGISTER).getToBoolean(Sys_Common_Variable.S_VALUE)) {
                basicinfo.remove(User_Basicinfo.S_ID);
                basicinfo.set(User_Basicinfo.S_USERID, user.get(User.S_ID));
                basicinfo.save();
            } else {
                new User_Basicinfo().set(User_Basicinfo.S_USERID, user.get(User.S_ID)).save();
            }
        }

        return user;
    }
}
