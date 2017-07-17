package com.gdj.myview.view;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.gdj.myview.ui.fragment.ParallaxFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导页的最外层布局.
 * 在代码中直接new一个Custom View实例的时候,会调用第一个构造函数
 * 在xml布局文件中调用Custom View的时候,会调用第二个构造函数 .
 * 在xml布局文件中调用Custom View,并且Custom View标签中还有自定义属性时,这里调用的还是第二个构造函数.
 * 也就是说,系统默认只会调用Custom View的前两个构造函数,至于第三个构造函数的调用,通常是我们自己在构造函数中主动调用的
 * （例如,在第二个构造函数中调用第三个构造函数）.
 */

public class ParallaxContainer extends FrameLayout {

    private List<ParallaxFragment> fragments;

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


        //实例化viewpager


        //绑定

    }
}
