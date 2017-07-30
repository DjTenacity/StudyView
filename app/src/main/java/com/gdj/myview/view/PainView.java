package com.gdj.myview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Administrator on 2017/7/30.
 */

public class PainView extends View {

    Paint mPaint;
    String str="呜呜呜呜wwww";

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
        mPaint.setColor(Color.RED);
        mPaint.setAlpha(255);
        //   mPaint.setStyle(Paint.Style.STROKE);//描边
        //   mPaint.setStyle(Paint.Style.FILL);//填充
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        //宽度
        mPaint.setStrokeWidth(80);

        mPaint.setStrokeCap(Paint.Cap.BUTT);//线的头尾形状  ，没有
//        mPaint.setStrokeCap(Paint.Cap.ROUND);//线的头尾形状  ，圆形
//        mPaint.setStrokeCap(Paint.Cap.SQUARE);//线的头尾形状  ，方形
        Path path = new Path();
        path.moveTo(100, 100);
        path.lineTo(300, 300);
        path.lineTo(100, 300);
        // mPaint.setStrokeJoin(Paint.Join.BEVEL);//直线
        mPaint.setStrokeJoin(Paint.Join.MITER);//锐角
//        mPaint.setStrokeJoin(Paint.Join.ROUND);//交汇的地方 圆弧
        canvas.drawPath(path, mPaint);

        path.moveTo(400, 100);
        path.lineTo(200, 300);
        path.lineTo(500, 300);
        mPaint.setStrokeJoin(Paint.Join.BEVEL);//直线
        canvas.drawPath(path, mPaint);
//        canvas.drawCircle(100, 100, 90, mPaint);

        //文字绘制
        //获得字符行间距
        //mPaint.getFontSpacing();
        //获得字符之间的间距
        //mPaint.getLetterSpacing();
        //  mPaint.setLetterSpacing();

        mPaint.setStrikeThruText(true); //设置文本删除线

        mPaint.setUnderlineText(true); //设置下划线

        //设置字体类型
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);

        // Typeface.create(familyName,style);//加载自定义字体

        mPaint.setTextSkewX(-0.25f);//倾斜  ，默认为0，官方推荐的  -0.25
        mPaint.setTextAlign(Paint.Align.LEFT); //文本对齐方式

        //文本
        mPaint.setTextSize(50);  // mPaint.getTextSize();
        //计算某个指定长度的字符串（字符长度，字符个数，显示的时候真实的长度）//中文占两个字符

       // float[] measuredWidth = new float[1];
       // int breakText = mPaint.breakText(str, true, 200, measuredWidth);
       // mPaint.getTextBounds(str,start,end,bounds); //获取文本的矩形区域（宽高）
       // Log.w("measuredWidth",measuredWidth[0]+"breakText"+breakText+ str);

        float[] measuredWidth = new float[10];
       // mPaint.measureText(str);  //测量文本的宽度，但是是一个比较粗略的结果
        mPaint.getTextWidths(str,measuredWidth) ;//测量文本的宽度，但比上面一个比较粗略的结果

        //canvas.drawText();
    }
}
