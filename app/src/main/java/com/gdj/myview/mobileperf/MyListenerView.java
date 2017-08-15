package com.gdj.myview.mobileperf;


import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.gdj.myview.mobileperf.ListenerCollector;

/**
 * Comment:
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/8/13
 */
public class MyListenerView extends View {
    public MyListenerView(Context context) {
        this(context,null);
    }

    public MyListenerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyListenerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }
    //activity每次旋转都会调用
    private void init() {
        ListenerCollector collector=new ListenerCollector();
        collector.setsListener(this,myListener);
    }

    public interface MyListener{
      public void myListenerCallback();
    }

    private  MyListener  myListener=new MyListener(){

        @Override
        public void myListenerCallback() {
            Log.e("MyListener","MyListener");
        }
    };
}
