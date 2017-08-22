package com.gdj.myview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Administrator on 2017/7/30.
 */

public class PainView extends View {

    Paint mPaint;
    String str = "呜呜呜呜wwww";

    public PainView(Context context) {
        this(context, null);
    }

    public PainView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PainView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //不能new在这里
        mPaint.reset();

//        mPaint.setAlpha(255);
//        mPaint.setStyle(Paint.Style.STROKE);//描边
//        mPaint.setStyle(Paint.Style.FILL);//填充
//        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
//
//        //宽度
//        mPaint.setStrokeWidth(10);
//
//        mPaint.setStrokeCap(Paint.Cap.BUTT);//线的头尾形状  ，没有
//        mPaint.setStrokeCap(Paint.Cap.ROUND);//线的头尾形状  ，圆形
//        mPaint.setStrokeCap(Paint.Cap.SQUARE);//线的头尾形状  ，方形
//        Path path = new Path();
//        path.moveTo(100, 100);
//        path.lineTo(300, 300);
//        path.lineTo(100, 300);
//        mPaint.setStrokeJoin(Paint.Join.BEVEL);//直线
//        mPaint.setStrokeJoin(Paint.Join.MITER);//锐角
//        mPaint.setStrokeJoin(Paint.Join.ROUND);//交汇的地方 圆弧
//        canvas.drawPath(path, mPaint);
//
//        path.moveTo(400, 100);
//        path.lineTo(200, 300);
//        path.lineTo(500, 300);
//        mPaint.setStrokeJoin(Paint.Join.BEVEL);//直线
//        canvas.drawPath(path, mPaint);
//        canvas.drawCircle(100, 100, 90, mPaint);


        //文字绘制
        //获得字符行间距
        //mPaint.getFontSpacing();
        //获得字符之间的间距
        //mPaint.getLetterSpacing();
        //  mPaint.setLetterSpacing();

        //mPaint.setStrikeThruText(true); //设置文本删除线

        // mPaint.setUnderlineText(true); //设置下划线


        //mPaint.setTypeface(Typeface.DEFAULT_BOLD);//设置字体类型

        // Typeface.create(familyName,style);//加载自定义字体

        //mPaint.setTextSkewX(-0.25f);//倾斜  ，默认为0，官方推荐的  -0.25
        //mPaint.setTextAlign(Paint.Align.LEFT); //文本对齐方式


        // mPaint.setTextSize(50);  // mPaint.getTextSize(); //文本
        //计算某个指定长度的字符串（字符长度，字符个数，显示的时候真实的长度）//中文占两个字符

        // float[] measuredWidth = new float[1];
        // int breakText = mPaint.breakText(str, true, 200, measuredWidth);
        // mPaint.getTextBounds(str,start,end,bounds); //获取文本的矩形区域（宽高）
        // Log.w("measuredWidth",measuredWidth[0]+"breakText"+breakText+ str);

        // float[] measuredWidth = new float[10];
        // mPaint.measureText(str);  //测量文本的宽度，但是是一个比较粗略的结果
        // mPaint.getTextWidths(str,measuredWidth) ;//测量文本的宽度，但比上面一个比较粗略的结果

        //canvas.drawText();

        int top = 60;
        int baseLineX = 0;
        mPaint.setColor(Color.BLUE);
        mPaint.setTextSize(50);
        mPaint.setTextAlign(Paint.Align.LEFT);


        canvas.drawText(str, 0, 0, mPaint);

        //文本metrics信息
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        //基线  baseLine  大写字母位于基线上。最常见的例外是J和Q。不齐线数字（见阿拉伯数字）位于基线上。
        //Paint.FontMetricsInt fontMetricsInt = mTextPaint.getFontMetricsInt();  获取int,上面是float
        //fontMetrics.top   (descent)
        //fontMetrics.ascent   (bottom)

        Log.w("baseLineY", "top:" + fontMetrics.top + ",bottom" + fontMetrics.bottom);
        //drawText是不是是从基线开始画的,
        float baseLineY = top - fontMetrics.top;
        canvas.drawText("0, 0开始 ", 0, baseLineY, mPaint);
        canvas.drawLine(0, baseLineY, 1000, baseLineY, mPaint);
        mPaint.setColor(Color.RED);
        canvas.drawText("60高度 ", baseLineX, top, mPaint);
        canvas.drawLine(0, top, 1000, top, mPaint);
        mPaint.setColor(Color.BLACK);
        canvas.drawText("文本中间", baseLineX, (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom, mPaint);

        canvas.drawLine(0, (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom, 1000,
                (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom, mPaint);

        canvas.drawText("文本基线的学习",  getWidth()/2, (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom +getHeight()/2, mPaint);
    }
}
