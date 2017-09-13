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

    private int radiu;
    private Paint paintCirular, tvPaint;
    private int center;

    String topStr, centerStr, bottomStr;
    private Rect textBounds;
    private Path path;
    private int padding;


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
        paintCirular.setStyle(Paint.Style.STROKE);

        tvPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        paintCirular.setStrokeWidth(4f);
        paintCirular.setColor(Color.parseColor("#B3D962"));

        tvPaint.setColor(Color.parseColor("#6FB513"));
        padding = getContext().getResources().getDimensionPixelSize(R.dimen.dp_4);
        tvPaint.setTextSize(getContext().getResources().getDimensionPixelSize(R.dimen.sp_12));
        tvPaint.setStyle(Paint.Style.FILL);

        textBounds = new Rect();

        path = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        //DisplayUtils.dp2px(4f)
        center = Math.min(width, height);
        radiu = center / 2 - 2;
        setMeasuredDimension(center, center);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // canvas.draw(center, center, center, paintCirular);
        RectF rectF = new RectF(0, 0, center, center);
        // path.reset();
        path.arcTo(rectF, 0, 360, true);
        canvas.drawPath(path, paintCirular);

        canvas.drawCircle(center / 2, center / 2, radiu, tvPaint);


        if (!TextUtils.isEmpty(centerStr)) {
            if (setBunds(centerStr, textBounds)) {
                canvas.drawText(centerStr, center / 2 - textBounds.width() / 2, center / 2 - textBounds.height() / 2, tvPaint);
            }
        }


        if (!TextUtils.isEmpty(bottomStr) && TextUtils.isEmpty(topStr)) {
            if (setBunds(bottomStr, textBounds)) {
            }
            float x = Math.min(judgeDistance(textBounds), padding);
            canvas.drawText(bottomStr, center / 2 - textBounds.width() / 2, center / 2 - textBounds.height() / 2 + x, tvPaint);

        }

        if (TextUtils.isEmpty(bottomStr) && !TextUtils.isEmpty(topStr)) {
            if (setBunds(topStr, textBounds)) {
                float x = Math.min(judgeDistance(textBounds), padding);
                canvas.drawText(topStr, center / 2 - textBounds.width() / 2, center / 2 - textBounds.height() / 2 - x, tvPaint);
            }
        }
        //上下的text都有,统一padding
        if (!TextUtils.isEmpty(bottomStr) && !TextUtils.isEmpty(topStr))
            drawUniteText(canvas, topStr, bottomStr, tvPaint);
    }

    private float judgeDistance(Rect textBounds) {
        return (float) (Math.sqrt(radiu * radiu - textBounds.width() * textBounds.width() / 4) - textBounds.height());
    }

    private void drawUniteText(Canvas canvas, String topStr, String bottomStr, Paint tvPaint) {
        String str = topStr;
        if (topStr.length() - bottomStr.length() < 0) {

        } else {
            str = bottomStr;
        }
        if (setBunds(str, textBounds)) {
            float x = Math.min(judgeDistance(textBounds), padding);
            canvas.drawText(topStr, center / 2 - textBounds.width() / 2, center / 2 - textBounds.height() / 2 - x, tvPaint);
            canvas.drawText(bottomStr, center / 2 - textBounds.width() / 2, center / 2 - textBounds.height() / 2 + x, tvPaint);
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

    public void setTopStr(String topStr) {
        this.topStr = topStr;
        invalidate();
    }

    public void setCenterStr(String centerStr) {
        this.centerStr = centerStr;
        invalidate();
    }

    public void setBottomStr(String bottomStr) {
        this.bottomStr = bottomStr;
        invalidate();
    }
}
