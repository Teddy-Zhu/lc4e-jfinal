package com.teddy.lc4e.core.web.controller;

import com.jfinal.core.ActionKey;
import com.teddy.jfinal.annotation.*;
import com.teddy.jfinal.entity.Method;
import com.teddy.jfinal.interfaces.BaseController;
import com.teddy.jfinal.tools.RelativeDate;
import com.teddy.jfinal.tools.WebTool;
import com.teddy.lc4e.core.entity.Article;
import com.teddy.lc4e.core.entity.Popup;
import com.teddy.lc4e.core.web.service.ComVarService;
import com.teddy.lc4e.core.web.service.MenuService;
import com.teddy.lc4e.core.web.service.testService;

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
    @ActionKey("/Articles")
    @ValidateParam(value = "p", type = int.class, defaultValue = "1")
    public void articletest() {
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
        render("template/_article");
    }

    @ActionKey(value = "/TopHots")
    public void tophostsTest() {
        render("topHotTest");
    }

    @ActionKey(value = "/SignIn")
    public void signIn() {
        render("pages/signin");
    }

}
