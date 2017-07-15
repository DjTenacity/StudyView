package com.gdj.myview.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.gdj.myview.R;
import com.gdj.myview.view.ContentView;
import com.gdj.myview.view.SplashView;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/15.
 */

public class YahooNewsActivity extends AppCompatActivity {

    private FrameLayout fl;
    private SplashView splashView;
    private ContentView contentView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        ButterKnife.bind(this);

        //帧布局
        fl = new FrameLayout(this);
        contentView = new ContentView(this);
        splashView = new SplashView(this);

        fl.addView(contentView);
        fl.addView(splashView);

        setContentView(fl);

        //开启splashView   模拟后台加载数据
        startLoad();
    }

    Handler handler = new Handler();

    private void startLoad() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //数据加载完成后，执行后面的动画，让contenView展示出来
                splashView.splashAndDisappear();
            }
        }, 2000);

    }
}
