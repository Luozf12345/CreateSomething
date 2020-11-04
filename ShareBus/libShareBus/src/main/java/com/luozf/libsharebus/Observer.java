package com.luozf.libsharebus;

/**
 * 消息观察者。
 * 采用创建者模式，将构建解耦。
 * 用Observer再封装MessageHandler，不直接回调，是为了方便添加参数。
 */
public class Observer {

    /**
     * 监听的消息Key，创建之后不可更改
     */
    private final String key;
    /**
     * 消息处理者
     */
    private MassageHandler handler;
    /**
     * 是否是粘性消息
     */
    private boolean isSticky;

    private Observer(Builder builder){
        key = builder.key;
        handler = builder.handler;
        isSticky = builder.isSticky;
    }

    public String getKey() {
        return key;
    }

    public boolean isSticky() {
        return isSticky;
    }

    public void call(String message) {
        if (handler != null){
            handler.handleMessage(message);
        }
    }

    /**
     * 构建者
     */
    public static class Builder{
        private final String key;
        private MassageHandler handler;
        private boolean isSticky;

        public Builder(String key){
            this.key = key;
        }

        public Builder setHandler(MassageHandler handler) {
            this.handler = handler;
            return this;
        }

        public Builder setSticky(boolean sticky) {
            isSticky = sticky;
            return this;
        }

        public Observer builde(){
            return new Observer(this);
        }
    }
}
