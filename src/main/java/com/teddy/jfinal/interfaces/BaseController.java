package com.teddy.jfinal.interfaces;

import com.jfinal.core.Controller;
import com.teddy.jfinal.plugin.beetl.Lc4eCaptchaRender;
import com.teddy.jfinal.tools.WebTool;

/**
 * Created by teddy on 2015/7/20.
 */
public abstract class BaseController extends Controller {
    public boolean isPJAX() {
        return WebTool.isPJAX(getRequest());
    }

    public boolean isAJAX() {
        return WebTool.isAJAX(getRequest());
    }

    public void renderCaptcha() {
        Lc4eCaptchaRender lc4eCaptchaRender = new Lc4eCaptchaRender();
        render(lc4eCaptchaRender);
    }


}
