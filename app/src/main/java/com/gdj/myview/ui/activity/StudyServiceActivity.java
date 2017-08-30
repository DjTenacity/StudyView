package com.gdj.myview.ui.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.gdj.myview.R;
import com.gdj.myview.service.DownloadService;
import com.gdj.myview.service.MyBindService;
import com.gdj.myview.service.MyIntentService;

/**
 * Comment: 服务
 * 启动service两种方式 startService 和 bindService
 *
 * @author : DJ鼎尔东/ 1757286697@qq.com
 * @version : ${user} 1.0
 * @date : 2017/8/30 15:26
 */
public class StudyServiceActivity extends AppCompatActivity {
    TextView tv_des;
    MyBindService myBindService;
    boolean mBind = false;//默认是不绑定的
    private MyBindService.LocalBinder localBinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_study_service);

        tv_des = (TextView) findViewById(R.id.tv_des);
    }

    public void onCLick(View v) {
        Intent intent = new Intent(this, DownloadService.class);
        startService(intent);
    }

    public void MyIntentService(View v) {
        Intent intent = new Intent(this, MyIntentService.class);
        startService(intent);
    }

    //绑定service
    public void bind1(View v) {
        Intent intent = new Intent(this, MyBindService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    // 调用service的方法
    public void bind2(View v) {
        if (mBind) {
            tv_des.setText(myBindService.getRandom() + "");
        }
    }

    public void data(View v) {
        //往service中传递值得对象
        Parcel data = Parcel.obtain();
        data.writeInt(25);
        data.writeString("jackkk");
        Parcel reply = Parcel.obtain();
        try {
            localBinder.transact(IBinder.LAST_CALL_TRANSACTION,data,reply,0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Log.w("Activity","readString:"+reply.readString()+"readInt:"+reply.readInt());
    }

    //ServiceConnection 客户端和服务端的桥梁
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            localBinder = (MyBindService.LocalBinder) service;
            myBindService = localBinder.getService();

            mBind = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBind = false;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
//        Intent intent = new Intent(this, MyBindService.class);
//        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBind) {
            unbindService(connection);
            mBind = false;
        }
    }
}
