package com.gdj.myview.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.gdj.myview.utils.HttpConnectionUtils;

/**
 * Comment:Service 运行在主线程中
 *
 * @author : DJ鼎尔东/ 1757286697@qq.com
 * @version : ${user} 1.0
 * @date : 2017/8/30 15:17
 */
public class DownloadService extends Service {

    private final String TAG = "downloadservice";
    private final String url = "http://img.ui.cn/data/file/6/8/8/1346886.jpg";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // intent.getStringExtra("name");

        //不能直接使用http协议访问网络,Service 运行在主线程中
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                if (msg.what == 1) {
                    stopSelf();
                    Toast.makeText(getApplicationContext(), "下载图片完成", Toast.LENGTH_SHORT).show();
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpConnectionUtils.downloadFile(url);
                Message message = Message.obtain();
                message.what = 1;
                //关闭service
                handler.sendMessage(message);
            }
        }).start();

        return super.onStartCommand(intent, flags, startId);

    }

}
