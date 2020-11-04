package com.luozf.libsharebus;

import com.luozf.libsharebus.utils.StringUtil;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 真正实现消息通信逻辑，让ShareBus类代码简洁，方便查看接口
 */
public class ShareBusDeleget {

    /**
     * 采用CopyOnWriteArrayList，线程安全
     */
    private CopyOnWriteArrayList<Observer> observers = new CopyOnWriteArrayList<>();

    public void register(Observer observer) {
        if (observer == null) {
            return;
        }
        if (observer.isSticky()){
            // TODO:检查粘性事件
        }
        observers.add(observer);
    }

    public void unRegister(Observer observer) {
        observers.remove(observer);
    }

    public void post(Event event) {
        if (event == null || StringUtil.isEmpty(event.getKey())){
            return;
        }
        //此处采用遍历好还是hash链表好？
        // 遍历省空间，方便实现优先级。但是当观察者太多，耗时太多
        // hash链表速度快，优先级可在链表中实现。
        // 如果要做消息分级，需要树形结构实现。
        for (Observer observer : observers){
            if (event.getKey().equals(observer.getKey())){
                observer.call(event.getMessage());
            }
        }
        // 使用过的事件可以回收
        EventPool.getInstantce().rycleEvent(event);
    }

    public void postSticky(Event event) {
        // TODO: 待实现
    }
}
