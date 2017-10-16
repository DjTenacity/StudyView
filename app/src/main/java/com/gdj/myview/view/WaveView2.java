package com.gdj.myview.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.gdj.myview.R;

/**
 * Comment:有小船的波浪线
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/10/15
 */
public class WaveView2 extends View {
    private Paint paint;
    private Path path;
    private int waveLength = 500;
    private int dy = 0;
    private float fraction;
    private Bitmap mBitmap;
    private PathMeasure mPathMeasure;
    private float[] pos = new float[2];
    private float[] tan = new float[2];
    private Matrix mMatrix;


    public WaveView2(Context context) {
        this(context, null);
    }


    public WaveView2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        path = new Path();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(8);
        paint.setStyle(Paint.Style.STROKE);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_start, options);
        setPathData();
        mMatrix = new Matrix();
        mPathMeasure = new PathMeasure(path, false);
    }

    private void setPathData() {
        int originY = 400;
        if (dy < originY + 150) {
            dy += 10;

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
        path.moveTo(-waveLength, originY - dy);

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
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPath(path, paint);
        float length = mPathMeasure.getLength();
        mMatrix.reset();
        //方法一,通过计算:将tan值通过反三角函数得到对应的弧度,然后将弧度转换成度数
        //       mPathMeasure.getPosTan(length * fraction, pos, tan);
//        float degrees = (float) (Math.atan2(tan[1], tan[0]) * 180f / Math.PI);
//        mMatrix.postRotate(degrees, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);
//        mMatrix.postTranslate( pos[0]-mBitmap.getWidth()/2, pos[1]-mBitmap.getHeight());

        //方法2,Api,,//flag:表示要求哪一个值：tan或者pos
        mPathMeasure.getMatrix(length * fraction, mMatrix, PathMeasure.TANGENT_MATRIX_FLAG | PathMeasure.POSITION_MATRIX_FLAG);
        //修改偏移值----即让图片中心点与当前坐标点一致 来绘制
        mMatrix.preTranslate(-mBitmap.getWidth()/2, -mBitmap.getHeight());

        canvas.drawBitmap(mBitmap, mMatrix, paint);
        canvas.drawPath(path, paint);
        //canvas.drawBitmap(mBitmap, pos[0] - mBitmap.getWidth() / 2, pos[1] - mBitmap.getHeight() / 2, paint);
    }

    public void startAnimation() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(1000);
        //循环
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                fraction = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
    }
}
