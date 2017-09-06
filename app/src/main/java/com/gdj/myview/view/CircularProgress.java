package com.gdj.myview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by cjt on 2017/9/5.
 */

public class CircularProgress extends View {

    private int center_X;
    private int center_Y;

    private int strokeWidth;
    private int spaceWidth;
    private int radius;

    private Paint mPaint;

    public CircularProgress(Context context) {
        this(context, null);
    }

    public CircularProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircularProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        strokeWidth = 90;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        center_X = getWidth() / 2;
        center_Y = getHeight() / 2;
        radius = getWidth() < getHeight() ? getWidth() / 2 : getHeight() / 2;
        strokeWidth = (int) (radius / 4.5);
        spaceWidth = strokeWidth / 9;
        mPaint.setStrokeWidth(strokeWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(0xff440000);
        canvas.drawCircle(center_X, center_Y, radius - strokeWidth / 2, mPaint);
        mPaint.setColor(0xff444400);
        canvas.drawCircle(center_X, center_Y, (float) (radius - strokeWidth * 1.5) - spaceWidth, mPaint);
        mPaint.setColor(0xff000044);
        canvas.drawCircle(center_X, center_Y, (float) (radius - strokeWidth * 2.5) - 2 * spaceWidth, mPaint);


        mPaint.setColor(0xffff0000);
        RectF rectF = new RectF(
                center_X - (radius - strokeWidth / 2),
                center_Y - (radius - strokeWidth / 2),
                center_X + (radius - strokeWidth / 2),
                center_Y + (radius - strokeWidth / 2));
        Path path = new Path();
        path.reset();
        path.arcTo(rectF, -90, 270, true);
        canvas.drawPath(path, mPaint);
        calculateItemPositions(canvas, path);

        mPaint.setColor(0xffffff00);
        rectF = new RectF(
                center_X - ((float) (radius - strokeWidth * 1.5) - spaceWidth),
                center_Y - ((float) (radius - strokeWidth * 1.5) - spaceWidth),
                center_X + ((float) (radius - strokeWidth * 1.5) - spaceWidth),
                center_Y + ((float) (radius - strokeWidth * 1.5) - spaceWidth));
        path.reset();
        path.arcTo(rectF, -90, 200, true);
        canvas.drawPath(path, mPaint);
        calculateItemPositions(canvas, path);

        mPaint.setColor(0xff0000ff);
        rectF = new RectF(
                center_X - ((float) (radius - strokeWidth * 2.5) - 2 * spaceWidth),
                center_Y - ((float) (radius - strokeWidth * 2.5) - 2 * spaceWidth),
                center_X + ((float) (radius - strokeWidth * 2.5) - 2 * spaceWidth),
                center_Y + ((float) (radius - strokeWidth * 2.5) - 2 * spaceWidth));
        path.reset();
        path.arcTo(rectF, -90, 230, true);
        canvas.drawPath(path, mPaint);
        calculateItemPositions(canvas, path);

    }

    private void calculateItemPositions(Canvas canvas, Path path) {

        PathMeasure measure = new PathMeasure(path, false);
        float[] startPoint = new float[]{0f, 0f};
        float[] endPoint = new float[]{0f, 0f};

        measure.getPosTan(0, startPoint, null);
        measure.getPosTan(measure.getLength(), endPoint, null);

        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(startPoint[0], startPoint[1], strokeWidth / 2, mPaint);
        canvas.drawCircle(endPoint[0], endPoint[1], strokeWidth / 2, mPaint);

        mPaint.setStyle(Paint.Style.STROKE);

    }
}
