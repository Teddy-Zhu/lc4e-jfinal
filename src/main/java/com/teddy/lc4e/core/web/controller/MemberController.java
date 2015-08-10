package com.teddy.lc4e.core.web.controller;

import com.teddy.jfinal.annotation.Controller;
import com.teddy.jfinal.annotation.ValidateParam;
import com.teddy.jfinal.annotation.ValidateParams;
import com.teddy.jfinal.interfaces.BaseController;
import com.teddy.lc4e.core.database.mapping.T_User;
import com.teddy.lc4e.core.database.model.User;
import com.teddy.lc4e.core.entity.Message;
import com.teddy.lc4e.core.web.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

/**
 * Created by teddy on 2015/8/10.
 */
@Controller("/member")
public class MemberController extends BaseController {


    public void signup() {
        User user = getModel(User.class, "user").enhancer();
        UserService.service.createUser(user);

        renderJson(user);
    }

    @ValidateParams(fields = {@ValidateParam(value = "user.name", minLen = 4, maxLen = 12), @ValidateParam(value = "user.password", minLen = 6, maxLen = 20)})
    public void signin() {
        User user = getModel(User.class, "user");
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getStr(T_User.name), user.getStr(T_User.password));
        token.setRememberMe(true);
        subject.login(token);
        if (subject.isAuthenticated()) {
            renderJson(new Message(true, "Login Success"));
        } else {
            renderJson(new Message("Login failed"));
        }
    }
}
