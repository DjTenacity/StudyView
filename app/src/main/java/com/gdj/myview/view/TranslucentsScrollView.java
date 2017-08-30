package com.gdj.myview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Comment:  toolbar透明的scrollView,随着页面向下走动逐渐显示
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/8/19
 */
public class TranslucentsScrollView extends ScrollView {


    private int scrollY;
    private int height;

    public TranslucentsScrollView(Context context) {
        this(context, null);
    }

    public TranslucentsScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TranslucentsScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //屏幕高度
        height = getContext().getResources().getDisplayMetrics().heightPixels;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if (translucentsListener != null) {

            scrollY = getScrollY();
            if (scrollY <= height / 3f) {
                float alpht = 1 - (scrollY / (height / 3f));  //1~0
                translucentsListener.TranslucentsListener(alpht);
            }
        }
    }

    public interface TranslucentsListener {
        public void TranslucentsListener(float alpht);

    }

    public void setTranslucentsListener(TranslucentsListener translucentsListener) {
        this.translucentsListener = translucentsListener;
    }

    TranslucentsListener translucentsListener;
}
