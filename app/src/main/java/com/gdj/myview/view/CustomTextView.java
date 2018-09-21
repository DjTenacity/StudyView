package com.gdj.myview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.gdj.myview.R;

/**
 * 描   述: 倾斜角标
 * 创 建 人: gaodianjie
 * 创建日期: 2018/9/21 16:39
 */
public class CustomTextView extends View {

    private Context mContext;
    private Paint mPaintText;
    private Paint mPaint;
    private String mText = "";//需要绘制的文字
    private int mTextColor;
    private float mTextSize;
    private int mTextBgColor;
    private float mOffset = 2f;//背景色的高度偏移量控制值(文字高度的mOffset倍数)

    public void setmText(String mText) {
        this.mText = mText;
    }

    public void setmTextBgColor(int mTextBgColor) {
        this.mTextBgColor = mTextBgColor;
        mPaint.setColor(mTextBgColor);
    }

    public CustomTextView(Context context) {
        this(context, null);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomTextView);
        mText = array.getString(R.styleable.CustomTextView_ct_title_name);
        mTextColor = array.getColor(R.styleable.CustomTextView_ct_title_color, Color.WHITE);
        mTextSize = array.getDimension(R.styleable.CustomTextView_ct_title_size, sp2px(context, 10));
        mTextBgColor = array.getColor(R.styleable.CustomTextView_ct_title_bd_color, Color.RED);
        array.recycle();

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mTextBgColor);

        mPaintText = new Paint();
        mPaintText.setAntiAlias(true);
        mPaintText.setColor(mTextColor);
        mPaintText.setTextSize(mTextSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int size = width < height ? width : height;
        /**
         * 这里高度要和宽度一致，这样才能保证效果(不考虑测量模式)
         */
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         * 实现的原理，先把画布以中心点为准旋转45度，
         * 然后画文字，计算文字的高度，根据这个高度稍微再扩大一些做为背景条的高度，再画背景条(一条比较粗的直线)
         */
        canvas.rotate(45, getWidth() / 2, getWidth() / 2);

        if (!TextUtils.isEmpty(mText)) {
            Paint.FontMetricsInt fontMetricsInt = mPaintText.getFontMetricsInt();
            float dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
            /**
             * 这里是设置文字的背景色块的高度，根据文字的高度
             */
            float mTextBackGroundHeight = dy * 2 * mOffset;
            mPaint.setStrokeWidth(mTextBackGroundHeight);

            /**
             * 这里是求出背景块的中心点Y坐标
             */
            float startY = (getWidth() - mTextBackGroundHeight) / 2;

            /**
             * 这里x代表画布旋转以后，画笔需要延伸到旋转之前的那个点，具体请看博客介绍
             */
            float x = (float) ((Math.sqrt(2 * getWidth() * getWidth()) - getWidth()) / 2);
            canvas.drawLine(-x, startY, getWidth() + x, startY, mPaint);

            //画文字
            float mTetxWidth = mPaintText.measureText(mText, 0, mText.length());
            float dx = (getWidth() - mTetxWidth) / 2;
            canvas.drawText(mText, dx, startY + dy, mPaintText);
        }
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}