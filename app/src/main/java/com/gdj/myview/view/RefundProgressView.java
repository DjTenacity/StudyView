package com.gdj.myview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.hulubao.android.tyc.R;

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
    int radius;//半径

    public RefundProgressView(Context context) {
        this(context, null);
    }

    public RefundProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefundProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        paint = new Paint();
        paddingBothSides = context.getResources().getDimensionPixelSize(R.dimen.dp_64);
        radius = context.getResources().getDimensionPixelSize( 8dp);
        stringList.add("提交申请");
        stringList.add("提交申请");
        stringList.add("提交申请");
        stringList.add("提交申请");
        stringList.add("提交申请");

    }

    List<String> stringList = new ArrayList<>();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int num = stringList.size();
        int length = getWidth() - paddingBothSides;
        paint.setStyle(Paint.Style.FILL);

        paint.setStrokeWidth(radius);

        int y = paddingBothSides + radius;

        canvas.drawCircle(y, y, radius, paint);

        //stringList长度为x,  总长度为(x-1)*2个线段,2 x 个半圆,线段长度为:
        if (num < 1) return;

        int lineLength = (length - (2 * num * radius)) / (2 * (num - 1));

        paint.setColor(Color.GRAY);

        //全长的灰线
        canvas.drawLine(y, y, length - radius + lineLength, y, paint);


        canvas.drawLine(y + lineLength, y,
                length - radius, y, paint);


        paint.setColor(Color.GREEN);

        canvas.drawLine(y + (lineLength + radius) * position, y, y + lineLength, y, paint);

        for (int i = 0; i < num; i++) {
            if (num > position)
                paint.setColor(Color.GRAY);
                canvas.drawCircle(y + (lineLength + radius) * position, y, radius, paint);


        }

        // canvas.drawCircle((length + paddingBothSides) / 2, y, radius, paint);
        // canvas.drawCircle(length - radius, y, radius, paint);
    }


    public void setTextList(List<String> stringList) {
        this.stringList = stringList;
    }
}
