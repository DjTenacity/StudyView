package com.gdj.myview.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * description:
 * created by darren on 2017/8/1 07:18
 * email: 240336124@qq.com
 * version: 1.0
 */
public class SingleLineFlowLayout extends LinearLayout {

    public SingleLineFlowLayout(Context context) {
        super(context);
    }

    public SingleLineFlowLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SingleLineFlowLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void addView(View child) {
        int allChildWidth = 0;

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            LayoutParams params = (LayoutParams) childView.getLayoutParams();
            allChildWidth += childView.getMeasuredWidth() + params.leftMargin + params.rightMargin;
        }
        LayoutParams params = (LayoutParams) child.getLayoutParams();
        allChildWidth += child.getMeasuredWidth() + params.leftMargin + params.rightMargin;
        if (allChildWidth < getMeasuredWidth()) {
            super.addView(child);
        }
    }
}
