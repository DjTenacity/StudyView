package com.gdj.myview.mobileperf;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 只是用来展示内存泄漏
 **/
public class MobileActivity extends AppCompatActivity {
    int a = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        final TextView tv =  (TextView) findViewById(R.id.tv);
//        tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int b = a;
//            }
//        });//监听执行完回收对象
        //add监听，放到集合里面
//        tv.getViewTreeObserver().addOnWindowFocusChangeListener(new ViewTreeObserver.OnWindowFocusChangeListener() {
//            @Override
//            public void onWindowFocusChanged(boolean b) {
//                //监听view的加载，view加载出来的时候，计算他的宽高等。
//
//                //计算完后，一定要移除这个监听
//                tv.getViewTreeObserver().removeOnWindowFocusChangeListener(this);
//            }
//        });

//        SensorManager sensorManager = getSystemService(SENSOR_SERVICE);
//        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ALL);
//        sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_FASTEST);
//        //不需要用的时候记得移除监听
//        sensorManager.unregisterListener(listener);


        CommUtil commUtil = CommUtil.getInstance(this);
        //解决办法：使用Application的上下文
        //CommonUtil生命周期跟MobileActivity不一致，而是跟Application进程同生同死。
//        CommUtil commUtil = CommUtil.getInstance(getApplicationContext());


//        MyView myView = new MyView(this);
//        setContentView(myView);


//        loadData();

    }

    //加上static，里面的匿名内部类就变成了静态匿名内部类
    public static void loadData() {//隐士持有MobileActivity实例。MobileActivity.this.a
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while(true){
//                    try {
//                        //int b=a;
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();

//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                while(true){
//                    try {
//                        //int b=a;
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }, 20000);//这个线程延迟5分钟执行
        //activity onDestroy把timer.cancel掉然后赋空


//        mHandler.sendEmptyMessage(0);
//        mHandler.sendMessageAtTime(msg,10000);//atTime

    }
    //错误的示范：
    //mHandler是匿名内部类的实例，会引用外部对象MobileActivity.this。如果Handler在Activity退出的时候，它可能还活着，这时候就会一直持有Activity。
//    private Handler mHandler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what){
//                case 0:
//                    //加载数据
//                    break;
//
//            }
//        }
//    };

    //解决方案：
    private static class MyHandler extends Handler {
        //        private MobileActivity MobileActivity;//直接持有了一个外部类的强引用，会内存泄露
        private WeakReference<MobileActivity> mobileActivity;//设置软引用保存，当内存一发生GC的时候就会回收。

        public MyHandler(MobileActivity mobileActivity) {
            this.mobileActivity = new WeakReference<MobileActivity>(mobileActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MobileActivity main = mobileActivity.get();
            if (main == null || main.isFinishing()) {
                return;
            }
            switch (msg.what) {
                case 0:
                    //加载数据
//                    MobileActivity.this.a;//有时候确实会有这样的需求：需要引用外部类的资源。怎么办？
                    int b = main.a;
                    break;

            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
//        ListenerCollector.clearListeners();
    }
}
