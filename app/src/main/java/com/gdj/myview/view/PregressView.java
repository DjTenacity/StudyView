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
import android.util.TypedValue;
import android.view.View;

import com.gdj.myview.R;


/**
 * Created by raytine on 2017/7/25.
 */

public class PregressView extends View {
    private int mOutCycleColor = Color.BLUE;
    private int mInnerCycleColor = Color.RED;
    private int cycleWidth = 10;
    private int mTextSize = 15;
    private int mTextColor = Color.RED;
    private Paint mPaint;
    private int stepMax = 0;
    private int stepCurrent = 0;
    private Paint paint1, paint2, paint3;

    public PregressView(Context context) {
        this(context, null);
    }

    public PregressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PregressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.progressView);
        mOutCycleColor = typedArray.getColor(R.styleable.progressView_progressoutCycleColor, mOutCycleColor);
        mInnerCycleColor = typedArray.getColor(R.styleable.progressView_progressinnerCycleColor, mInnerCycleColor);
        mTextColor = typedArray.getColor(R.styleable.progressView_progressoutCycleColor, mTextColor);
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.progressView_progresstextSize, mTextSize);
        cycleWidth = typedArray.getDimensionPixelSize(R.styleable.progressView_progressCycleWidth, cycleWidth);
        typedArray.recycle();
        initPaint();
    }
    private float diptopx(int dip){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dip,getResources().getDisplayMetrics());
    }
    private void initPaint() {
        //外圆弧paint
        paint1 = new Paint();
        paint1.setAntiAlias(true);
        paint1.setColor(mOutCycleColor);
        paint1.setStrokeWidth(cycleWidth);
        paint1.setStyle(Paint.Style.STROKE);
//        paint1.setStrokeCap(Paint.Cap.ROUND);
        //内圆弧paint
        paint2 = new Paint();
        paint2.setAntiAlias(true);
        paint2.setColor(mInnerCycleColor);
        paint2.setStrokeWidth(cycleWidth);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setStrokeCap(Paint.Cap.ROUND);
        //画进度
        paint3 = new Paint();
        paint3.setAntiAlias(true);
        paint3.setColor(mInnerCycleColor);
        paint3.setTextSize(mTextSize);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int mode_width = MeasureSpec.getMode(widthMeasureSpec);
        int mode_height = MeasureSpec.getMode(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width > height ? height : width, width > height ? width : height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        //画外圆
        RectF rectF = new RectF(cycleWidth / 2, cycleWidth / 2, getWidth() - cycleWidth / 2, getHeight() - cycleWidth / 2);
//        canvas.drawArc(rectF, 180, 360, false, paint1);
        canvas.drawCircle(getWidth()/2,getWidth()/2,getWidth()/2-cycleWidth/2,paint1);
        float percent = ((float) stepCurrent / stepMax);
        canvas.drawArc(rectF, 90, 180 * percent, false, paint2);
        canvas.drawArc(rectF, 90, -180 * percent, false, paint2);
        if (stepMax != 0 && stepCurrent != 0) {
            //画文字
            float i = (float) stepCurrent / stepMax;
            String t = i + "";
            String split;
            try {
                split = t.substring(2, 4);
            } catch (Exception e) {
                split = "100";
            }

            String text = Integer.valueOf(split) + "%";
            Rect bomds = new Rect();
            paint3.getTextBounds(text, 0, text.length(), bomds);
            Paint.FontMetricsInt fontMetrics = paint3.getFontMetricsInt();
            int dy = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
            int baselines = getHeight() / 2 + dy;
            canvas.drawText(text, getWidth() / 2 - bomds.width() / 2, baselines, paint3);
        }
    }

    public synchronized void setStepMax(int stepMax) {
        this.stepMax = stepMax;
    }

    public synchronized void setStepCurrent(int stepCurrent) {
        this.stepCurrent = stepCurrent;
        invalidate();
    }
}
