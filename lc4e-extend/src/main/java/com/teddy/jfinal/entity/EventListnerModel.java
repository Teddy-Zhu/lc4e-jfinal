package com.teddy.jfinal.entity;

import com.teddy.jfinal.annotation.Event;
import com.teddy.jfinal.plugin.support.event.EventListener;

import java.lang.annotation.Annotation;

/**
 * Created by teddyzhu on 16/7/3.
 */
public class EventListnerModel {

    int order;

    boolean async;

    boolean asyncWait;

    EventListener listener;

    public int getOrder() {
        return order;
    }

    public boolean isAsync() {
        return async;
    }

    public boolean isAsyncWait() {
        return asyncWait;
    }

    public EventListnerModel(int order, boolean async, boolean asyncWait) {
        this.order = order;
        this.async = async;
        this.asyncWait = asyncWait;
    }

    public EventListnerModel(Annotation annotation, EventListener eventListener) {
        Event event = (Event) annotation;
        this.order = event.order();
        this.async = event.async();
        this.asyncWait = event.asyncWait();
        this.listener = eventListener;
    }

    public EventListener getListener() {
        return listener;
    }
}
