package com.teddy.jfinal.plugin.CustomAnnotationResolve;

import com.jfinal.aop.Invocation;
import com.jfinal.plugin.ehcache.CacheKit;
import com.teddy.jfinal.annotation.Cache;
import com.teddy.jfinal.annotation.CustomAnnotation;
import com.teddy.jfinal.common.Const;
import com.teddy.jfinal.common.Dict;
import com.teddy.jfinal.interfaces.CustomAnnotationPlugin;
import com.teddy.jfinal.plugin.PropPlugin;
import com.teddy.jfinal.tools.ReflectTool;
import com.teddy.jfinal.tools.StringTool;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;

/**
 * Created by teddyzhu on 15/12/12.
 */
@CustomAnnotation
public class CacheAnnotationResolver extends CustomAnnotationPlugin {

    @Override
    public int getOrder() {
        return 999;
    }

    @Override
    public Class<? extends Annotation> getAnnotation() {
        return Cache.class;
    }

    @Override
    public Object intercept(Annotation annotation, AnnotationPluginResolver resolver, Object[] objects, Object obj, Method method, boolean[] isHandled) throws Throwable {
        Object returnValue;
        boolean useCache;
        Object cacheKey = null;
        Cache cache = (Cache) annotation;
        useCache = cache != null && PropPlugin.getBool(Dict.USE_CACHE, true) && cache.index() < objects.length;

        cacheKey = cache.index() == -1 ? cache.key() : objects[cache.index()];
        if (cacheKey.getClass().isArray()) {
            cacheKey = StringUtils.join((String[]) cacheKey, ",");
        } else if (cacheKey instanceof Collection) {
            cacheKey = StringTool.join((Collection<String>) cacheKey, ",");
        } else {
            cacheKey = cacheKey.toString();
        }

        cacheKey = method.getName() + cacheKey;

        returnValue = CacheKit.get(cache.cacheName(), cacheKey);
        if (returnValue != null) {
            isHandled[0] = true;
            return returnValue;
        } else if (!cache.defaultValue().equals(Const.DEFAULT_NONE)) {
            CacheKit.put(cache.cacheName(), cacheKey, cache.defaultValue());
            isHandled[0] = true;
            return cache.defaultValue();
        }
        returnValue = resolver.invoke();
        if (useCache) {
            CacheKit.put(cache.cacheName(), cacheKey, returnValue);
        }
        return returnValue;
    }

}