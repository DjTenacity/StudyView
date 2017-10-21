package com.gdj.myview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;

/**
 * Comment:
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/10/21
 */
public class RevealDrawable extends Drawable {

    private final Rect mTmpRect = new Rect();

    private final int mOrientation;
    Drawable mUnSelectedDrawable;
    Drawable mSelectedDrawable;

    public static final int HORIZONTAL = 1;
    public static final int VERTICAL = 2;

    public RevealDrawable(Drawable unselected, Drawable selected, int orientation) {
        this.mUnSelectedDrawable = unselected;
        this.mSelectedDrawable = selected;
        this.mOrientation = orientation;

    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        int level = getLevel();
        if (level == 10000 || level == 0) {
            mUnSelectedDrawable.draw(canvas);
        } else if (level == 5000) {
            mSelectedDrawable.draw(canvas);
        } else {
            //由两张图片拼接而成
            //得到当前drawable自身的矩形区域
            Rect bounds = getBounds();
            //先繪製灰色部分
            {
                int w = bounds.width();
                int h = bounds.height();

                //level:0~5000~10000
                //ratio:-1f ~ 0 ~-1f
                float ratio = (level / 5000) - 1f;
                w  = (int) (w * Math.abs(ratio));

                //从一个已有的bounds 矩形区域范围内抠出一个矩形出来
                int gravity = ratio > 0 ? Gravity.LEFT : Gravity.RIGHT;
                Rect r = mTmpRect;
                //左边或者右边扣 被扣的对象bounds  目标rect
                Gravity.apply(gravity, w, h, bounds, r);
                canvas.save();
                //把画布裁剪
                canvas.clipRect(r);
                //画图片
                mUnSelectedDrawable.draw(canvas);
                canvas.restore();
            }
            //彩色部分
            {

                int w = bounds.width();
                int h = bounds.height();

                //level:0~5000~10000
                //ratio:-1f ~ 0 ~-1f
                float ratio = (level / 5000) - 1f;
                w -= (int) (w * Math.abs(ratio));

                //从一个已有的bounds 矩形区域范围内抠出一个矩形出来
                int gravity = ratio < 0 ? Gravity.LEFT : Gravity.RIGHT;

                Rect r2 = mTmpRect;
                //左边或者右边扣 被扣的对象bounds  目标rect
                Gravity.apply(gravity, w, h, bounds, r2);
                canvas.save();//保存画布
                //把画布裁剪
                canvas.clipRect(r2);
                //画图片
                mSelectedDrawable.draw(canvas);
                canvas.restore();
            }
        }


    }

    @Override
    protected boolean onLevelChange(int level) {//0~10000,
        invalidateSelf();
        //  getLevel();
        return true;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        //定好了两个Drawable图片的宽高
        mUnSelectedDrawable.setBounds(bounds);
        mSelectedDrawable.setBounds(bounds);

    }

    @Override
    public int getIntrinsicWidth() {
        //得到Drawable的实际宽度
        return Math.max(mSelectedDrawable.getIntrinsicWidth(),
                mUnSelectedDrawable.getIntrinsicWidth());
    }

    @Override
    public int getIntrinsicHeight() {
        //得到Drawable的实际高度
        return Math.max(mSelectedDrawable.getIntrinsicHeight(),
                mUnSelectedDrawable.getIntrinsicHeight());
    }


    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }
}
