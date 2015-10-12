package com.teddy.lc4e.core.web.controller;

import com.jfinal.kit.StrKit;
import com.teddy.jfinal.annotation.*;
import com.teddy.jfinal.entity.Method;
import com.teddy.jfinal.interfaces.BaseController;
import com.teddy.jfinal.tools.RelativeDate;
import com.teddy.lc4e.core.config.Key;
import com.teddy.lc4e.core.entity.Article;
import com.teddy.lc4e.core.entity.Message;
import com.teddy.lc4e.core.entity.Popup;
import com.teddy.lc4e.core.web.service.ComVarService;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresGuest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


/**
 * Created by teddy on 2015/7/18.
 */
@Controller("/")
public class ViewController extends BaseController {

    private static final Logger log = Logger.getLogger(ViewController.class);

    @ValidateParams(value = {
            @ValidateParam(index = 1, type = int.class, defaultValue = "1"),
            @ValidateParam(value = "art", type = boolean.class, defaultValue = "false"),
            @ValidateParam(value = "a", type = String.class, defaultValue = "index"),
            @ValidateParam(index = 0, type = Integer.class, defaultValue = "2")
    })
    public void index() {
        setAttr("page", getPara(1));
        if (isPJAX()) {
            forwardAction("/Articles");
        } else {
            setAttr("topics", getArticle(getParaToInt(1), getParaToInt(0), getPara("a")));
            render("pages/index");
        }
    }

    @RequestMethod(Method.GET)
    @ValidateParams({
            @ValidateParam(value = "p", type = int.class, defaultValue = "1"),
            @ValidateParam(value = "a", type = String.class, defaultValue = "index"),
            @ValidateParam(value = "o", type = Integer.class, defaultValue = "1")
    })
    public void Articles() {
        setAttr("topics", getArticle(getParaToInt("p"), getParaToInt("o"), getPara("a")));
        render("ajax/_topic_detail");
    }

    @RequestMethod(Method.GET)
    @ValidateParams({
            @ValidateParam(index = 2, type = int.class, defaultValue = "1"), //page
            @ValidateParam(index = 0, type = String.class, defaultValue = "index"),
            @ValidateParam(index = 1, type = Integer.class, defaultValue = "1") //order
    })
    public void a() {
        setAttr("curArea", getPara(0));
        setAttr("topics", getArticle(getParaToInt(2), getParaToInt(1), getPara(0)));
        render("pages/area");
    }


    public void captcha() {
        renderCaptcha();
    }

    public void TopHots() {
        render("topHotTest");
    }

    public void SignOut() {
        SecurityUtils.getSubject().logout();
        if (isAJAX()) {
            renderJson(new Message(true, "SignOut Successfully"));
        } else {
            forwardAction("/");
        }
    }

    @RequiresGuest
    @SetComVar(value = Key.CAPTCHA, type = Boolean.class)
    public void SignIn() {
        render("pages/signin");
    }

    @RequiresGuest
    @SetComVars(value = {
            @SetComVar(value = Key.SIMPLE_REGISTER, type = Boolean.class),
            @SetComVar(value = Key.CAPTCHA, type = Boolean.class)
    })
    public void SignUp() {
        render("pages/signup");
    }

    public void test() {
        render("pages/test.html");
    }

    //test
    public void Error() {
        setAttr("message", new Message("test error"));
        render("pages/exception");
    }

    public List<Article> getArticle(int page, int order, String area) {
        Integer size = 10;
        if (StrKit.isBlank(area) || "index".equals(area)) {
            area = "";
            size = Integer.valueOf(ComVarService.service.getComVarValueByName("IndexPageSize"));
        } else {
            size = Integer.valueOf(ComVarService.service.getComVarValueByName("AreaPageSize"));
        }

        String[] cate = new String[]{"Java", "Obj-C", "C", "C++", "IOS", "Android"};
        String[] high = new String[]{"TOP", "NOTICE", "OTHER", "SYSTEM", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
        String[] users = new String[]{"Admin", "Test", "Myas", "Liakx", "Google", "vsss"};
        Date now = new Date();
        List<Article> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(new Article("/themes/" + Key.kvs.get("Theme") + "/images/wireframe/image.png", new Popup("Matt", "Matt has been a member since July 2014"), "The friction between your thoughts and your code" + page, cate[new Random().nextInt(cate.length - 1)], users[new Random().nextInt(users.length - 1)], new Random().nextInt(100),
                    RelativeDate.format(RelativeDate.randomDate("2015-05-11 13:00:00", now), now), users[new Random().nextInt(users.length - 1)], high[new Random().nextInt(high.length - 1)]));
        }
        return list;
    }

}
