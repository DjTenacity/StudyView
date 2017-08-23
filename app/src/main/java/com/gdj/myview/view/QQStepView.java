package com.gdj.myview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.gdj.myview.R;

/**
 * 防QQ步数
 * 构造函数，第一个调第二个，第二个调第三个
 */

public class QQStepView extends View {
    private int mOuterColor = Color.RED;
    private int mInnerColor = Color.BLUE;
    private int mBorderWidth = 20;      //(px)
    private int mStepTextSize;
    private int mStepTextColor = Color.RED;
    private Paint mOutPaint;

    private Paint mInnPaint, mTextPaint;

    private int mStepMax = 0;//总共
    private int mCurrentStep = 0;//当前的

    public QQStepView(Context context) {
        this(context, null);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //1. 分析效果
        //2.确定自定义属性，编写attrs
        //3 在布局中使用
        //4 自定义 view 中获取自定义属性
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.QQStepView);
        mOuterColor = array.getColor(R.styleable.QQStepView_outerColor, mOuterColor);
        mInnerColor = array.getColor(R.styleable.QQStepView_innerColor, mInnerColor);

        mBorderWidth = (int) array.getDimension(R.styleable.QQStepView_borderWidth, mBorderWidth);
        mStepTextSize = (int) array.getDimensionPixelOffset(R.styleable.QQStepView_stepTextSize, mStepTextSize);

        mStepTextColor = array.getColor(R.styleable.QQStepView_stepTextColor, mStepTextColor);
        array.recycle();
        //5 measure
        //6 画外圆弧 ，内圆弧 ，文字
        mOutPaint = new Paint();
        //抗锯齿
        mOutPaint.setAntiAlias(true);
        //宽度
        mOutPaint.setStrokeWidth(mBorderWidth);
        mOutPaint.setStrokeCap(Paint.Cap.ROUND);//大圆头部圆的
        mOutPaint.setColor(mOuterColor);
        mOutPaint.setStyle(Paint.Style.STROKE);//Fill 画笔实心  STROKE空心

        mInnPaint = new Paint();
        //抗锯齿
        mInnPaint.setAntiAlias(true);
        //宽度
        mInnPaint.setStrokeWidth(mBorderWidth);
        mInnPaint.setStrokeCap(Paint.Cap.ROUND);// 圆头部圆的
        mInnPaint.setColor(mInnerColor);
        mInnPaint.setStyle(Paint.Style.STROKE);//Fill 画笔实心  STROKE空心

        mTextPaint = new Paint();
        //抗锯齿
        mTextPaint.setAntiAlias(true);
        //宽度
        mTextPaint.setColor(mInnerColor);
        mTextPaint.setTextSize(mStepTextSize);
        mTextPaint.setStyle(Paint.Style.STROKE);//Fill 画笔实心  STROKE空心
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //调用者在布局文件中可能是  wrap_content  宽度高度不一致
        //获取模式 AT_MOST  40dp
        //宽度高度不一致 时，取最小值 ，确保是个正方形
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        //三步运算符，第五步完成  ,取最小值
        setMeasuredDimension(width > height ? height : width, width > height ? height : width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //  int center=getWidth()/2;  中心点
        //半径 int radius=getWidth()/2-mBorderWidth;

        //6.1 外圓弧   描边有宽度，容易出现描边覆盖的问题
        //center-radius,center-radius,  +  ,  +
        // left, float top, float right, float bottom
        RectF rectF = new RectF(mBorderWidth / 2, mBorderWidth / 2, getWidth() - mBorderWidth / 2, getHeight() - mBorderWidth / 2);
        // boolean useCenter  ,如果为true就会闭合
        canvas.drawArc(rectF, 135, 270, false, mOutPaint);

        //6.2 画内弧  百分比，从外面传的

        if (mCurrentStep == 0) {
            return;
        }

        float sweepAngle = (float) mCurrentStep / mStepMax;//float 切记强转
        // boolean useCenter  ,如果为true就会闭合
        canvas.drawArc(rectF, 135, sweepAngle * 270, false, mInnPaint);

        //花蚊子

        String stepText = mCurrentStep + "";
        //Rect类主要用于表示坐标系中的一块矩形区域，并可以对其做一些简单操作。这块矩形区域，需要用左上右下两个坐标点表示
        //作为一个有一点经验的做图像或者矩阵运算或者编程的程序员来说，
        // 大家的共识是，如果一个矩阵是MxN的，也就是M行N列，那么行号是[0,M-1],列号是[0,N-1]。
        // 可是 Rect类并不是这样的！如果你这么声明一个Rect类：
        // Rect rect=new Rect(100,50,300,500);  这个矩形实际区域是：(100,50,299,499)。Rect计算出的Height和Width倒是对的。
        // 涉及Rect运算的时候，尽量不要使用它的右下角左边，即right和bottom。他们是错的。在你调用android自己的函数时，是可以使用的，因为Android里面一直保持这么奇葩的思维。
        Rect textBounds = new Rect();

        //参数1：字符串来衡量并返回它的界限
        //参数2：索引的第一个字符的字符串来衡量
        //参数3：过去的最后一个字符字符串
        //参数4：返回联合边界的所有文本。必须分配给调用者
        mTextPaint.getTextBounds(stepText, 0, stepText.length(), textBounds);
        int dx = getWidth() / 2 - textBounds.width() / 2;//

        //基线  baseLine  大写字母位于基线上。最常见的例外是J和Q。不齐线数字（见阿拉伯数字）位于基线上。
        Paint.FontMetricsInt fontMetricsInt = mTextPaint.getFontMetricsInt();

        Log.w("baseLineYQQ", "top:" + fontMetricsInt.top + ",bottom" + fontMetricsInt.bottom);

        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        int baseLine = getHeight() / 2 + dy;
        canvas.drawText(stepText, dx, baseLine, mTextPaint);


    }

    //动画
    public synchronized void setCurrentStep(int mCurrentStep) {
        this.mCurrentStep = mCurrentStep;
        invalidate();

    }

    public synchronized void setStepMax(int mStepMax) {
        this.mStepMax = mStepMax;
    }
}
