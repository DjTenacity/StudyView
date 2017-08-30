package com.gdj.myview.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.gdj.myview.utils.HttpConnectionUtils;

/**
 * Comment:
 * 无需创建线程以及开启线程,会自动停止并同时会返回一个值,onCreate里面已经创建了线程
 * 也不需要stopSelf关闭服务(自动关闭服务在onHandleIntent方法后面)
 *
 * @author : DJ鼎尔东/ 1757286697@qq.com
 * @version : ${user} 1.0
 * @date : 2017/8/30 16:05
 */
public class MyIntentService extends IntentService {
    private final String TAG = "IntentService";
    private final String url = "http://img.ui.cn/data/file/6/8/8/1346886.jpg";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public MyIntentService(String name) {
        super(name);
    }
//启动该应用,

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        HttpConnectionUtils.downloadFile(url);
    }
}
