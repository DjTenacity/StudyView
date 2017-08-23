package com.gdj.myview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


import com.gdj.myview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Comment:
 *
 * @author : dianjiegao / gaodianjie@zhimadi.cn
 * @version : 1.0
 * @date : 2017/8/23 17:50
 */
public class RefundProgressView extends View {

    private Paint paint;
    int position = 2;
    int paddingBothSides;
    int paddingTop;
    int radius;//半径
    private Paint tvPaint;
    private int num;
    private int y;
    private int length;

    public RefundProgressView(Context context) {
        this(context, null);
    }

    public RefundProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefundProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        paint = new Paint();
        tvPaint = new Paint();

        tvPaint.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.sp_12));
        tvPaint.setColor(Color.GRAY);

        paddingBothSides = context.getResources().getDimensionPixelSize(R.dimen.dp_28);
        radius = context.getResources().getDimensionPixelSize(R.dimen.dp_8);
        stringList.add("提交申请");
        stringList.add("提交申请");
        stringList.add("提交申请");
        stringList.add("提交申请");
        stringList.add("提交申请");


    }


    List<String> stringList = new ArrayList<>();
    int lineLength = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        num = stringList.size();

        if (num == 0) return;

        paint.setStyle(Paint.Style.FILL);

        paint.setStrokeWidth(radius);

        y = paddingBothSides + radius;

        length = getWidth() - 2 * y;
        //stringList长度为x,  总长度为(x-1)*2个线段,2 x 个半圆,线段长度为:
        if (num == 0) return;

        if (num > 1) lineLength = length / (num - 1);

        if (num == 1) lineLength = length;

        paint.setColor(Color.GRAY);

        //全长的灰线
        canvas.drawLine(y, y, getWidth() - y, y, paint);

        Paint.FontMetrics fontMetrics = tvPaint.getFontMetrics();

        for (int i = 0; i < num; i++) {
            float textMe = paint.measureText(stringList.get(i));

            canvas.drawText(stringList.get(i), y + lineLength * i - textMe, y + radius * 4 + fontMetrics.bottom, tvPaint);

            if (i > position) {
                paint.setColor(Color.GRAY);
                canvas.drawCircle(y + lineLength * i, y, radius, paint);
            } else {

                paint.setColor(Color.GREEN);

                if (y + lineLength * (position + 0.5f) * currentStep >= y + lineLength * i) {
                    canvas.drawCircle(y + lineLength * i, y, radius, paint);
                }
            }
        }

        paint.setColor(Color.GREEN);

        canvas.drawLine(y, y, y + lineLength * (position + 0.5f) * currentStep, y, paint);

        // canvas.drawCircle((length + paddingBothSides) / 2, y, radius, paint);
        // canvas.drawCircle(length - radius, y, radius, paint);
    }

    float currentStep;

    public void setTextList(List<String> stringList) {
        this.stringList = stringList;
    }

    public synchronized void setCurrentStep(float currentStep) {
        this.currentStep = currentStep;
        invalidate();
    }

    public void setPosition(int position) {
        this.position = position;
    }
    /** <declare-styleable name="RefundProgressView">
     <attr name="pvBackgroundColor" format="color" />
     <attr name="pvPassBackgroundColor" format="color" />
     <attr name="pvTextColor" format="color" />
     <attr name="pvTextSize" format="dimension" />
     <attr name="pvTextPaddingTop" format="dimension" />
     <attr name="pvPadding" format="dimension" />
     </declare-styleable>**/

    /**
     *
     ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 1);
     valueAnimator.setDuration(3000);
     valueAnimator.setInterpolator(new DecelerateInterpolator());//插值器，先快后慢
     valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
    @Override public void onAnimationUpdate(ValueAnimator animation) {
    float currentStep = (float) animation.getAnimatedValue();//提供了float对象，在下面抢转
    rpv.setCurrentStep(  currentStep);
    }
    });
     valueAnimator.start();*/
}
