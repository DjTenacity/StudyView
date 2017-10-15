package com.gdj.myview.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Comment:
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/10/15
 */
public class WaveView extends View {
    private Paint paint;
    private Path path;
    private int waveLength = 500;
    private int dy=0;
    public WaveView(Context context) {
        this(context, null);
    }


    public WaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        path = new Path();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(8);
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int originY = 400;
        if(dy<originY+150){
            dy+=10;

        }
        //第一个点--->起始点p0
        //  path.moveTo(100, 400);
//        //二级贝瑟尔曲线1,起始点x轴距离结束点300
//        path.quadTo(250, 200, 400, 400);
//
//        //二级贝瑟尔曲线2(后面的曲线的起始点,默认是上一个曲线的结束点)
//        path.quadTo(550, 600, 700, 400);
//        path.moveTo(100, 700);
//        //三级贝瑟尔曲线
//        path.cubicTo(0, 0, 400, 0, 400, 700);
        //动画循环播放,所以重置path
        path.reset();
        path.moveTo(-waveLength, originY-dy);

        int halfWaveLength = waveLength / 2;
        //屏幕宽度里面画多少波长
        for (int i = -waveLength; i <= getWidth() + waveLength; i += waveLength) {
            //相对于自己的起始点,也就是上一个曲线的终点的距离 dx,dy ,,不是上一个点哦
            //绝对坐标表示: x1=x0+dx
            //相对坐标点表示: x1=dx
            path.rQuadTo(halfWaveLength / 2, -150, halfWaveLength, 0);//相对于曲线的起始点
            path.rQuadTo(halfWaveLength / 2, 150, halfWaveLength, 0);
        }
        //颜色填充,画一个封闭的空间
        path.lineTo(getWidth(), getHeight());
        path.lineTo(0, getHeight());
        //关闭路径(将结束点与起始点连接起来)

        path.close();

        canvas.drawPath(path, paint);
    }

    public void startAnimation() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, waveLength);
        animator.setDuration(1000);
        //循环
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dy = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
    }
}
