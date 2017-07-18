package com.gdj.myview.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.gdj.myview.R;
import com.gdj.myview.view.ParallaxContainer;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/16.
 */

public class SplashActivity extends AppCompatActivity {
    ParallaxContainer container;
    ImageView iv_man;
    ImageView rl_weibo;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        container = (ParallaxContainer) findViewById(R.id.parallax_container);
        iv_man = (ImageView) findViewById(R.id.iv_man);


        container.setUp(new int[]{
                R.layout.view_intro_1,
                R.layout.view_intro_2,
                R.layout.view_intro_3,
                R.layout.view_intro_4,
                R.layout.view_intro_5,
                R.layout.view_login
        });

        rl_weibo = (ImageView) findViewById(R.id.rl_weibo);
//        rl_weibo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(SplashActivity.this, MainActivity.class));
//                SplashActivity.this.finish();
//            }
//        });
        iv_man.setBackgroundResource(R.drawable.man_run);
        container.setIv_man(iv_man);
    }
}
