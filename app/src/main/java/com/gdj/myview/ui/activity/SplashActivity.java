package com.gdj.myview.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.gdj.myview.R;
import com.gdj.myview.view.ParallaxContainer;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/16.
 */

public class SplashActivity extends AppCompatActivity {
    @BindView(R.id.parallax_container)
    ParallaxContainer  container;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_splash);

        ButterKnife.bind(this);

        container.setUp(new int[]{

        });
    }
}
