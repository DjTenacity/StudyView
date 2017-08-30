package com.gdj.myview.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Random;

/**
 * Comment:   BindService
 *
 * @author : DJ鼎尔东/ 1757286697@qq.com
 * @version : ${user} 1.0
 * @date : 2017/8/30 18:17
 */
public class MyBindService extends Service {

    private final Binder binder = new LocalBinder();
    private final Random random = new Random();//产生一个随机数

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {//必须返回当前 Bind的一个引用
        return binder;
    }

    public int getRandom() {
        return random.nextInt(30);
    }


    public class LocalBinder extends Binder {
        //必须有一个公有地方法来返回当前的service的实例

        public MyBindService getService() {
            return MyBindService.this;
        }

    }
}
