package com.teddy.jfinal.plugin.CustomAnnotationResolve;

import com.jfinal.aop.Invocation;
import com.teddy.jfinal.interfaces.CustomAnnotationPlugin;
import com.teddy.jfinal.plugin.CustomPlugin;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

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

    private Annotation[] ans;
    private static Map<Class<? extends Annotation>, CustomAnnotationPlugin> pluginMap;

    static {
        pluginMap = CustomPlugin.getCustoms();
    }

    public AnnotationPluginResolver(boolean[] isHandled, Object target, Method method, Object[] args, MethodProxy methodProxy, Annotation[] annotations) {
        this.target = target;
        this.method = method;
        this.args = args;
        this.methodProxy = methodProxy;
        this.isHandled = isHandled;
        if (annotations == null)
            annotations = new Annotation[0];
        this.ans = annotations;
    }

    public Object invoke() throws Throwable {
        Object returnValue = null;
        if (index < ans.length) {
            Annotation annotation = ans[index++];
            if (pluginMap.containsKey(annotation.annotationType())) {
                returnValue = pluginMap.get(annotation.annotationType()).intercept(annotation, this, args, target, method, isHandled);
            }
            if (isHandled[0]) {
                return returnValue;
            }
        } else if (index++ == ans.length) {
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
