package com.gdj.myview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

import java.util.LinkedList;

/**
 * Comment:
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/8/30
 */
public class MyScrollView1 extends ScrollView {
    public MyScrollView1(Context context) {
        super(context);
    }

    public MyScrollView1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        requestDisallowInterceptTouchEvent(true);//不要拦截
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        if (clampedY) {
            requestDisallowInterceptTouchEvent(false);//拦截
        }
    }

    //针对scrollview里面listview的问题,viewholder就不行了,
    // 因为所有的条目已经画出来了,而viewholder就是为了回收划出去的条目
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int expandedHeight=MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST);
//        super.onMeasure(widthMeasureSpec, expandedHeight);
//    }
}
//--x  先减再赋值