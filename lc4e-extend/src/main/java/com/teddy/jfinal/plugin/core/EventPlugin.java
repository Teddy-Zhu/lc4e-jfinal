package com.teddy.jfinal.plugin.core;

import com.jfinal.config.*;
import com.teddy.jfinal.annotation.Event;
import com.teddy.jfinal.common.Dict;
import com.teddy.jfinal.entity.EventListnerModel;
import com.teddy.jfinal.interfaces.IPlugin;
import com.teddy.jfinal.plugin.CustomPlugin;
import com.teddy.jfinal.plugin.support.event.EventListener;
import com.teddy.jfinal.plugin.support.event.EventObject;
import com.teddy.jfinal.tools.PropTool;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by teddyzhu on 16/7/1.
 */
public class EventPlugin implements IPlugin {

    private static Map<Type, List<EventListener>> eventMap = new HashMap<>();

    private static Map<Type, List<EventListnerModel>> eventInstance = new HashMap<>();

    // thread pool
    private static ExecutorService pool = null;


    @Override
    public void init(Plugins me) {

    }

    @Override
    public void init(Routes me) {

    }

    @Override
    public void init(Constants me) {

    }

    @Override
    public void init(Interceptors me) {

    }

    @Override
    public void init(Handlers me) {

    }

    @Override
    public boolean start(CustomPlugin configPlugin) {

        PropTool prop = configPlugin.getProp();

        pool = Executors.newFixedThreadPool(prop.getInt(Dict.EVENT_THREAD_SIZE, 1));

        configPlugin.getAnnotationClass(Event.class).forEach(aClass -> {
            if (EventListener.class.isAssignableFrom(aClass)) {
                try {
                    EventListener listener = (EventListener) aClass.newInstance();

                    Annotation event = aClass.getAnnotation(Event.class);
                    Type type = ((ParameterizedType) aClass.getGenericInterfaces()[0]).getActualTypeArguments()[0];

                    addEvent(type, listener);
                    addEventInstance(type, new EventListnerModel(event, listener));

                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

        return true;
    }

    @Override
    public boolean stop(CustomPlugin configPlugin) {
        return true;
    }

    private void addEvent(Type event, EventListener listener) {

        if (eventMap.containsKey(event)) {
            eventMap.get(event).add(listener);
        } else {
            eventMap.put(event, new ArrayList<EventListener>() {
                private static final long serialVersionUID = -5977539826815408281L;

                {
                    add(listener);
                }
            });
        }
    }

    private void addEventInstance(Type event, EventListnerModel model) {
        if (eventInstance.containsKey(event)) {
            eventInstance.get(event).add(model);
        } else {
            eventInstance.put(event, new ArrayList<EventListnerModel>() {
                private static final long serialVersionUID = -5977539826815408281L;

                {
                    add(model);
                }
            });
        }
    }

    public static void Excute(EventObject event) {
        List<EventListnerModel> listenerList = eventInstance.get(event.getClass());

        List<Future> asyncTask = new ArrayList<>();
        for (EventListnerModel model : listenerList) {
            if (null != pool && model.isAsync()) {
                Future task = pool.submit(() -> model.getListener().postEvent(event));
                if (model.isAsyncWait()) {
                    asyncTask.add(task);
                }
            } else {
                model.getListener().postEvent(event);
            }
        }

        asyncTask.forEach(task -> {
            try {
                task.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
    }
}
