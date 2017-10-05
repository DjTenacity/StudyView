package com.gdj.myview.ui.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Comment:
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/10/4
 */
public class SyncScrollBehavior extends CoordinatorLayout.Behavior<View> {

    public SyncScrollBehavior(Context context, AttributeSet attr) {
        super(context, attr);
    }


    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout,
                                       View child, View directTargetChild, View target, int nestedScrollAxes) {
        return (nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL) || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }


    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child,
                                  View target, int dx, int dy, int[] consumed) {
        int scrollY = (int) target.getScaleY();
        //ScrollView
        child.setScaleY(target.getScaleY());
        //这种实现方式在快速滑动的时候会出问题
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
    }

    //快速滑动的时候手指松开以后的惯性hua动作
    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY, boolean consumed) {
        ((NestedScrollView)child).fling((int) velocityY);

        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    }
}
