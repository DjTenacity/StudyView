package com.gdj.myview.view.searchview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Comment:
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/10/21
 */
public class SearchView2 extends View {
    private BaseController mCotroller;
    private Paint mPaint;

    public SearchView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mCotroller.draw(canvas, mPaint);
    }

    public void setCotroller(BaseController controller) {
        this.mCotroller = controller;
        mCotroller.setSearchView(this);
        invalidate();
    }

    public void start() {
        if (mCotroller != null) {
            mCotroller.startAnim();
        }

    }

    public void reset() {
        if (mCotroller != null) {
            mCotroller.resetAnim();
        }
    }
}
