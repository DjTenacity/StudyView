package com.gdj.myview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.gdj.myview.R;
import com.gdj.myview.utils.DisplayUtils;
import com.gdj.myview.utils.UIUtils;

/**
 * Comment:  仪表盘
 *
 * @author : DJ鼎尔东/ 1757286697@qq.com
 * @version : ${user} 1.0
 * @date : 2017/8/8 09:46
 */
public class PanelView extends View {

    private int mWidth;
    private int mHeight;

    private int mPercent;

    //刻度宽度
    private float mTikeWidth;

    //第二个弧的宽度
    private int mScendArcWidth;

    //最小圆的半径
    private int mMinCircleRadius;

    //文字矩形的宽
    private int mRectWidth;

    //文字矩形的高
    private int mRectHeight;


    //文字内容
    private String mText = "";

    //文字的大小
    private int mTextSize;

    //设置文字颜色
    private int mTextColor;
    private int mArcColor;

    //小圆和指针颜色
    private int mMinCircleColor;

    //刻度的个数
    private int mTikeCount;

    private Context mContext;
    private Paint p;
    private float secondRectWidth;
    private float secondRectHeight;
    private float percent;
    private RectF secondRectF;


    public PanelView(Context context) {
        this(context, null);
    }

    public PanelView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PanelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PanelView, defStyleAttr, 0);
        mArcColor = a.getColor(R.styleable.PanelView_arcColor, Color.parseColor("#5FB1ED"));
        mMinCircleColor = a.getColor(R.styleable.PanelView_pointerColor, Color.parseColor("#C9DEEE"));
        mTikeCount = a.getInt(R.styleable.PanelView_tikeCount, 12);
        mTextSize = a.getDimensionPixelSize(UIUtils.dip2px(R.styleable.PanelView_android_textSize), 24);
        mText = a.getString(R.styleable.PanelView_android_text);
        mScendArcWidth = 50;
        a.recycle();

        p = new Paint();
    }

    /**
     * MeasureSpec .AT_MOST  在布局中指定了warp_content
     * MeasureSpec.EXACTLY    在布局中指定了固定的大小  100dp  match_parent  fill_parent
     * MeasureSpec.UNSPECIFIED  尽可能的大 , 很少用得到
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) { //在布局中指定了固定的大小
            mWidth = widthSize;
        } else {
            mWidth = DisplayUtils.dp2px(200);
        }


        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        } else {
            mHeight = DisplayUtils.dp2px(200);
        }
        setMeasuredDimension(mWidth, mHeight);

    }
    //分解成如下：1.最外面的弧   2.里面的粗弧   3.中间小圆   4.最小的圆  5.刻度   6.指针  7.矩形  8.文字


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int strokeWidth = 3;
        p.setStrokeWidth(strokeWidth);
        p.setAntiAlias(true);
        p.setStyle(Paint.Style.STROKE);
        p.setColor(mArcColor);
        //最外面线条
        canvas.drawArc(new RectF(strokeWidth, strokeWidth, mWidth - strokeWidth, mHeight - strokeWidth), 145, 250, false, p);

        /**因为大圆和里面粗弧的长短不一致，这里使用百分比来计算 所以会造成指针偏差，
         * 那么这里把 1、2两个部分固定来画，然后是3 充满的部分，用百分比来计算需要画多少度，最后是4 空白的部分。
         首先把粗弧的矩形画出来，这里固定了比大弧半径少50（这里其实可以改进，你可以改成动态的让他更灵活），然后计算出百分比*/

        secondRectF = new RectF(strokeWidth + 50, strokeWidth + 50, mWidth - strokeWidth - 50, mHeight - strokeWidth - 50);
        secondRectWidth = mWidth - strokeWidth - 50 - (strokeWidth + 50);
        secondRectHeight = mHeight - strokeWidth - 50 - (strokeWidth + 50);
        percent = mPercent / 100f;
    }
}
