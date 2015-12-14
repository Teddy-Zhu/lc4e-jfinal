package com.teddy.jfinal.interfaces;

import com.jfinal.core.Controller;
import com.teddy.jfinal.plugin.jetbrick.Lc4eCaptchaRender;
import com.teddy.jfinal.tools.WebTool;

/**
 * Created by teddy on 2015/7/20.
 */
public abstract class BaseController extends Controller {

    protected boolean isPJAX() {
        return WebTool.isPJAX(getRequest());
    }

    protected boolean isAJAX() {
        return WebTool.isAJAX(getRequest());
    }

    protected void renderCaptcha() {
        Lc4eCaptchaRender lc4eCaptchaRender = new Lc4eCaptchaRender();
        render(lc4eCaptchaRender);
    }

    protected boolean isPOST() {
        return WebTool.isPOST(getRequest());
    }
}
