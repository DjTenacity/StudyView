package com.gdj.myview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.ListView;

import com.gdj.myview.R;

/**
 * 作者：${LoveDjForever} on 2017/7/14 15:44
 *  * 邮箱： @qq.com
 */

public class ParallaxListView extends ListView {
    ImageView mImageView,iv_icon;
    int mImageViewHeight = 0;

    public ParallaxListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mImageViewHeight = context.getResources().getDimensionPixelOffset(R.dimen.dp_256);
    }

    //监听  可滑动控件的滑动过度
    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY,
                                   int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {

        //deltaX, deltaY ——>dx,dy 增量    正的  上拉过度 和负的 下拉过度

        if (deltaY < 0) {
            //添加高度，因为是centerCrop所以宽度也会变大
            mImageView.getLayoutParams().height = mImageView.getHeight() - deltaX;
            mImageView.requestLayout();
            iv_icon.setRotation(iv_icon.getRotation()-deltaY);
        } else {
            //缩小,要确认是放大情况
            if (mImageView.getHeight() > mImageViewHeight) {
                mImageView.getLayoutParams().height = mImageView.getHeight() - deltaX;
                mImageView.requestLayout();
                iv_icon.setRotation(iv_icon.getRotation()+deltaY);
            }
        }

        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX,
                scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    public void setZoomImageView(ImageView imageView) {
        mImageView = imageView;
    }
    public void setHeardImageView(ImageView imageView) {
        iv_icon = imageView;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        //监听，让ImageView在上滑时缩小
        View heard = (View) mImageView.getParent();
        //往上推的时候，父容器会被推出去一部分，所以gettop，距离头部为负
        if (heard.getTop() < 0 && mImageView.getHeight() > mImageViewHeight) {
            mImageView.getLayoutParams().height = mImageView.getHeight() + heard.getTop();
            //因为拿到的是父容器的gettop，父容器也需要layout一下，因为子控件发生变化，而我们参考了父容器的gettop
            heard.layout(heard.getLeft(), heard.getTop(), heard.getRight(), heard.getBottom());
            mImageView.requestLayout();
        }

        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        //松开手
        if (action == MotionEvent.ACTION_UP) {
            ResetAnimation resetAnimation = new ResetAnimation(mImageView,mImageViewHeight,iv_icon);
            resetAnimation.setDuration(300);
            mImageView.startAnimation(resetAnimation);
        }

        return super.onTouchEvent(ev);
    }

    //自定义动画
    public class ResetAnimation extends Animation {
        ImageView mIV,iv_icon;
        int targetHeight;   //最终要恢复的高度
        int originalHeight;//现在的高度
        int extraHeight;//高度差

        public ResetAnimation(ImageView mImageView, int targetHeight,ImageView iv_icon) {
            mIV = mImageView;
            this.iv_icon=iv_icon;
            this.targetHeight = targetHeight;
            this.originalHeight = mIV.getHeight();
            extraHeight = originalHeight - targetHeight;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            // interpolatedTime(0.0 - 1.0 )执行的百分比
            //减小imageView的高度
            mIV.getLayoutParams().height = (int) (originalHeight-extraHeight* interpolatedTime);//现在的高度-高度差 * interpolatedTime
            iv_icon.setRotation(iv_icon.getRotation()-iv_icon.getRotation()* interpolatedTime);
            super.applyTransformation(interpolatedTime, t);
        }
    }
}
