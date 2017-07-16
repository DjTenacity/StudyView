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
        mInnerColor = array.getColor(R.styleable.QQStepView_outerColor, mInnerColor);

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
        //三步运算符，第五步完成
        setMeasuredDimension(width > height ? height : width, width > height ? height : width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //  int center=getWidth()/2;  中心点
        //半径 int radius=getWidth()/2-mBorderWidth;

        //6.1 外圓弧   描边有宽度，容易出现描边覆盖的问题
        //center-radius,center-radius,  +  ,  +
        RectF rectF = new RectF(mBorderWidth / 2, mBorderWidth / 2, getWidth() + mBorderWidth / 2, getHeight() + mBorderWidth / 2);
        // boolean useCenter  ,如果为true就会闭合
        canvas.drawArc(rectF, 135, 270, false, mOutPaint);

        //6.2 画内弧  百分比，从外面传的

        if (mCurrentStep == 0) {
            return;
        }

        float sweepAngle = mCurrentStep / mStepMax;
        // boolean useCenter  ,如果为true就会闭合
        canvas.drawArc(rectF, 135, sweepAngle * 270, false, mInnPaint);

        //花蚊子

        String stepText = mCurrentStep + "";
        Rect textBounds = new Rect();
        mTextPaint.getTextBounds(stepText, 0, stepText.length() / 2, textBounds);
        int dx = getWidth() / 2 - textBounds.width() / 2;


        //基线  baseLine
        Paint.FontMetricsInt fontMetricsInt = mTextPaint.getFontMetricsInt();

        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        int baseLine = getHeight() / 2 + dy;
        canvas.drawText(stepText, dx, baseLine, mTextPaint);


    }

    //动画
    public synchronized void setCurrentStep(int mCurrentStep  ) {
        this.mCurrentStep=mCurrentStep;

        invalidate();

    }
    public synchronized void setStepMax(int mStepMax  ) {
        this.mStepMax=mStepMax;
    }
}
