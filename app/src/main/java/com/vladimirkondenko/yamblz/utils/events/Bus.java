package com.vladimirkondenko.yamblz.utils.events;

import org.greenrobot.eventbus.EventBus;

/**
 * EventBusWrapper
 */
public class Bus {

    public static void subscribe(Object subscriber) {
        getBus().register(subscriber);
    }

    public static void post(Object event) {
        getBus().post(event);
    }

    public static void unsubscribe(Object subscriber) {
        getBus().unregister(subscriber);
    }

    private static EventBus getBus() {
        return EventBus.getDefault();
    }

}
