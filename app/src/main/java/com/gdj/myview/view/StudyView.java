package com.gdj.myview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.gdj.myview.R;
import com.gdj.myview.utils.UIUtils;

/**
 * Comment:
 *
 * @author : DJ鼎尔东/ 1757286697@qq.com
 * @version : ${user} 1.0
 * @date : 2017/8/7 15:54
 */
public class StudyView extends View {

    float mRectPercent = 0.8f;

    int mWidth = 0;
    private int mColor;
    private int mMode;
    private int mWaveWidth, mWaveCount,mHeight ;
    private float mRectWidth,mRectHeight;
    private float  startX,startY;
    private Paint paint;

    public StudyView(Context context) {
        this(context, null);
    }

    public StudyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StudyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.WaveView, defStyleAttr, 0);
        mWaveCount = typedArray.getInt(R.styleable.WaveView_waveCount, 10);
        mWaveWidth = typedArray.getInt(R.styleable.WaveView_waveWidth, 10);
        mMode = typedArray.getInteger(R.styleable.WaveView_mode, -2);
        mColor = typedArray.getColor(R.styleable.WaveView_android_color, Color.parseColor("#2C97DE"));
        typedArray.recycle();

        paint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        // UNSPECIFIED	父容器没有对当前View有任何限制，当前View可以任意取尺寸
        // EXACTLY	当前的尺寸就是当前View应该取的尺寸
        // AT_MOST	当前尺寸是当前View能取的最大尺寸
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        //矩形宽度为view的80%
        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;

            mRectWidth = (float) (mWidth * 0.8);

            //如果是wrap_content 直接给一个定值
        } else if (widthMode == MeasureSpec.AT_MOST) {
            mWidth = UIUtils. dip2px(300 );

            mRectWidth = (float) (mWidth * 0.8);
        }


        //矩形高度为view的80%
        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
            mRectHeight = (float) (mHeight * 0.8);
            //如果是wrap_content 直接给一个定值
        } else if (heightMode == MeasureSpec.AT_MOST) {

            mHeight = UIUtils. dip2px(200 );

            mRectHeight = (float) (mHeight * 0.8);
        }


        setMeasuredDimension(mWidth, mHeight);
    }

    /**
     * 构造方法里要调用a.recycle();
     * onDraw里最好不要创建对象。。。
     */

    @Override
    protected void onDraw(Canvas canvas) {
        //调用父View的onDraw函数，因为View这个类帮我们实现了一些
        // 基本的而绘制功能，比如绘制背景颜色、背景图片等
        super.onDraw(canvas);
        int r = 30;// 可以是getMeasuredWidth()/2
        //圆心的横坐标为当前的View的左边起始位置+半径
        int centerX = getLeft() + r;
        //圆心的纵坐标为当前的View的顶部起始位置+半径
        int centerY = r;
        Log.w("getTop", getTop() + "width");



        paint.setColor(Color.BLUE);//ff7895
        //如果设置了圆弧的宽度,那么就要注意圆心了兄弟
        // paint.setStrokeWidth(getContext().getResources().getDimensionPixelSize(R.dimen.dp_4));
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        //开始绘制
        canvas.drawCircle(centerX, centerY, r, paint);

        //画气泡  start
        //left, top, right, bottom
        canvas.drawRoundRect(new RectF(getLeft() + 3 * r, 0, getLeft() + 7 * r, 4 * r), 10, 10, paint);

        Path path = new Path();
        path.moveTo(getLeft() + 5 * r - 10, 4 * r);
        path.lineTo(getLeft() + 5 * r, 4 * r + 16);
        path.lineTo(getLeft() + 5 * r + 10, 4 * r);
        path.close();

        canvas.drawPath(path, paint);
        //画气泡  end


        //波浪 start
        //计算每个三角形的高
        int mWaveHeight = 6 * r / mWaveCount;
        //绘制矩形

        //计算padding
        float padding = ((mWidth - mRectWidth) / 2);
        //left, top, right, bottom
        canvas.drawRect(padding , padding+3*r, mRectWidth + padding, mRectHeight + padding, paint);

        //绘制左边的波浪
        startX = padding ;
        startY = padding+3*r;
        path.moveTo(startX, startY);
        for (int i = 0; i < mWaveCount; i++) {
            path.lineTo(startX - mWaveWidth, startY + i * mWaveHeight + (mWaveHeight / 2));
            path.lineTo(startX, startY + mWaveHeight * (i + 1));
        }
        //绘制右边的波浪
        float startX = padding + mRectWidth;
        float startY = padding+3*r;
        Path pathR = new Path();
        pathR.moveTo(startX, startY);
        for (int i = 0; i < mWaveCount; i++) {
            pathR.lineTo(startX + mWaveWidth, startY + i * mWaveHeight + (mWaveHeight / 2));
            pathR.lineTo(startX, startY + mWaveHeight * (i + 1));

        }
        pathR.close();
        canvas.drawPath(pathR, paint);
        pathR.close();
        canvas.drawPath(path, paint);
        //波浪 end


    }
}
