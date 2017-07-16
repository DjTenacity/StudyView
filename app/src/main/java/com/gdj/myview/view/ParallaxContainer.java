package com.gdj.myview.view;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * 引导页的最外层布局.
 */

public class ParallaxContainer extends FrameLayout{
    public ParallaxContainer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public ParallaxContainer(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ParallaxContainer(@NonNull Context context) {
        super(context);
    }
    /**
     * 指定引导页的所有页面布局文件
     * **/
    public void setUp(int...ChildIds){
        //根据布局文件数组，初始化

        //实例化适配器


    }
}
