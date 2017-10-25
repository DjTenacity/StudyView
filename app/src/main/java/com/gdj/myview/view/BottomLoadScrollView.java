package com.gdj.myview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Comment:
 *
 * @author : dianjiegao / gaodianjie@zhimadi.cn
 * @version : 1.0
 * @date : 2017/8/25 10:17
 */
public class BottomLoadScrollView extends ScrollView {
    public BottomLoadScrollView(Context context) {
        super(context);
    }

    public BottomLoadScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomLoadScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);

        //到底部....
        if (clampedY) {
            if (listener != null) listener.loadData(false);
        }
    }

    LoadScrollViewListener listener;

    public void setListener(LoadScrollViewListener listener) {
        this.listener = listener;
    }

    public interface LoadScrollViewListener {

        public void loadData(boolean loadMore);
    }

}
