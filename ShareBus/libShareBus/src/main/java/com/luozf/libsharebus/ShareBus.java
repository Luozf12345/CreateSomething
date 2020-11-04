package com.luozf.libsharebus;

/**
 * 消息通信的门面
 */
public class ShareBus {

    private ShareBusDeleget deleget = new ShareBusDeleget();

    private static volatile ShareBus instantce;

    public static ShareBus getInstantce(){
        synchronized (ShareBus.class){
            if (instantce == null){
                synchronized (ShareBus.class){
                    instantce = new ShareBus();
                }
            }
            return instantce;
        }
    }

    private ShareBus(){}

    public void register(Observer observer){
        deleget.register(observer);
    }

    public void unRegister(Observer observer){
        deleget.unRegister(observer);
    }

    public void post(Event event){
        deleget.post(event);
    }

    public void postSticky(Event event){
        deleget.postSticky(event);
    }
}
