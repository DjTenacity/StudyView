package com.gdj.myview.ui.listener;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;

/**
 * Comment:
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/10/2
 */
public class FabOnScrollListener extends OnScrollListener {

    private static final int THRESHOLD = 20;
    private int distance = 0;

    HideScrollListener mHideListener;

    public FabOnScrollListener(HideScrollListener hideScrollListener) {
        this.mHideListener = hideScrollListener;
    }

    boolean visible = true;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        /**
         * dy:Y轴方向的增量,有正有负
         * 正在执行动画的时候,就不要再执行了
         * */

        if (distance > THRESHOLD && visible) {
            //隐藏
            visible = false;
            if (mHideListener != null) mHideListener.onHide();
            distance = 0;
        } else if (distance < -20 && !visible) {
            //显示
            visible = true;
            if (mHideListener != null) mHideListener.onShow();
            distance = 0;
        }

        if (dy > 0 && visible || !visible && dy < 0) {
            distance += dy;

        }
    }
}
