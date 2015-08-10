package com.teddy.jfinal.plugin.beetl;

import com.teddy.jfinal.common.Const;
import org.apache.log4j.Logger;
import org.beetl.core.GroupTemplate;
import org.beetl.ext.jfinal.BeetlRender;

public class Lc4eBeetlRender extends BeetlRender {

    private static Logger log = Logger.getLogger(Lc4eBeetlRender.class);

    public Lc4eBeetlRender(GroupTemplate gt, String view) {
        super(gt, view);
    }

    @Override
    public void render() {
        long start = System.currentTimeMillis();
        log.debug("Lc4eBeetlRender render start time = " + start);
        super.render();
        long end = System.currentTimeMillis();
        long renderTime = end - start;
        log.debug("Lc4eBeetlRender render end time = " + end + "，renderTime = " + renderTime);
        request.setAttribute(Const.RENDER_TIME, renderTime);
    }
}
