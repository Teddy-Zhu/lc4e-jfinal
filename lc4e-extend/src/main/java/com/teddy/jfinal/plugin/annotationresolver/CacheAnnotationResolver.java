package com.teddy.jfinal.plugin.annotationresolver;

import com.jfinal.plugin.ehcache.CacheKit;
import com.teddy.jfinal.annotation.Cache;
import com.teddy.jfinal.annotation.CustomAnnotation;
import com.teddy.jfinal.common.Const;
import com.teddy.jfinal.common.Dict;
import com.teddy.jfinal.config.Config;
import com.teddy.jfinal.interfaces.CustomAnnotationPlugin;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;

/**
 * Created by teddyzhu on 15/12/12.
 */
@CustomAnnotation
public class CacheAnnotationResolver implements CustomAnnotationPlugin {

    @Override
    public int getOrder() {
        return 10;
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
        useCache = cache != null && Config.getCustomConfig().getProp().getBool(Dict.USE_CACHE, true) && cache.index() < objects.length;

        cacheKey = cache.index() == -1 ? cache.key() : objects[cache.index()];
        if (cacheKey.getClass().isArray() || cacheKey instanceof Collection) {
            cacheKey = StringUtils.join((String[]) cacheKey, ",");
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
