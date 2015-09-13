package com.teddy.lc4e.core.web.controller;

import com.jfinal.kit.StrKit;
import com.teddy.jfinal.annotation.Controller;
import com.teddy.jfinal.annotation.ValidateParam;
import com.teddy.jfinal.annotation.ValidateParams;
import com.teddy.jfinal.interfaces.BaseController;
import com.teddy.lc4e.core.config.Key;
import com.teddy.lc4e.core.entity.Message;
import com.teddy.lc4e.core.web.service.UserService;

/**
 * Created by teddy on 2015/8/27.
 */
@Controller("/su")
public class SuggestController extends BaseController {

    @ValidateParams(value = {
            @ValidateParam(value = "user.name", minLen = 4, maxLen = 12, required = false, defaultValue = ""),
            @ValidateParam(value = "user.nick", minLen = 4, maxLen = 12, required = false, defaultValue = ""),
            @ValidateParam(value = "user.mail", minLen = 6, maxLen = 30, required = false, defaultValue = "")})
    public void user() {
        boolean exist = true;
        StringBuilder message = new StringBuilder();
        String name = getPara("user.name"), nick = getPara("user.nick"), mail = getPara("user.mail");
        if (StrKit.notBlank(name)) {
            exist = UserService.service.validateUserName(name);
            message.append("UserName");
        } else if (StrKit.notBlank(nick)) {
            exist = UserService.service.validateUserNick(nick);
            message.append("UserNick");
        } else if (StrKit.notBlank(mail)) {
            exist = UserService.service.validateUserMail(mail);
            message.append("UserMail");
        }
        message.append(" Has been occupied");
        renderJson(new Message(!exist, exist ? message.toString() : Key.Empty));
    }
}
