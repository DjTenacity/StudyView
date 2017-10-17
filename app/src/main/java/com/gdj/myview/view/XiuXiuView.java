package com.gdj.myview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.gdj.myview.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Comment:
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/10/5
 */
public class XiuXiuView extends View {
    private int raudis;
    private Bitmap mBitmap;
    int bitmapHeight;
    int bitmapWidth;

    int height;
    int width;
    private Paint paint;

    long current = System.currentTimeMillis();

    List<Integer> mList = new ArrayList<>();

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            invalidate();

            //隔一段时间就添加一个波
            if (System.currentTimeMillis() - current > 500) {
                mList.add(bitmapWidth / 2);
                current = System.currentTimeMillis();
            }

            //改变半径的值
            for (int i = 0; i < mList.size(); i++) {
                mList.set(i, mList.get(i) + 4);
            }

            //控制波的数量
            Iterator<Integer> iterator = mList.iterator();
            while (iterator.hasNext()) {
                Integer next = iterator.next();
                if (next > width / 2) {
                    if (mList.contains(next)) {
                        iterator.remove();
                    }
                }
            }

            handler.postDelayed(runnable, 16);
        }
    };

    public XiuXiuView(Context context) {
        this(context, null);
    }

    public XiuXiuView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XiuXiuView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor("#f0f567"));

        bitmapWidth = mBitmap.getWidth();
        bitmapHeight = mBitmap.getHeight();

        raudis = bitmapWidth / 2;

        mList.add(raudis);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        width = canvas.getWidth();
        height = canvas.getHeight();
        int left = width / 2 - bitmapWidth / 2;
        int top = height /4 - bitmapHeight / 2;
        //先把圈圈图片画上面
        //绘制波纹,鲜花一个波,,,同时改变透明度

        for (int i : mList) {

            paint.setAlpha(177 - 177 * (i - bitmapWidth / 2) / (width/ 2 - bitmapWidth / 2));
            canvas.drawCircle(width / 2, height / 4, i, paint);

        }

        canvas.drawBitmap(mBitmap, left, top, null);
    }

    public void start() {
        handler.post(runnable);
    }
}
