package com.teddy.jfinal.tools;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Model;
import com.teddy.jfinal.common.Const;
import com.teddy.jfinal.exceptions.Lc4eException;
import com.teddy.jfinal.exceptions.ReflectException;
import net.sf.cglib.proxy.Enhancer;
import org.eclipse.jetty.server.Request;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

public class ReflectTool {

    public static ReflectTool on(String name) throws ReflectException {
        return on(forName(name));
    }


    public static ReflectTool on(Class<?> clazz) {
        return new ReflectTool(clazz);
    }

    public static ReflectTool on(Object object) {
        return new ReflectTool(object);
    }

    public static <T extends AccessibleObject> T accessible(T accessible) {
        if (accessible == null) {
            return null;
        }

        if (!accessible.isAccessible()) {
            accessible.setAccessible(true);
        }

        return accessible;
    }

    private final Object object;


    private final boolean isClass;


    private ReflectTool(Class<?> type) {
        this.object = type;
        this.isClass = true;
    }

    private ReflectTool(Object object) {
        this.object = object;
        this.isClass = false;
    }

    @SuppressWarnings("unchecked")
    public <T> T get() {
        return (T) object;
    }

    public ReflectTool set(String name, Object value) throws ReflectException {
        try {

            // Try setting a public field
            Field field = type().getField(name);
            field.set(object, unwrap(value));
            return this;
        } catch (Exception e1) {

            // Try again, setting a non-public field
            try {
                accessible(type().getDeclaredField(name)).set(object, unwrap(value));
                return this;
            } catch (Exception e2) {
                throw new ReflectException(e2);
            }
        }
    }

    public <T> T get(String name) throws ReflectException {
        return field(name).<T>get();
    }

    public Field getDeclaredField(Class<?> clazz, String name) throws NoSuchFieldException {
        Field field = null;
        while (clazz != Object.class) {
            try {
                field = clazz.getDeclaredField(name);
                if (field != null)
                    break;
            } catch (Exception e) {
                clazz = clazz.getSuperclass();
            }
        }
        if (field == null) {
            throw new NoSuchFieldException("name is not found");
        }
        return field;
    }

    public ReflectTool field(String name) throws ReflectException {
        try {

            // Try getting a public field
            Field field = type().getField(name);
            return on(field.get(object));
        } catch (Exception e1) {

            // Try again, getting a non-public field
            try {
                return on(accessible(getDeclaredField(type(), name)).get(object));
            } catch (Exception e2) {
                throw new ReflectException(e2);
            }
        }
    }

    public Map<String, ReflectTool> fields() {
        Map<String, ReflectTool> result = new LinkedHashMap<String, ReflectTool>();

        for (Field field : type().getFields()) {
            if (!isClass ^ Modifier.isStatic(field.getModifiers())) {
                String name = field.getName();
                result.put(name, field(name));
            }
        }

        return result;
    }

    public ReflectTool call(String name) throws ReflectException {
        return call(name, new Object[0]);
    }

    public ReflectTool call(String name, Object... args) throws ReflectException {
        Class<?>[] types = types(args);

        // Try invoking the "canonical" method, i.e. the one with exact
        // matching argument types
        try {
            Method method = exactMethod(name, types);
            return on(method, object, args);
        }

        // If there is no exact match, try to find a method that has a "similar"
        // signature if primitive argument types are converted to their wrappers
        catch (NoSuchMethodException e) {
            try {
                Method method = similarMethod(name, types);
                return on(method, object, args);
            } catch (NoSuchMethodException e1) {
                throw new ReflectException(e1);
            }
        }
    }


    private Method exactMethod(String name, Class<?>[] types) throws NoSuchMethodException {
        final Class<?> type = type();
        try {
            return type.getMethod(name, types);
        } catch (NoSuchMethodException e) {
            return type.getDeclaredMethod(name, types);
        }
    }

    private Method similarMethod(String name, Class<?>[] types) throws NoSuchMethodException {
        final Class<?> type = type();

        for (Method method : type.getMethods()) {
            if (isSimilarSignature(method, name, types)) {
                return method;
            }
        }

        for (Method method : type.getDeclaredMethods()) {
            if (isSimilarSignature(method, name, types)) {
                return method;
            }
        }

        throw new NoSuchMethodException("No similar method " + name + " with params " + Arrays.toString(types)
                + " could be found on type " + type() + ".");
    }


    private boolean isSimilarSignature(Method possiblyMatchingMethod, String desiredMethodName,
                                       Class<?>[] desiredParamTypes) {
        return possiblyMatchingMethod.getName().equals(desiredMethodName)
                && match(possiblyMatchingMethod.getParameterTypes(), desiredParamTypes);
    }

    public ReflectTool create() throws ReflectException {
        return create(new Object[0]);
    }


    public ReflectTool create(Object... args) throws ReflectException {
        Class<?>[] types = types(args);

        // Try invoking the "canonical" constructor, i.e. the one with exact
        // matching argument types
        try {
            Constructor<?> constructor = type().getDeclaredConstructor(types);
            return on(constructor, args);
        }

        // If there is no exact match, try to find one that has a "similar"
        // signature if primitive argument types are converted to their wrappers
        catch (NoSuchMethodException e) {
            for (Constructor<?> constructor : type().getConstructors()) {
                if (match(constructor.getParameterTypes(), types)) {
                    return on(constructor, args);
                }
            }

            throw new ReflectException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public <P> P as(Class<P> proxyType) {
        final boolean isMap = (object instanceof Map);
        final InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                String name = method.getName();

                // Actual method name matches always come first
                try {
                    return on(object).call(name, args).get();
                }

                // [#14] Simulate POJO behaviour on wrapped map objects
                catch (ReflectException e) {
                    if (isMap) {
                        Map<String, Object> map = (Map<String, Object>) object;
                        int length = (args == null ? 0 : args.length);

                        if (length == 0 && name.startsWith("get")) {
                            return map.get(property(name.substring(3)));
                        } else if (length == 0 && name.startsWith("is")) {
                            return map.get(property(name.substring(2)));
                        } else if (length == 1 && name.startsWith("set")) {
                            map.put(property(name.substring(3)), args[0]);
                            return null;
                        }
                    }

                    throw e;
                }
            }
        };

        return (P) Proxy.newProxyInstance(proxyType.getClassLoader(), new Class[]{proxyType}, handler);
    }


    private static String property(String string) {
        int length = string.length();

        if (length == 0) {
            return "";
        } else if (length == 1) {
            return string.toLowerCase();
        } else {
            return string.substring(0, 1).toLowerCase() + string.substring(1);
        }
    }

    private boolean match(Class<?>[] declaredTypes, Class<?>[] actualTypes) {
        if (declaredTypes.length == actualTypes.length) {
            for (int i = 0; i < actualTypes.length; i++) {
                if (!wrapper(declaredTypes[i]).isAssignableFrom(wrapper(actualTypes[i]))) {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }


    @Override
    public int hashCode() {
        return object.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ReflectTool) {
            return object.equals(((ReflectTool) obj).get());
        }

        return false;
    }

    @Override
    public String toString() {
        return object.toString();
    }


    private static ReflectTool on(Constructor<?> constructor, Object... args) throws ReflectException {
        try {
            return on(accessible(constructor).newInstance(args));
        } catch (Exception e) {
            throw new ReflectException(e);
        }
    }


    private static ReflectTool on(Method method, Object object, Object... args) throws ReflectException {
        try {
            accessible(method);

            if (method.getReturnType() == void.class) {
                method.invoke(object, args);
                return on(object);
            } else {
                return on(method.invoke(object, args));
            }
        } catch (Exception e) {
            throw new ReflectException(e);
        }
    }

    private static Object unwrap(Object object) {
        if (object instanceof ReflectTool) {
            return ((ReflectTool) object).get();
        }

        return object;
    }


    private static Class<?>[] types(Object... values) {
        if (values == null) {
            return new Class[0];
        }

        Class<?>[] result = new Class[values.length];

        for (int i = 0; i < values.length; i++) {
            Object value = values[i];
            result[i] = value == null ? Object.class : value.getClass();
        }

        return result;
    }


    private static Class<?> forName(String name) throws ReflectException {
        try {
            return Class.forName(name);
        } catch (Exception e) {
            throw new ReflectException(e);
        }
    }

    public Class<?> type() {
        if (isClass) {
            return (Class<?>) object;
        } else {
            return object.getClass();
        }
    }

    public static Class<?> wrapper(Class<?> type) {
        if (type == null) {
            return null;
        } else if (type.isPrimitive()) {
            if (boolean.class == type) {
                return Boolean.class;
            } else if (int.class == type) {
                return Integer.class;
            } else if (long.class == type) {
                return Long.class;
            } else if (short.class == type) {
                return Short.class;
            } else if (byte.class == type) {
                return Byte.class;
            } else if (double.class == type) {
                return Double.class;
            } else if (float.class == type) {
                return Float.class;
            } else if (char.class == type) {
                return Character.class;
            } else if (void.class == type) {
                return Void.class;
            }
        }

        return type;
    }

    /**
     * according method and class to get method
     */
    public static Method getMethodByClassAndName(Class c, String methodName) throws Lc4eException {
        Method[] methods = c.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        throw new Lc4eException("Can not find Method [" + methodName + "] in Class");
    }

    public static Method getMethodByClassAndNameBase(Class c, String methodName) throws Lc4eException {
        Method[] methods = c.getMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        throw new Lc4eException("Can not find Method [" + methodName + "] in Class");
    }

    public static <T> T getAnnotationByMethod(Method method, Class<T> annoClass) {
        Annotation all[] = method.getAnnotations();
        for (Annotation annotation : all) {
            if (annotation.annotationType() == annoClass) {
                return (T) annotation;
            }
        }
        return null;
    }

    public static String parseDefaultValue(String value) {
        return Const.DEFAULT_NONE.equals(value) ? null : value;
    }

    public static Field getFieldByClass(Class clz, String name) {
        Field[] fields = clz.getFields();
        for (int i = 0, len = fields.length; i < len; i++) {
            if (fields[i].getName().equals(name)) {
                return fields[i];
            }
        }
        return null;
    }

    public static List<String> getKeyWordConst(String... args) {
        Field[] fields = Const.class.getDeclaredFields();
        List<String> list = new ArrayList<>();
        try {
            for (int i = 0, len = fields.length; i < len; i++) {
                String name = fields[i].getName();
                for (int j = 0, l = args.length; j < l; j++) {
                    if (name.startsWith(args[j])) {
                        list.add(fields[i].get(null).toString());
                        break;
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static Object getFieldByObjectAndFileName(Controller controller, Class type, String fileName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        String tmp[] = fileName.split("\\.");
        Object arg = controller.getModel(type, tmp[0]);
        if (arg == null) {
            return null;
        }

        if (Model.class.isAnnotationPresent(type)) {
            for (int i = 1; i < tmp.length; i++) {
                Method method = arg.getClass().getMethod("get", String.class);
                arg = method.invoke(arg, tmp[i]);
            }
        } else {
            for (int i = 1; i < tmp.length; i++) {
                Method method = arg.getClass().getMethod(getGetterNameByFieldName(tmp[i]));
                arg = method.invoke(arg);
            }
        }

        return arg;
    }

    /**
     * get field get function
     */
    public static String getGetterNameByFieldName(String fieldName) {
        return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }


    public static void setParameter(String key, String value, Controller controller) throws NoSuchFieldException, IllegalAccessException {

        Request innerRequest = (Request) controller.getRequest();
        innerRequest.getParameters().put(key, value);
    }


    public static void setParameter(String[] key, String[] value, Controller controller) throws NoSuchFieldException, IllegalAccessException {

        if (key.length != value.length) {
            return;
        }
        Request innerRequest = (Request) controller.getRequest();

        for (int i = 0, len = key.length; i < len; i++) {
            innerRequest.getParameters().put(key[i], value[i]);
        }

    }

    public static Map<Class<? extends Annotation>, Annotation> getAnnotationsMap(Method method) {
        Map<Class<? extends Annotation>, Annotation> ret = new HashMap<>();
        Annotation[] ans = method.getAnnotations();
        for (int i = 0, len = ans.length; i < len; i++) {
            ret.put(ans[i].annotationType(), ans[i]);
        }
        return ret;
    }


    /**
     * exclude method in Controller
     *
     * @return
     */
    public static Set<String> buildExcludedMethodName(Class... clzes) {
        Set<String> excludedMethodName = new HashSet<String>();
        for (Class clz : clzes) {
            Method[] methods = clz.getMethods();
            for (Method m : methods) {
                if (m.getParameterTypes().length == 0)
                    excludedMethodName.add(m.getName());
            }
        }
        return excludedMethodName;
    }

    public static <T> void WrapperMethodEnhancer(Enhancer enhancer, T target) {
        Field[] fields = target.getClass().getFields();
    }
}