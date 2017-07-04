package com.gdj.myview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by Lenovo on 2016/8/17.
 *
 * @date: 2016年8月17日17:48:13
 */
public class UpHideScrollView extends LinearLayout {
    int Max = 0;//顶部待隐藏的高度
    private boolean isMeasured = false;//是否第一次测量大小
    private View topView, bottomView;
    public boolean mShowing = true;
    float oldY, mDLastY;
    Scroller mScroller;
    private boolean down;//用来表示是否下滑
    int mlastinterceptx, mlastinterceptY;
    OnHeaderScrollLiesten onHeaderScrollLiesten;

    public UpHideScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setScrollBarStyle(SCROLLBARS_OUTSIDE_OVERLAY);
        mScroller = new Scroller(context);
    }

    private void smoothScrollTo(int dx, int dy) {
        mScroller.startScroll(getScrollX(), getScrollY(), dx, dy - getScrollY());
        invalidate();
        if (dy == 0) {//这里可以判断滑动完成后顶部是隐藏还是现实，在回调中可以控制界面的底部的展现
            // 和隐藏，比如改变tab的透明度什么的
            mShowing = true;
            if (onHeaderScrollLiesten != null)
                onHeaderScrollLiesten.isCompute(true);
        } else if (dy == Max) {
            if (onHeaderScrollLiesten != null)
                onHeaderScrollLiesten.isCompute(false);
            mShowing = false;
        }
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean indelet = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                indelet = false;
                if (!mScroller.isFinished()) {
                    // 保证停止动画防止冲突
                    mScroller.abortAnimation();
                    indelet = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = ev.getX() - mlastinterceptx;
                float dy = ev.getY() - mlastinterceptY; // 纵向滑动
                if (Math.abs(dx) >= Math.abs(dy)) {
                    indelet = false;
                } else {// 竖向滑动
                    if (mShowing) {
                        if (dy < 0)// 这里我们只需要响应竖向滑动的上滑；
                            indelet = true;
                    } else {
                        indelet = false;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                indelet = false;
                break;
        }
        mlastinterceptx = (int) ev.getX();
        mlastinterceptY = (int) ev.getY();
        mDLastY = ev.getY();
        return indelet;
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int scrollY = getScrollY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                oldY = ev.getY();
                mDLastY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int mSlid = (int) (ev.getY() - mDLastY);
                if (mSlid < 0 && scrollY < Max) {
                    if (onHeaderScrollLiesten != null)
                        onHeaderScrollLiesten.onScoll(-mSlid, Max);
                    scrollBy(0, -mSlid);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (scrollY > 0)
                    if (Math.abs(scrollY) < Max / 2) {
                        smoothScrollTo(0, 0);
                    } else {
                        smoothScrollTo(0, Max);
                    }
                break;
        }
        mDLastY = ev.getY();
        return true;
    }

    public void setOnHeaderScrollLiesten(OnHeaderScrollLiesten liesten) {
        onHeaderScrollLiesten = liesten;
    }

    public interface OnHeaderScrollLiesten {
        void onScoll(int y, int h);

        void isCompute(boolean b);//
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        super.dispatchTouchEvent(ev);
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b + Max);
    }

    int topHeight;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!isMeasured) {
            isMeasured = true;
            topView = getChildAt(0);
            bottomView = getChildAt(1);
            Max = topView.getMeasuredHeight();
        }
        bottomView.measure(widthMeasureSpec, heightMeasureSpec);
    }

    public void ShowHeader() {
        smoothScrollTo(0, 0);
    }

    public void ShowHeaderDelay() {
        mScroller.startScroll(getScrollX(), getScrollY(), 0, 0 - getScrollY(), 2000);
        invalidate();
        mShowing = true;
    }

    public void HideHeader() {
        smoothScrollTo(0, Max);
    }
}
