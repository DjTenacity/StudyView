package com.gdj.myview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Comment:流式布局
 * 没有权重的时候线性布局的性能高于相对布局
 * 区别在于onMeasure,线性分为水平和竖直,没有权重只会onMeasure一次,而相对布局是两次
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/10/8
 */
public class CustomLayout extends ViewGroup {
    public CustomLayout(Context context) {
        this(context, null);
    }

    public CustomLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    int width, height, lineWidth, lineHeight;

    //height最终高度,,,width行中最宽的
    //为了子控件的margin
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        int chileCount = getChildCount();
        //循环遍历子控件
        for (int i = 0; i < chileCount; i++) {
            View child = getChildAt(i);

            //系统自动测量子控件,这里使用getMeasuredWidth,getWidth都行
            measureChild(child,widthMeasureSpec,heightMeasureSpec);

            MarginLayoutParams mlp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = mlp.leftMargin + mlp.rightMargin + child.getMeasuredWidth() ;
            int childHeight = child.getMeasuredHeight() + mlp.bottomMargin + mlp.topMargin ;

            if (childWidth + lineWidth > sizeWidth) {//一旦超出了容器的宽度 换行
                width = Math.max(width, lineWidth);
                height += lineHeight;
                lineWidth = childWidth;
                lineHeight = childHeight;
            } else {
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }
            if (i == chileCount - 1) {
                width = Math.max(width, lineWidth);
                height += lineHeight;
            }

        }
        int measureHeight = modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height;
        int measureWidth = modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width;
        setMeasuredDimension(measureWidth, measureHeight);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mListViews.clear();
        mListHeight.clear();

        int lineWidth = 0;
        int lineHeight = 0;
        int width = getWidth();
        int count = getChildCount();

        //行的,这里只能用getMeasuredWidth
        List<View> mList = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            MarginLayoutParams mlp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = mlp.leftMargin + mlp.rightMargin + child.getMeasuredWidth() ;
            int childHeight = child.getMeasuredHeight() + mlp.bottomMargin + mlp.topMargin;

            if (childWidth + lineWidth > width) {
                mListViews.add(mList);
                mListHeight.add(lineHeight);
                lineWidth = 0;
                lineHeight = childHeight;
                mList = new ArrayList<>();
            }

            mList.add(child);
            lineWidth += childWidth;
            lineHeight = Math.max(lineHeight, childHeight);
        }
        mListViews.add(mList);
        mListHeight.add(lineHeight);

        //排列 每一个子控件的 left,top,right, bottom
        int left = 0;
        int top = 0;
        int size = mListViews.size();

        for (int i = 0; i < size; i++) {
            mList = mListViews.get(i);

            lineHeight = mListHeight.get(i);
            for (int j = 0; j < mList.size(); j++) {
                View child = mList.get(j);
                MarginLayoutParams mlp = (MarginLayoutParams) child.getLayoutParams();
                int lc = left + mlp.leftMargin;
                int tc = top + mlp.topMargin;
                int rc = lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();
                child.layout(lc, tc, rc, bc);

                left += child.getMeasuredWidth() + mlp.leftMargin + mlp.rightMargin;
            }
            top += lineHeight;
            left = 0;
        }

    }

    List<List<View>> mListViews = new ArrayList<>();
    List<Integer> mListHeight = new ArrayList<>();


}
