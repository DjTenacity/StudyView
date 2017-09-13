package com.gdj.myview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.gdj.myview.R;

/**
 * Comment:
 *
 * @author : DJ鼎尔东/ 1757286697@qq.com
 * @version : ${user} 1.0
 * @date : 2017/9/13 17:47
 */
public class CircularDataView extends View {

    private float radiu;
    private Paint paintCirular, tvPaint;
    private float center;

    String topStr, centerStr, bottomStr;
    private Rect textBounds;
    private int padding;
    private int paddingCenter;
    private float baseLine;
    private Paint paintSmallCirular;


    public CircularDataView(Context context) {
        this(context, null);
    }

    public CircularDataView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircularDataView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paintCirular = new Paint(Paint.ANTI_ALIAS_FLAG);
        tvPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintSmallCirular = new Paint(Paint.ANTI_ALIAS_FLAG);

        paintSmallCirular.setStyle(Paint.Style.FILL);
        paintCirular.setStyle(Paint.Style.FILL);

        paintCirular.setColor(Color.parseColor("#B3D962"));
        paintSmallCirular.setColor(Color.parseColor("#6FB513"));
        tvPaint.setColor(Color.WHITE);

        padding = getContext().getResources().getDimensionPixelSize(R.dimen.dp_8);
        tvPaint.setTextSize(getContext().getResources().getDimensionPixelSize(R.dimen.sp_12));

        textBounds = new Rect();

        //  path = new Path();
    }
    //  private Path path;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        //DisplayUtils.dp2px(4f)
        center = Math.min(width, height);
        radiu = center / 2 - 16f;
        //cirularCenter = center / 2 - 8f;
        setMeasuredDimension((int) center + 8, (int) center);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(center / 2, center / 2, center / 2, paintCirular);
        // RectF rectF = new RectF(0, 0, center, center);
        // path.reset();
        //path.arcTo(rectF,  90, 450 , true);
        // canvas.drawPath(path, paintCirular);

        canvas.drawCircle(center / 2, center / 2, radiu, paintSmallCirular);

        //基线  baseLine  大写字母位于基线上。最常见的例外是J和Q。不齐线数字（见阿拉伯数字）位于基线上。
        Paint.FontMetricsInt fontMetricsInt = tvPaint.getFontMetricsInt();
        float dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        baseLine = center / 2 + dy;

        if (!TextUtils.isEmpty(centerStr)) {
            if (setBunds(centerStr, textBounds)) {
                paddingCenter = textBounds.height() / 2;
                canvas.drawText(centerStr, center / 2 - textBounds.width() / 2, baseLine, tvPaint);
            }
        }


        if (!TextUtils.isEmpty(bottomStr) && TextUtils.isEmpty(topStr)) {
            if (setBunds(bottomStr, textBounds)) {

                float x = Math.min(judgeDistance(textBounds), padding);
                canvas.drawText(bottomStr, center / 2 - textBounds.width() / 2, baseLine + x + paddingCenter, tvPaint);

            }
        }

        if (TextUtils.isEmpty(bottomStr) && !TextUtils.isEmpty(topStr)) {
            if (setBunds(topStr, textBounds)) {
                float x = Math.min(judgeDistance(textBounds), padding);
                canvas.drawText(topStr, center / 2 - textBounds.width() / 2, baseLine - x - paddingCenter, tvPaint);
            }
        }
        //上下的text都有,统一padding
        if (!TextUtils.isEmpty(bottomStr) && !TextUtils.isEmpty(topStr))
            drawUniteText(canvas, topStr, bottomStr, tvPaint);
    }

    private float judgeDistance(Rect textBounds) {
        return (float) (Math.sqrt(radiu * radiu - textBounds.width() * textBounds.width() / 4 - padding * padding) - textBounds.height());
    }

    private void drawUniteText(Canvas canvas, String topStr, String bottomStr, Paint tvPaint) {
        String str = topStr;
        if (topStr.length() - bottomStr.length() > 0) {

        } else {
            str = bottomStr;
            bottomStr = topStr;
        }
        //str 是长的,bottomStr是短的
        if (setBunds(str, textBounds)) {
            float x = Math.min(judgeDistance(textBounds), padding);
            canvas.drawText(topStr, center / 2 - textBounds.width() / 2, baseLine - x - paddingCenter, tvPaint);
            setBunds(bottomStr, textBounds);
            canvas.drawText(bottomStr, center / 2 - textBounds.width() / 2, baseLine + x + paddingCenter, tvPaint);
        }
    }

    private boolean setBunds(String str, Rect textBounds) {
        tvPaint.getTextBounds(str, 0, str.length(), textBounds);
        if (center < textBounds.width()) {
            Toast.makeText(getContext(), str + "字太多了傻吊", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public void setTopStr(String topStr, boolean b) {
        this.topStr = topStr;
        if (b) invalidate();
    }

    public void setCenterStr(String centerStr, boolean b) {
        this.centerStr = centerStr;
        if (b) invalidate();
    }

    public void setBottomStr(String bottomStr, boolean b) {
        this.bottomStr = bottomStr;
        if (b) invalidate();
    }
}
