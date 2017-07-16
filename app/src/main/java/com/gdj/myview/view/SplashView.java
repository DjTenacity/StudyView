package com.gdj.myview.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ViewAnimator;

import com.gdj.myview.R;

/**
 * Created by Administrator on 2017/7/15.
 */

public class SplashView extends View {

    //大圆（包括了6个小圆）的半径，大圆旋转小圆也就会旋转
    private float mRotationRadius = 90;
    //大圆的半径
    private float mCircleRadius = 18;

    //小圆的颜色列表，在initialize方法里面初始化
    private int[] mCircleColors;

    //大圆和小圆 旋转的时间
    private long mRotationDuration = 1200;//ms

    //第二部分动画的执行总时间（包括第二个动画时间，各占1/2）
    private long mSplashDuration = 1200;//ms
    //整体的背景颜色
    private int mSplashBgColor = Color.WHITE;

    /**
     * 参数，保存了一些绘制状态，会被动态的改变
     **/

    //空心圆初始半径
    private float mHoleRadius = 0f;

    //当前大圆旋转角度（弧度）
    private float mCurrentRotationRadiusAngle = 0f;

    //当前大圆的半径
    private float mCurrentRotationRadius = mRotationRadius;

    //绘制圆的画笔
    private Paint mPaint = new Paint();

    //绘制背景的画笔
    private Paint mPaintBackground = new Paint();

    //屏幕正中心点坐标
    private float mCenterX;
    private float mCenterY;

    //屏幕对角线的一般
    private float mDiagonalDist;

    //来定义动画状态
    SplashState mState = null;

    private abstract class SplashState {
        protected abstract void drowState(Canvas canvas);
    }

    public SplashView(Context context) {
        super(context);
        //初始化数据
        init(context);
    }

    private void init(Context context) {
        mCircleColors = context.getResources().getIntArray(R.array.splash_circle_colors);
        //画笔
        //消除锯齿
        mPaint.setAntiAlias(true);
        mPaintBackground.setStyle(Paint.Style.STROKE);//待会要画空心圆
        mPaintBackground.setColor(mSplashBgColor);//背景颜色
    }

    //当屏幕出来的时候会调用  onSizeChanged   方法
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //初始化中心点坐标
        //在view显示出来的时候会调用一次
        mCenterX = w / 2f;
        mCenterY = h / 2f;

        //对角线的一半
        mDiagonalDist = (float) Math.sqrt(w * w + h * h) / 2;
    }

    //进入主界面----开启后面的两个动画
    public void splashAndDisappear() {
        if (mState != null && mState instanceof RotationState) {

            RotationState rt = (RotationState) mState;
            rt.cancel();//首先取消动画
            post(new Runnable() {//为了性能，为了保证ui的优先
                @Override
                public void run() {
                    //执行第二个d动画
                    mState = new MergingState();
                    //提醒view重新绘制  ***onDraw方法
                    //invalidate();
                }
            });
        }
    }

    //动画就是不断调用onDraw方法     旋转动画  聚合动画  扩散动画
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //设计模式-----模板模式  三个类，分别封装，分别管自己的，
        // 每一个都有draw方法（专门写一个抽象类，让他们都有自己的绘制方法）
        //这里面就做一个简单的分发
        if (mState == null) {
            //第一次执行动画
            mState = new RotationState();
        }
        mState.drowState(canvas);

    }

    private ValueAnimator mAnimation;

    /**
     * 旋转
     **/
    private class RotationState extends SplashState {


        public RotationState() {
            //小圆的坐标 ————>大圆的半径，大圆旋转了多少角度
            //   ValueAnimator  估值器   1200ms时间内，计算某个时刻当前角度是  0 ~ 2π
            mAnimation = ValueAnimator.ofFloat(0, (float) Math.PI * 2);
            //设置茶之器--均匀计算
            mAnimation.setInterpolator(new LinearInterpolator());

            mAnimation.setDuration(mRotationDuration);

            mAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    //设置监听，得到某个时间点的结果，当前大圆旋转的角度
                    mCurrentRotationRadiusAngle = (float) mAnimation.getAnimatedValue();

                    //重新绘制----onDraw
                    //invalidate();
                    postInvalidate();
                }
            });
            //一直旋转，旋转次数————>无穷
            mAnimation.setRepeatCount(ValueAnimator.INFINITE);
            mAnimation.start();
        }

        @Override
        protected void drowState(Canvas canvas) {
            //绘制旋转动画———————不断获得旋转的角度
            //绘制小圆的旋转动画
            //1 .清空画板,要把下面的先清空，再“覆盖”
            drawBackgroun(canvas);
            //2.绘制小圆
            drawCircles(canvas);
        }

        //取消动画
        public void cancel() {
            mAnimation.cancel();
        }
    }

    /***清空画板 或者绘制空心圆**/
    private void drawBackgroun(Canvas canvas) {
        if (mHoleRadius > 0) {
            //绘制空心圆，使用一个非常宽的画笔，不断减小画笔的宽度
            //设置画笔的宽度 =  对角线 / 2 -空心部分的半径
            float strokeWidth = mDiagonalDist - mHoleRadius;
            mPaintBackground.setStrokeWidth(strokeWidth);//画笔的宽度
            float raduio = mHoleRadius + strokeWidth / 2;
            canvas.drawCircle(mCenterX, mCenterY, raduio, mPaintBackground);

        } else {
            canvas.drawColor(mSplashBgColor);
        }

    }

    /*** 绘制小圆 ***/
    private void drawCircles(Canvas canvas) {

        float rotationAngle = (float) (2 * Math.PI / mCircleColors.length);
        for (int i = 0; i < mCircleColors.length; i++) {
            mPaint.setColor(mCircleColors[i]);
            /***
             * x=r*cos(a)+mCenX
             * y=r*sin(a)+mCenterY
             *
             * a=旋转监督+间隔角度*i
             ***/

            double a = mCurrentRotationRadiusAngle + rotationAngle * i;

            float cx = (float) (mCurrentRotationRadius * Math.cos(a) + mCenterX);
            float cy = (float) (mCurrentRotationRadius * Math.sin(a) + mCenterY);


            canvas.drawCircle(cx, cy, mCircleRadius, mPaint);
        }

    }

    /*** 聚合***/
    private class MergingState extends SplashState {

        public MergingState() {
            //绘制聚合动画,半径不停的减小
            //小圆的坐标 ————>大圆的半径，大圆旋转了多少角度
            //   ValueAnimator  估值器   1200ms时间内，计算某个时刻当前 大圆的半径  r ~ 0
            mAnimation = ValueAnimator.ofFloat(0, mRotationRadius);//反过来
            //设置茶之器--   加速器
            mAnimation.setInterpolator(new OvershootInterpolator(10));
            mAnimation.setDuration(mSplashDuration / 2);

            mAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    //设置监听，得到某个时间点的结果，当前大圆的半径
                    mCurrentRotationRadius = (float) mAnimation.getAnimatedValue();

                    //重新绘制----onDraw
                    postInvalidate();
                }
            });

//监听动画执行完毕
            mAnimation.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mState = new ExpandingState();
                    //invalidate();
                    postInvalidate();
                }
            });            //一直旋转，旋转次数————>无穷
            // mAnimation.setRepeatCount(ValueAnimator.INFINITE);
            //翻转执行
            mAnimation.reverse();

        }

        @Override
        protected void drowState(Canvas canvas) {
            //绘制旋转动画———————不断获得旋转的角度
            //绘制小圆的旋转动画
            //1 .清空画板,要把下面的先清空，再“覆盖”
            drawBackgroun(canvas);
            //2.绘制小圆
            drawCircles(canvas);
        }
    }

    /**
     * 扩散
     **/
    private class ExpandingState extends SplashState {
        public ExpandingState() {
            //绘制聚合动画,半径不停的减小
            //小圆的坐标 ————>大圆的半径，大圆旋转了多少角度
            //   ValueAnimator  估值器   1200ms时间内，计算某个时刻当前        0   ~   mD对角线的一半
            mAnimation = ValueAnimator.ofFloat(0, mDiagonalDist);//反过来
            //设置茶之器--均匀计算
            mAnimation.setInterpolator(new LinearInterpolator());
            mAnimation.setDuration(mRotationDuration);


            mAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    //设置监听，得到某个时间点的结果，当前画笔的半径
                    mHoleRadius = (float) mAnimation.getAnimatedValue();

                    //重新绘制----onDraw
                    //invalidate();
                    postInvalidate();
                }
            });
            mAnimation.start();

        }

        @Override
        protected void drowState(Canvas canvas) {
            drawBackgroun(canvas);
        }
    }

}
