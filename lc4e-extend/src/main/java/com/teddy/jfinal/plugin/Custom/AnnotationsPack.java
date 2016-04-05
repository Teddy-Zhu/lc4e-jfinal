package com.teddy.jfinal.plugin.Custom;

import com.teddy.jfinal.tools.ClassSearcherTool;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by teddyzhu on 16/4/5.
 */
public class AnnotationsPack {

    private Map<Class<? extends Annotation>, Set<Class>> classesMap;

    public AnnotationsPack(List<String> jars) {
        if (jars.size() > 0) {
            classesMap = new ClassSearcherTool().includeAllJarsInLib(ClassSearcherTool.isValiJar()).injars(jars).getAllAnnotation();
        } else {
            classesMap = new ClassSearcherTool().getAllAnnotation();
        }
    }

    public boolean containsAnnotation(Class clz) {
        return classesMap.containsKey(clz);
    }

    public Set<Class> getAnnotationClass(Class clz) {
        return containsAnnotation(clz) ? classesMap.get(clz) : new HashSet<>();
    }

}
