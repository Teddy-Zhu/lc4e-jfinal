package com.teddy.jfinal.plugin.support.event;

/**
 * Created by teddyzhu on 16/7/1.
 */
public interface EventListener<E extends EventObject> extends java.util.EventListener {

    void postEvent(E event);
}
