package com.teddy.lc4e.web.controller;

import com.jfinal.plugin.ehcache.CacheKit;
import com.teddy.jfinal.annotation.Controller;
import com.teddy.jfinal.annotation.ValidateParam;
import com.teddy.jfinal.annotation.ValidateParams;
import com.teddy.jfinal.common.Const;
import com.teddy.jfinal.interfaces.BaseController;
import com.teddy.lc4e.entity.Message;

/**
 * Created by teddy on 2015/8/12.
 */
@Controller("/cache")
public class CacheController extends BaseController {

    @ValidateParams(value = {
            @ValidateParam(value = "cacheName", defaultValue = Const.COMVAR),
            @ValidateParam(value = "key")})
    public void clear() {
        String cacheName = getPara("cacheName");
        String key = getPara("key");
        CacheKit.remove(cacheName, key);
        renderJson(new Message(true, "clear " + cacheName + "--" + key + " success"));
    }
}
