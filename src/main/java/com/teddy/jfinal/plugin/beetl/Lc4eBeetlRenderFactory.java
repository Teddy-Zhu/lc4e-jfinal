package com.teddy.jfinal.plugin.beetl;

import com.jfinal.render.Render;
import com.teddy.lc4e.core.config.Key;
import org.apache.log4j.Logger;
import org.beetl.ext.jfinal.BeetlRender;
import org.beetl.ext.jfinal.BeetlRenderFactory;

public class Lc4eBeetlRenderFactory extends BeetlRenderFactory {

    private static Logger log = Logger.getLogger(Lc4eBeetlRenderFactory.class);

    @Override
    public Render getRender(String view) {

        BeetlRender render = new Lc4eBeetlRender(groupTemplate, view + (view.endsWith(viewExtension) ? Key.Empty : viewExtension));
        return render;
    }

}
