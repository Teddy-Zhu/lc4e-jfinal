package com.teddy.jfinal.handler.resolve;

import com.jfinal.kit.StrKit;
import com.teddy.jfinal.annotation.CustomAnnotation;
import com.teddy.jfinal.annotation.RequestHeader;
import com.teddy.jfinal.exceptions.Lc4eException;
import com.teddy.jfinal.exceptions.Lc4eValidateException;
import com.teddy.jfinal.interfaces.CustomAnnotationPlugin;
import com.teddy.jfinal.plugin.annotationresolve.AnnotationPluginResolver;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by teddyzhu on 15/12/14.
 */
@CustomAnnotation
public class RequestHeaderAnnotationResolver implements CustomAnnotationPlugin {
    @Override
    public Class<? extends Annotation> getAnnotation() {
        return RequestHeader.class;
    }

    @Override
    public Object intercept(Annotation annotation, AnnotationPluginResolver resolver, Object[] objects, Object target, Method method, boolean[] isHandled) throws Throwable {

        com.jfinal.core.Controller controller = (com.jfinal.core.Controller) target;
        RequestHeader header = (RequestHeader) annotation;
        HttpServletRequest request = controller.getRequest();

        String[] keys = header.key();
        String[] values = header.value();
        if (keys.length == values.length) {
            for (int i = 0, len = keys.length; i < len; i++) {
                String value = request.getHeader(keys[i].trim());
                if (StrKit.isBlank(value) || !value.trim().equals(values[i].trim())) {
                    //controller.renderError(404);
                    throw new Lc4eValidateException("Request Header [" + keys[i] + "] must be [" + values[i] + "]");
                }
            }
        } else {
            throw new Lc4eException("@RequestHeader need the same length key and value");
        }

        return resolver.invoke();
    }
}
