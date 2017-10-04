package com.gdj.myview.ui.behavior;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Comment:自定义Behavior
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/10/4
 */
public class CustomBehavior extends CoordinatorLayout.Behavior<View> {

    public CustomBehavior(Context context, AttributeSet attr) {
        super(context, attr);
    }

    /**
     * 需要监听那些空间或者容易的状态 ( 1,知道监听谁 ;2,什么状态改变 )
     * <p>
     * CoordinatorLayout  parent 父容器
     * VIew child 子控件-->要监听dependency这个view的视图们
     * View dependency  要监听的view
     */
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {

        //还可以根据ID 或者TAG 来判断
        return dependency instanceof TextView || super.layoutDependsOn(parent, child, dependency);
    }

    /**
     * 当被监听的view发生改变的时候回调
     * <p>
     * 可以在此方法里面做一些乡音的联动动画等效果
     */
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        //获取监听的view的状态=====垂直效果
        int offset = dependency.getTop() - child.getTop();
        //让child进行平移,也可以翻转等等
        ViewCompat.offsetTopAndBottom(child, offset);
        child.animate().rotation(child.getTop() * 10);
        return super.onDependentViewChanged(parent, child, dependency);
    }

}
