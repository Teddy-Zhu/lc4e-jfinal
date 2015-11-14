package com.teddy.jfinal.plugin;

import com.jfinal.aop.Invocation;
import com.teddy.jfinal.interfaces.AnnotationResolver;
import com.teddy.jfinal.interfaces.Lc4ePlugin;

import java.util.List;

/**
 * Created by teddy on 2015/9/29.
 */
public class AnnotationPlugin extends Lc4ePlugin {
    private List<AnnotationResolver> beforeAnnotationResolvers;

    private List<AnnotationResolver> afterAnnotationResolvers;

    @Override
    public void init() {

    }

    @Override
    public boolean beforeController(Invocation invocation) throws Exception {
        for (AnnotationResolver annotationResolver : beforeAnnotationResolvers) {
            annotationResolver.resolve(invocation);
        }
        return true;
    }

    @Override
    public void afterController(Invocation invocation) throws Exception {
        for (AnnotationResolver annotationResolver : afterAnnotationResolvers) {
            annotationResolver.resolve(invocation);
        }
    }


    public List<AnnotationResolver> getBeforeAnnotationResolvers() {
        return beforeAnnotationResolvers;
    }

    public void setBeforeAnnotationResolvers(List<AnnotationResolver> beforeAnnotationResolvers) {
        this.beforeAnnotationResolvers = beforeAnnotationResolvers;
    }

    public List<AnnotationResolver> getAfterAnnotationResolvers() {
        return afterAnnotationResolvers;
    }

    public void setAfterAnnotationResolvers(List<AnnotationResolver> afterAnnotationResolvers) {
        this.afterAnnotationResolvers = afterAnnotationResolvers;
    }
}
