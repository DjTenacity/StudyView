package com.gdj.myview.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.gdj.myview.R;
import com.gdj.myview.ui.activity.SplashActivity;
import com.gdj.myview.ui.fragment.ParallaxFragment;
import com.gdj.myview.weight.ParallaxPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导页的最外层布局.
 * 在代码中直接new一个Custom View实例的时候,会调用第一个构造函数
 * 在xml布局文件中调用Custom View的时候,会调用第二个构造函数 .
 * 在xml布局文件中调用Custom View,并且Custom View标签中还有自定义属性时,这里调用的还是第二个构造函数.
 * 在布局layout中使用，但是会有style---第三个构造函数
 */

public class ParallaxContainer extends FrameLayout implements ViewPager.OnPageChangeListener {

    private List<ParallaxFragment> fragments;
    private ParallaxPagerAdapter adapter;

    ImageView iv_man;

    public ParallaxContainer(Context context) {
        this(context, null);
    }

    public ParallaxContainer(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ParallaxContainer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
    }

    /**
     * 指定引导页的所有页面布局文件
     **/
    public void setUp(int... ChildIds) {
        //根据布局文件数组，初始化所有的fragment
        fragments = new ArrayList<ParallaxFragment>();
        for (int i = 0; i < ChildIds.length; i++) {

            ParallaxFragment f = ParallaxFragment.getInstance(i, ChildIds[i]);
            fragments.add(f);
        }

        //实例化适配器
        SplashActivity activity = (SplashActivity) getContext();
        adapter = new ParallaxPagerAdapter(activity.getSupportFragmentManager(), fragments);


        //实例化viewpager
        ViewPager vp = new ViewPager(getContext());
        vp.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        vp.setId(R.id.parallax_pager);//部分控件不给id会出问题,重复性的id会出现覆盖
        //绑定
        vp.setAdapter(adapter);

        vp.setOnPageChangeListener(this);


        addView(vp,0);
    }

    private float containerWidth;

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        containerWidth = getWidth();
        ParallaxFragment inFragment = null;

        //在翻页的过程中，不断根据视图的标签中对应的动画参数，改变视图的位置或者透明度
        //获取到进入的页面
        try {
            inFragment = fragments.get(position - 1);
        } catch (Exception e) {
        }
        // 还有获取到退出的页面
        ParallaxFragment outFragment = null;
        try {
            outFragment = fragments.get(position);
        } catch (Exception e) {
        }
        if (inFragment != null) {
            //获取fragment上所有的视图，实现动画效果
            List<View> inViews = inFragment.getParallaxViews();
            if (inViews != null) {
                for (View view : inViews) {
                    Log.w("tag.xIn", inViews.size() + "/////");
                    //获取标签，从标签上获取所有的动画参数
                    ParallaxViewTag tag = (ParallaxViewTag) view.getTag(R.id.parallax_view_tag);
                    if (tag == null) continue;
                    //(containerWidth-positionOffsetPixels) 最后为0，containerWidth 为宽
                    //相对位置 left，根据xIn转换快慢

                    Log.w("tag.xIn", tag.xIn + "/////" + tag.yIn);
                    view.setTranslationX((containerWidth - positionOffsetPixels) * tag.xIn);
                    //top  根据滑动的距离来设定
                    view.setTranslationY((containerWidth - positionOffsetPixels) * tag.yIn);
                    // fade in
                    view.setAlpha(1.0f - (containerWidth - positionOffsetPixels) * tag.alphaIn / containerWidth);
                }
            }

        }
        if (outFragment != null) {
            List<View> outViews = outFragment.getParallaxViews();
            if (outViews != null) {
                for (View view : outViews) {
                    Log.w("tag.xIn", outViews.size() + "// ss");

                    //获取标签，从标签上获取所有的动画参数
                    ParallaxViewTag tag = (ParallaxViewTag) view.getTag(R.id.parallax_view_tag);
                    if (tag == null) continue;
                    Log.w("tag.xIn", tag.xOut + "/////" + tag.yOut);

                    //(containerWidth-positionOffsetPixels) 最后为0，containerWidth 为宽
                    //相对位置 left，根据xIn转换快慢
                    view.setTranslationX((0 - positionOffsetPixels) * tag.xOut);
                    //top  根据滑动的距离来设定
                    view.setTranslationY((0 - positionOffsetPixels) * tag.yOut);

                    view.setAlpha(1.0f - positionOffsetPixels * tag.alphaOut / containerWidth);
                }
            }

        }
    }

    @Override
    public void onPageSelected(int position) {
        if (position == adapter.getCount() - 1) {
            iv_man.setVisibility(GONE);
        } else {
            iv_man.setVisibility(VISIBLE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        AnimationDrawable animationDrawable = (AnimationDrawable) iv_man.getBackground();
        switch (state) {
            case ViewPager.SCROLL_STATE_DRAGGING://完成
                animationDrawable.start();
                break;
            case ViewPager.SCROLL_STATE_IDLE://完成
                animationDrawable.stop();
                break;
        }

    }

    public void setIv_man(ImageView iv_man) {
        this.iv_man = iv_man;
    }
}
