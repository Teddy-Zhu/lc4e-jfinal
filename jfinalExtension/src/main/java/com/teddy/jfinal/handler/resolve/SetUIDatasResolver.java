package com.teddy.jfinal.handler.resolve;

import com.jfinal.aop.Invocation;
import com.teddy.jfinal.annotation.SetUIDatas;
import com.teddy.jfinal.interfaces.AnnotationResolver;

/**
 * Created by teddy on 2015/8/22.
 */
public class SetUIDatasResolver implements AnnotationResolver {
    private SetUIDatas uiDatas;

    @Override
    public void resolve(Invocation invocation) throws Exception {
        AttributeKit.setUIDatas(uiDatas, invocation);
    }

    public SetUIDatasResolver(SetUIDatas uiDatas) {
        this.uiDatas = uiDatas;
    }
}
