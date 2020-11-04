package com.luozf.sharebus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.luozf.libsharebus.Event;
import com.luozf.libsharebus.MassageHandler;
import com.luozf.libsharebus.Observer;
import com.luozf.libsharebus.ShareBus;

public class MainActivity extends AppCompatActivity {

    private Observer mObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void register(View view) {
        // 如果一个界面要监听多个事件，需要保存观察者太多，需要批量注册
        if (mObserver == null) {
            mObserver = new Observer.Builder("test")
                    .setHandler(new MassageHandler() {
                        @Override
                        public void handleMessage(String message) {
                            // 内部类可能导致内存泄漏，必须及时注销
                            Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                        }
                    }).builde();
        }
        ShareBus.getInstantce().register(mObserver);
    }

    public void unRegister(View view) {
        ShareBus.getInstantce().unRegister(mObserver);
        mObserver = null;
    }

    int count = 1;
    public void postEvent(View view) {
        Event event = new Event.Builder("test")
                .setMessage("msseage:" + count ++)
                .obtain();
        ShareBus.getInstantce().post(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mObserver != null) {
            ShareBus.getInstantce().unRegister(mObserver);
            mObserver = null;
        }
    }
}
