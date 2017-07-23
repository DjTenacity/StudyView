package com.gdj.myview.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/7/23.
 */

public class StudyTextView extends View {
    public StudyTextView(Context context) {
        this(context, null);
    }

    public StudyTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StudyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
