package com.teddy.jfinal.plugin.support.event;

/**
 * Created by teddyzhu on 16/7/1.
 */
public class EventObject extends java.util.EventObject {
    /**
     * Constructs a prototypical EventObject.
     *
     * @param source The object on which the EventObject initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public EventObject(Object source) {
        super(source);
    }
}
