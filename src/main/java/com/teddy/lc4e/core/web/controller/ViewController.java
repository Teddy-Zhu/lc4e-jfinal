package com.teddy.lc4e.core.web.controller;

import com.teddy.jfinal.annotation.*;
import com.teddy.jfinal.entity.Method;
import com.teddy.jfinal.interfaces.BaseController;
import com.teddy.jfinal.tools.RelativeDate;
import com.teddy.jfinal.tools.WebTool;
import com.teddy.lc4e.core.database.mapping.T_User;
import com.teddy.lc4e.core.database.model.User;
import com.teddy.lc4e.core.entity.Article;
import com.teddy.lc4e.core.entity.Message;
import com.teddy.lc4e.core.entity.Popup;
import com.teddy.lc4e.core.web.service.ComVarService;
import com.teddy.lc4e.core.web.service.UserService;
import com.teddy.lc4e.core.web.service.testService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


/**
 * Created by teddy on 2015/7/18.
 */
@Controller("/")
public class ViewController extends BaseController {

    @Inject
    public testService service;

    @ValidateParam(value = "p", type = int.class, defaultValue = "1")
    public void index() {
        setAttr("page", getPara("p"));
        if (WebTool.isPJAX(getRequest())) {
            forwardAction("/Articles");
        } else {
            render("pages/index");
        }
    }

    @RequestMethod(Method.GET)
    @ValidateParam(value = "p", type = int.class, defaultValue = "1")
    public void Articles() {
        Integer size = Integer.valueOf(ComVarService.service.getComVarValueByName("IndexPageSize"));
        String[] cate = new String[]{"Java", "Obj-C", "C", "C++", "IOS", "Android"};
        String[] users = new String[]{"Admin", "Test", "Myas", "Liakx", "Google", "vsss"};
        Date now = new Date();
        List<Article> list = new ArrayList<Article>();
        for (int i = 0; i < size; i++) {
            list.add(new Article("/images/wireframe/image.png", new Popup("Matt", "Matt has been a member since July 2014"), "The friction between your thoughts and your code", cate[new Random().nextInt(cate.length - 1)], users[new Random().nextInt(users.length - 1)], new Random().nextInt(100),
                    RelativeDate.format(RelativeDate.randomDate("2015-05-11 13:00:00", now), now), users[new Random().nextInt(users.length - 1)]));
        }
        setAttr("lists", list);
        render("pjax/_article");
    }

    public void TopHots() {
        render("topHotTest");
    }

    @ValidateParams(fields = {@ValidateParam(value = "user.name", minLen = 4, maxLen = 12), @ValidateParam(value = "user.password", minLen = 6, maxLen = 20)})
    public void SignUp() {
        User user = getModel(User.class, "user").enhancer();
        UserService.service.createUser(user);

        renderJson(user);
    }

    @ValidateParams(fields = {@ValidateParam(value = "user.name", minLen = 4, maxLen = 12), @ValidateParam(value = "user.password", minLen = 6, maxLen = 20)})
    public void SignIn() {
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
