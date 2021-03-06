package com.luozf.eventbusdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.meta.SubscriberInfo;
import org.greenrobot.eventbus.meta.SubscriberInfoIndex;

/**
 * 1.builder创建者，门面模式，单例模式，public EventBus(DEFAULT_BUILDER)。
 * 2.方法职责分明
 * 3.CopyOnWriteArrayList，PendingPostQueue，同步锁尽量小的代码块
 * 5.HashMap缓存提高性能
 * 6.观察者优先级（处理函数的，不是事件的），线程模型，粘性
 * 7.ThreadLocal绑定线程变量
 * 8.为什么post时需要eventQueue？？
 * 9.引用传递，可双向修改
 * 10.既然有优先级，是否可以有序，消费掉事件？
 * 11.index索引是啥？通过编译时生成索引，注册时先去索引里查找，索引没有，才通过反射获取方法。
 *
 * 原理：
 * 1.如何做的？
 * 答：观察者模式，标签反射，线程切换，粘性消息。
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        EventBus.builder().addIndex(new SubscriberInfoIndex() {
            @Override
            public SubscriberInfo getSubscriberInfo(Class<?> subscriberClass) {
                return null;
            }
        });
    }

    @Subscribe
    public void onEvent123(AnyEventType event) {
        Toast.makeText(this,"123"+ event.message,Toast.LENGTH_SHORT).show();
    };

    @Subscribe
    public void onEvent456(AnyEventType event) {
        Toast.makeText(this,"456"+ event.message,Toast.LENGTH_SHORT).show();
    };

    @Subscribe
    public void onAnotherEvent(AnotherEventType event) {
        Toast.makeText(this,"onAnotherEvent"+ event.message,Toast.LENGTH_SHORT).show();
    };

    public void onClick(View view) {
        AnyEventType event = new AnyEventType("event test");
        EventBus.getDefault().post(event);
    }

    public void register(View view) {
        EventBus.getDefault().register(this);
    }

    public void unRegister(View view) {
        EventBus.getDefault().unregister(this);
    }

    public void sendEvent(View view) {
        AnotherEventType eventType = new AnotherEventType("Another envent type");
        EventBus.getDefault().post(eventType);
    }
}
