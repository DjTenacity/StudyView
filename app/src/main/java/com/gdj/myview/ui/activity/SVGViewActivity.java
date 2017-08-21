package com.gdj.myview.ui.activity;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.gdj.myview.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */

public class SVGViewActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawerlayout)
    DrawerLayout drawerlayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawerlayout_svg);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle drawerToggle=new ActionBarDrawerToggle(this,drawerlayout,toolbar, R.string.drawer_open, R.string.drawer_close);

        drawerToggle.syncState();
        //给侧滑控件设置监听
//		drawerlayout.setDrawerListener(drawerToggle);

        drawerlayout.setDrawerListener(new DrawerLayout.DrawerListener() {

            @Override
            public void onDrawerStateChanged(int newState) {
                // 状态发生改变

            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                // 滑动的过程当中不断地回调 slideOffset：0~1
                View content = drawerlayout.getChildAt(0);
                View menu = drawerView;
                float scale = 1-slideOffset;//1~0
                float leftScale = (float) (1-0.3*scale);
                float rightScale = (float) (0.7f+0.3*scale);//0.7~1
                menu.setScaleX(leftScale);//1~0.7
                menu.setScaleY(leftScale);//1~0.7

                content.setScaleX(rightScale);
                content.setScaleY(rightScale);
                content.setTranslationX(menu.getMeasuredWidth()*(1-scale));//0~width

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // 打开

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                // 关闭

            }
        });


    }


    public void anim1(View v) {
        ImageView iv = (ImageView) v;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Drawable drawable = iv.getDrawable();
            if (drawable instanceof Animatable) {
                ((Animatable) drawable).start();
            }
        }
    }
}
