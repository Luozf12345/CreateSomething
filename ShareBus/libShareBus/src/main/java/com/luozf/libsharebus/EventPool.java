package com.luozf.libsharebus;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 事件回收池
 */
public class EventPool {

    private static final int MAX_EVENT_SIZE = 50;

    private Queue<Event> events = new ConcurrentLinkedQueue<>();

    private static volatile EventPool instantce;

    public static EventPool getInstantce(){
        synchronized (EventPool.class){
            if (instantce == null){
                synchronized (EventPool.class){
                    instantce = new EventPool();
                }
            }
            return instantce;
        }
    }

    private EventPool(){}

    /**
     * 回收Event
     * @param event
     */
    public void rycleEvent(Event event){
        if (event == null || events.size() >= MAX_EVENT_SIZE){
            // 如果超限，则不再存储
            return;
        }
        events.offer(event);
    }

    /**
     * 获取Event,如果没有，则返回空
     * @return
     */
    public Event getEvent(){
        if (events.isEmpty()){
            return null;
        }
        return events.poll();
    }
}
