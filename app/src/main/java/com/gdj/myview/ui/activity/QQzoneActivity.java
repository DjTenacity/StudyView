package com.gdj.myview.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.gdj.myview.R;
import com.gdj.myview.view.ParallaxListView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：${LoveDjForever} on 2017/7/14 15:43
 *  * 邮箱： @qq.com
 */

public class QQzoneActivity extends AppCompatActivity {

    @BindView(R.id.lv)
    ParallaxListView listView;

    ImageView iv, iHeardV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qq_zone);
        ButterKnife.bind(this);

        View view = View.inflate(this, R.layout.layout_heard_img, null);
        iv = (ImageView) view.findViewById(R.id.iv_heard_image);
        iHeardV = (ImageView) view.findViewById(R.id.iv_icon);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //21以上   三种系统带的：滑动效果(Slide)、展开效果Explode、渐变显示隐藏效果Fade
            Slide slide = new Slide();
            slide.setDuration(500);
            getWindow().setExitTransition(slide);//出去的动画
            getWindow().setEnterTransition(slide);//进来的动画
            //如果有共享元素,可以设置共享元素,就会按照共享元素,其他的子view不受影响
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                new String[]{
                        "星期一", "星期二", "星期一", "星期二", "星期一", "星期二", "星期一", "星期二",
                        "星期一", "星期二", "星期一", "星期二", "星期一", "星期二", "星期一", "星期二"
                });
        listView.addHeaderView(view);
        listView.setZoomImageView(iv);
        listView.setHeardImageView(iHeardV);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    public void jjjjj(View v) {


        //当前的activity   共享元素哪一个view   共享元素的名称(必须相同)  android:transitionName="iv_heard_image"   <View,String>
        ActivityOptionsCompat optionsCompat =
                //  ActivityOptionsCompat.makeSceneTransitionAnimation ( this, Pair.create((View)iv,"iv_heard_image"),Pair.create((View)iv,"iv_heard_image") ) ;//多个共享元素
                ActivityOptionsCompat.makeSceneTransitionAnimation(QQzoneActivity.this, iv, "iv_heard_image");

        //最低兼容到16
        Intent intent = new Intent(QQzoneActivity.this, MyAnimationActivity.class);
        startActivity(intent, optionsCompat.toBundle());
    }

    public void back(View v) {
        //onBackPressed();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) finishAfterTransition();
    }
}

