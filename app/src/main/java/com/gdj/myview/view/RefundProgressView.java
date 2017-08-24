package com.gdj.myview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.gdj.myview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Comment:工作进程展示条
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
    private Paint loadingPoint;
    private int pvBackgroundColor, pvPassColor, pvTextColor, pvPassTextColor;

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
        loadingPoint = new Paint();


        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RefundProgressView);
        pvBackgroundColor = typedArray.getColor(R.styleable.RefundProgressView_pvBackgroundColor, Color.GRAY);
        pvTextColor = typedArray.getColor(R.styleable.RefundProgressView_pvTextColor, Color.GRAY);
        pvPassColor = typedArray.getColor(R.styleable.RefundProgressView_pvPassBackgroundColor, Color.GREEN);
        pvPassTextColor = typedArray.getColor(R.styleable.RefundProgressView_pvPassTextColor, Color.GREEN);


        int pvTextSize = typedArray.getDimensionPixelSize(R.styleable.RefundProgressView_pvRadius,
                context.getResources().getDimensionPixelSize(R.dimen.sp_12));

        radius = typedArray.getDimensionPixelSize(R.styleable.RefundProgressView_pvRadius,
                context.getResources().getDimensionPixelSize(R.dimen.dp_8));


        typedArray.recycle();

        paint.setAntiAlias(true);
        tvPaint.setAntiAlias(true);
        loadingPoint.setAntiAlias(true);


        tvPaint.setTextSize(pvTextSize);
        tvPaint.setColor(pvTextColor);

        paint.setColor(pvBackgroundColor);
        loadingPoint.setColor(pvPassColor);

        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(radius);

        loadingPoint.setStyle(Paint.Style.FILL);
        loadingPoint.setStrokeWidth(radius);

        stringList.add("提交申请");
        stringList.add("提交申");
        stringList.add("提交申请啊");
        num = stringList.size();
    }

    private int mWidth;
    private int mHeight;

    /**
     * MeasureSpec .AT_MOST  在布局中指定了warp_content
     * MeasureSpec.EXACTLY    在布局中指定了固定的大小  100dp  match_parent  fill_parent
     * MeasureSpec.UNSPECIFIED  尽可能的大 , 很少用得到
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        // int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        // int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        } else {
            //获取[屏幕宽度的两种方式
//            WindowManager wm = (WindowManager) getContext()   .getSystemService(Context.WINDOW_SERVICE);
//            mWidth = wm.getDefaultDisplay().getWidth();
            //	float density = resources.getDisplayMetrics().density;
            mWidth = getContext().getResources().getDisplayMetrics().widthPixels;
        }

        paddingBothSides = mWidth / (num + 4);

        mHeight = 2 * paddingBothSides + 4 * radius;
        setMeasuredDimension(mWidth, mHeight);
    }

    List<String> stringList = new ArrayList<>();
    int lineLength = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        if (num == 0) return;

        if (radius > (getWidth() - 2 * paddingBothSides) / 2 * num) {
            radius = (getWidth() - 2 * paddingBothSides) / (4 * num - 2);
        }

        y = paddingBothSides + radius;

        length = getWidth() - 2 * y;
        //stringList长度为x,  总长度为(x-1)*2个线段,2 x 个半圆,线段长度为:

        if (num > 1) lineLength = length / (num - 1);

        if (num == 1) lineLength = length;


        //全长的灰线
        canvas.drawLine(y, y, getWidth() - y, y, paint);

        //终点的x坐标
        float end = y + lineLength * (position + 0.5f);
        if (end >= getWidth() - y) {
            end = getWidth() - y;
        }
        Rect textBounds = new Rect();
        for (int i = 0; i < num; i++) {
            tvPaint.getTextBounds(stringList.get(i), 0, stringList.get(i).length(), textBounds);
            if (i == position) {
                tvPaint.setColor(pvPassTextColor);
            } else {
                tvPaint.setColor(pvTextColor);
            }
            canvas.drawText(stringList.get(i), y + lineLength * i - textBounds.width() / 2, y + radius * 4, tvPaint);
            canvas.drawCircle(y + lineLength * i, y, radius, paint);

            if (i <= position) {
                if (end * currentStep >= y + lineLength * i) { //radius * 0.2f +
                    canvas.drawCircle(y + lineLength * i, y, radius, loadingPoint);
                }
            }
        }

        canvas.drawLine(y, y, (end - y) * currentStep + y, y, loadingPoint);
    }

    float currentStep;

    public void setTextList(List<String> stringList) {
        this.stringList = stringList;
        num = stringList.size();
    }

    public synchronized void setCurrentStep(float currentStep) {
        this.currentStep = currentStep;

        invalidate();
    }

    public void setPosition(int position) {
        this.position = position;
    }
    /**    <declare-styleable name="RefundProgressView">
     <attr name="pvBackgroundColor" format="color" />
     <attr name="pvPassBackgroundColor" format="color" />
     <attr name="pvTextColor" format="color" />
     <attr name="pvTextSize" format="dimension" />
     <attr name="pvTextPaddingTop" format="dimension" />
     <attr name="pvPadding" format="dimension" />
     <attr name="pvRadius" format="dimension" />
     </declare-styleable>

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
