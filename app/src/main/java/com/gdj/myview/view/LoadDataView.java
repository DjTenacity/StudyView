package com.gdj.myview.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.gdj.myview.R;

/**
 * Created by Administrator on 2017/7/22.
 */

public class LoadDataView extends LinearLayout{

    public LoadDataView(Context context) {
        super(context);
    }

    public LoadDataView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadDataView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

   void initLayout(){
       //this代表parent
       //1,记载写好的xml布局
       //1.1实例化view
       //添加到该view
        inflate(getContext(), R.layout.view_intro_4,this);
     //  findViewById(R.id.)
       startFallAnimation();

    }
    View mShapeView;
    View mShadowView;


    //开始下落动画
    private void startFallAnimation() {
        //动画作用在shapeView上
        ObjectAnimator transtationAnition=ObjectAnimator.ofFloat(mShapeView,"translationY",0,80);

    }
}
