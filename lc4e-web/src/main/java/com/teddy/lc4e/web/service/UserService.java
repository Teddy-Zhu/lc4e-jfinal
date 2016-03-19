package com.teddy.lc4e.web.service;

import com.teddy.lc4e.database.model.SysCommonVariable;
import com.teddy.lc4e.database.model.User;
import com.teddy.lc4e.database.model.UserBasicinfo;
import com.teddy.lc4e.database.model.VwUserRolePermission;
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
    public List<VwUserRolePermission> findUserRolesAndPermission(String username) {
        SQLTool sql = new SQLTool().select(VwUserRolePermission.ALL_FIELDS)
                .from(VwUserRolePermission.TABLE_NAME)
                .where(" AND ", VwUserRolePermission.roleavailable + "=1",
                        VwUserRolePermission.permissionavailable + "=1",
                        VwUserRolePermission.roleendtime + ">NOW()",
                        VwUserRolePermission.name + "=?");
        sql.appendParam(username);
        return VwUserRolePermission.dao.find(sql);
    }

    @Cache(index = 0)
    public User findUserFullInfo(String username) {
        SQLTool sql = new SQLTool().select(User.ALL_FIELDS)
                .from(User.TABLE_NAME).where(User.name + "=?");
        sql.appendParam(username);
        return User.dao.findFirst(sql);
    }

    // to do simply
    public boolean validateUserName(String name) {
        return User.dao.exist(name, User.name);
    }

    public boolean validateUserNick(String nick) {
        return User.dao.exist(nick, User.nick);
    }

    public boolean validateUserMail(String mail) {
        return User.dao.exist(mail, User.mail);
    }

    @Transaction
    public User createUser(User user, UserBasicinfo basicinfo) throws Lc4eApplicationException {
        //validate exist
        //username
        if (validateUserName(user.getName())) {
            throw new Lc4eApplicationException("User Name Has been occupied");
        }
        //usernick
        if (validateUserMail(user.getMail())) {
            throw new Lc4eApplicationException("Email Has been occupied");
        }
        //usermail
        if (validateUserNick(user.getNick())) {
            throw new Lc4eApplicationException("Nick Has been occupied");
        }
        PassDisposer.encryptPassword(user);
        user.remove(User.ID);
        user.set(User.LOCKED, false);
        user.save();

        if (user.getId() != null) {
            if (!ComVarService.service.getComVarByName(Key.SIMPLE_REGISTER).getToBoolean(SysCommonVariable.VALUE)) {
                basicinfo.remove(UserBasicinfo.ID);
                basicinfo.setUserId(user.getId());
                basicinfo.save();
            } else {
                UserBasicinfo newBasicInfo = new UserBasicinfo();
                newBasicInfo.setUserId(user.getId());
                newBasicInfo.save();
            }
        }

        return user;
    }
}
