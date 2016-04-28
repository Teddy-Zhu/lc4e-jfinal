package com.teddy.jfinal.plugin.CustomAnnotationResolve;

import com.jfinal.aop.Invocation;
import com.teddy.jfinal.annotation.CustomAnnotation;
import com.teddy.jfinal.interfaces.CustomAnnotationPlugin;
import com.teddy.jfinal.plugin.CustomPlugin;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.commons.collections.map.HashedMap;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by teddyzhu on 15/12/12.
 */
public class AnnotationPluginResolver {

    public int index = 0;

    private boolean useInjectTarget = true;
    private Object target;
    private Method method;
    private Object[] args;
    private MethodProxy methodProxy;
    private boolean[] isHandled;

    private Map<CustomAnnotationPlugin, Annotation> pluginMap;

    private List<CustomAnnotationPlugin> keySet;

    public AnnotationPluginResolver(boolean[] isHandled, Object target, Method method, Object[] args, MethodProxy methodProxy, Annotation[] annotations) {
        this.target = target;
        this.method = method;
        this.args = args;
        this.methodProxy = methodProxy;
        this.isHandled = isHandled;
        if (annotations == null)
            annotations = new Annotation[0];

        pluginMap = new HashedMap();

        for (Annotation an:
             annotations) {
            if(CustomPlugin.getCustoms().containsKey(an.annotationType())){
                pluginMap.put(CustomPlugin.getCustoms().get(an.annotationType()), an);
            }
        }
        Set<CustomAnnotationPlugin> keys = pluginMap.keySet();

        keySet = new ArrayList<>(keys);

        Collections.sort(keySet, (o1, o2) -> {
            int x = o1.getOrder();
            int y = o2.getOrder();
            return (x < y) ? -1 : ((x == y) ? 0 : 1);
        });


    }


    public Object invoke() throws Throwable {
        Object returnValue = null;
        if (index < keySet.size()) {
            CustomAnnotationPlugin plugin = keySet.get(index++);

            Annotation annotation = pluginMap.get(plugin);

            returnValue = plugin.intercept(annotation, this, args, target, method, isHandled);

            if (isHandled[0]) {
                return returnValue;
            }
        } else if (index++ == keySet.size()) {
            try {
                if (useInjectTarget)
                    returnValue = methodProxy.invoke(target, args);
                else
                    returnValue = methodProxy.invokeSuper(target, args);
            } catch (InvocationTargetException e) {
                Throwable t = e.getTargetException();
                throw t instanceof RuntimeException ? (RuntimeException) t : new RuntimeException(e);
            } catch (RuntimeException e) {
                throw e;
            } catch (Throwable t) {
                throw new RuntimeException(t);
            }
            return returnValue;
        }

        return returnValue;

    }
}
