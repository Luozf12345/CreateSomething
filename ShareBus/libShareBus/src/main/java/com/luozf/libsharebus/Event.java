package com.luozf.libsharebus;

/**
 * 事件。
 * 是否可复用？如果复用，可能导致粘性事件重复，粘性的事件不可复用。
 * 如果只想修改mesage，不修改key，该怎么做？工厂还是builder?
 * 是否可双向修改？不能。
 */
public class Event {
    /**
     * 消息key，和Observer的key对应
     */
    private String key;

    /**
     * 需要传递的信息
     */
    private String message;

    private Event(Builder builder){
        init(builder);
    }

    private void init(Builder builder){
        key = builder.key;
        message = builder.message;
    }

    public String getKey() {
        return key;
    }

    public String getMessage() {
        return message;
    }

    public static class Builder{
        private String key;
        private String message;

        public Builder(String key){
            this.key = key;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * 构建一个新的Event对象
         * @return
         */
        public Event builde(){
            return new Event(this);
        }

        /**
         * 推荐使用。如果有，则复用回收的Event对象。
         * @return
         */
        public Event obtain(){
            Event event = EventPool.getInstantce().getEvent();
            if (event == null){
                event = new Event(this);
            } else {
                event.init(this);
            }
            return event;
        }
    }
}
