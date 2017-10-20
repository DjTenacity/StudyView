/*
 * Copyright 2016 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gdj.myview.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;


/**
 * Created on 2016/7/12.
 * 圆形进度条
 */
public class CircleTextProgressbar extends AppCompatTextView {

    /**
     * 外部轮廓的颜色。
     */
    private int outLineColor = Color.GRAY;

    /**
     * 外部轮廓的宽度。
     */
    private int outLineWidth = 12;

    /**
     * 内部圆的颜色。
     */
    private ColorStateList inCircleColors = ColorStateList.valueOf(Color.WHITE);
    /**
     * 中心圆的颜色。
     */
    private int circleColor;

    /**
     * 进度条的颜色。
     */
//    private int progressLineColor = Color.GREEN;
    private int progressLineColor = Color.GREEN;

    /**
     * 进度条的宽度。
     */
    private int progressLineWidth = 12;

    /**
     * 画笔。
     */
    private Paint mPaint = new Paint();

    /**
     * 进度条的矩形区域。
     */
    private RectF mArcRect = new RectF();

    /**
     * 进度。
     */
    private int progress = 0;

    /**
     * 最大进度
     */
    private int maxProgress = 100;
    /**
     * 进度条类型。
     */
    private ProgressType mProgressType = ProgressType.COUNT_BACK;
    /**
     * 进度倒计时时间。
     */
    private long timeMillis = 3000;

    /**
     * View的显示区域。
     */
    final Rect bounds = new Rect();

    /**
     * 设置中间显示的文本
     *
     * @param mText
     */
    public void setmText(String mText) {
        this.mText = mText;
    }

    public void setmTextColor(int mTextColor) {
        this.mTextColor = mTextColor;
    }

    private int mTextColor;

    private String mText;
    /**
     * 进度条通知。
     */
    private OnCountdownProgressListener mCountdownProgressListener;
    /**
     * Listener what。
     */
    private int listenerWhat = 0;

    public CircleTextProgressbar(Context context) {
        this(context, null);
    }

    public CircleTextProgressbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleTextProgressbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs);
    }

    /**
     * 初始化。
     *
     * @param context      上下文。
     * @param attributeSet 属性。
     */
    private void initialize(Context context, AttributeSet attributeSet) {
        mPaint.setAntiAlias(true);
        //  TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CircleTextProgressbar);
//        if (typedArray.hasValue(R.styleable.CircleTextProgressbar_in_circle_color)){
//            inCircleColors = typedArray.getColorStateList(R.styleable.CircleTextProgressbar_in_circle_color);
//        }
//        else{
//        }
        inCircleColors = ColorStateList.valueOf(Color.WHITE);

        circleColor = inCircleColors.getColorForState(getDrawableState(), Color.TRANSPARENT);
        //  typedArray.recycle();
    }

    /**
     * 设置外部轮廓的颜色。
     *
     * @param outLineColor 颜色值。
     */
    public void setOutLineColor(@ColorInt int outLineColor) {
        this.outLineColor = outLineColor;
        invalidate();
    }

    /**
     * 设置外部轮廓的颜色。
     *
     * @param outLineWidth 颜色值。
     */
    public void setOutLineWidth(@ColorInt int outLineWidth) {
        this.outLineWidth = outLineWidth;
        invalidate();
    }

    /**
     * 设置圆形的填充颜色。
     *
     * @param inCircleColor 颜色值。
     */
    public void setInCircleColor(@ColorInt int inCircleColor) {
        this.inCircleColors = ColorStateList.valueOf(inCircleColor);
        invalidate();
    }

    /**
     * 是否需要更新圆的颜色。
     */
    private void validateCircleColor() {
        int circleColorTemp = inCircleColors.getColorForState(getDrawableState(), Color.TRANSPARENT);
        if (circleColor != circleColorTemp) {
            circleColor = circleColorTemp;
            invalidate();
        }
    }

    /**
     * 设置进度条颜色。
     *
     * @param progressLineColor 颜色值。
     */
    public void setProgressColor(@ColorInt int progressLineColor) {
        this.progressLineColor = progressLineColor;
        invalidate();
    }

    /**
     * 设置进度条线的宽度。
     *
     * @param progressLineWidth 宽度值。
     */
    public void setProgressLineWidth(int progressLineWidth) {
        this.progressLineWidth = progressLineWidth;
        invalidate();
    }

    /**
     * 设置进度。
     *
     * @param progress 进度。
     */
    public void setProgress(int progress) {
        this.progress = validateProgress(progress);
        invalidate();
    }

    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
    }

    /**
     * 验证进度。
     *
     * @param progress 你要验证的进度值。
     * @return 返回真正的进度值。
     */
    private int validateProgress(int progress) {
        if (progress > maxProgress)
            progress = maxProgress;
        else if (progress < 0)
            progress = 0;
        return progress;
    }

    /**
     * 拿到此时的进度。
     *
     * @return 进度值，最大100，最小0。
     */
    public int getProgress() {
        return progress;
    }

    /**
     * 设置倒计时总时间。
     *
     * @param timeMillis 毫秒。
     */
    public void setTimeMillis(long timeMillis) {
        this.timeMillis = timeMillis;
        invalidate();
    }

    /**
     * 拿到进度条计时时间。
     *
     * @return 毫秒。
     */
    public long getTimeMillis() {
        return this.timeMillis;
    }

    /**
     * 设置进度条类型。
     *
     * @param progressType {@link ProgressType}.
     */
    public void setProgressType(ProgressType progressType) {
        this.mProgressType = progressType;
        resetProgress();
        invalidate();
    }

    /**
     * 重置进度。
     */
    private void resetProgress() {
        switch (mProgressType) {
            case COUNT:
                progress = 0;
                break;
            case COUNT_BACK:
                progress = maxProgress;
                break;
        }
    }

    /**
     * 拿到进度条类型。
     *
     * @return
     */
    public ProgressType getProgressType() {
        return mProgressType;
    }

    /**
     * 设置进度监听。
     *
     * @param mCountdownProgressListener 监听器。
     */
    public void setCountdownProgressListener(int what, OnCountdownProgressListener mCountdownProgressListener) {
        this.listenerWhat = what;
        this.mCountdownProgressListener = mCountdownProgressListener;
    }

    /**
     * 开始。
     */
    public void start() {
        stop();
        post(progressChangeTask);
    }

    /**
     * 重新开始。
     */
    public void reStart() {
        resetProgress();
        start();
    }

    /**
     * 停止。
     */
    public void stop() {
        removeCallbacks(progressChangeTask);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //获取view的边界
        getDrawingRect(bounds);

        int size = bounds.height() > bounds.width() ? bounds.width() : bounds.height();
        float outerRadius = size / 2;

        //画内部背景
        int circleColor = inCircleColors.getColorForState(getDrawableState(), 0);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(circleColor);
        canvas.drawCircle(bounds.centerX(), bounds.centerY(), outerRadius - progressLineWidth, mPaint);

//        //画边框圆
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(progressLineWidth);
        mPaint.setColor(outLineColor);
        canvas.drawCircle(bounds.centerX(), bounds.centerY(), outerRadius - progressLineWidth / 2, mPaint);
//        canvas.drawCircle(bounds.centerX(), bounds.centerY(), outerRadius - outLineWidth / 2, mPaint);

        //画字
        Paint paint = getPaint();
        paint.setColor(getCurrentTextColor());
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
        float textY = bounds.centerY() - (paint.descent() + paint.ascent()) / 2;
        canvas.drawText(getText().toString(), bounds.centerX(), textY, paint);

        //画进度条
        mPaint.setColor(progressLineColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(progressLineWidth);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        int deleteWidth = progressLineWidth;
        mArcRect.set(bounds.left + deleteWidth / 2, bounds.top + deleteWidth / 2, bounds.right - deleteWidth / 2, bounds.bottom - deleteWidth / 2);

        canvas.drawArc(mArcRect, -90, 360 * progress / maxProgress, false, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int lineWidth = 4 * (outLineWidth + progressLineWidth);
//        int width = getMeasuredWidth();
//        int height = getMeasuredHeight();
//        int size = (width > height ? width : height) + lineWidth;
//        setMeasuredDimension(size, size);
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        validateCircleColor();
    }

    /**
     * 进度更新task。
     */
    private Runnable progressChangeTask = new Runnable() {
        @Override
        public void run() {
            removeCallbacks(this);
            switch (mProgressType) {
                case COUNT:
                    progress += 1;
                    break;
                case COUNT_BACK:
                    progress -= 1;
                    break;
            }
            if (progress >= 0 && progress <= maxProgress) {
                if (mCountdownProgressListener != null)
                    mCountdownProgressListener.onProgress(listenerWhat, progress);
                invalidate();
                postDelayed(progressChangeTask, timeMillis / 100);
            } else {

                progress = validateProgress(progress);
            }
        }
    };

    /**
     * 进度条类型。
     */
    public enum ProgressType {
        /**
         * 顺数进度条，从0-100；
         */
        COUNT,

        /**
         * 倒数进度条，从100-0；
         */
        COUNT_BACK;
    }

    /**
     * 进度监听。
     */
    public interface OnCountdownProgressListener {

        /**
         * 进度通知。
         *
         * @param progress 进度值。
         */
        void onProgress(int what, int progress);
    }
}
