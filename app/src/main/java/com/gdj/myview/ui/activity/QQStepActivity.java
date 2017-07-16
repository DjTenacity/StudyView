package com.gdj.myview.ui.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.gdj.myview.R;
import com.gdj.myview.view.QQStepView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *  防QQ步数
 */

public class QQStepActivity extends AppCompatActivity {
    @BindView(R.id.qqstep)
    QQStepView qqs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        ButterKnife.bind(this);

        //属性动画写在这里,面向对象
        qqs.setStepMax(4000);
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 3000);
        valueAnimator.setDuration(1000);
        valueAnimator.setInterpolator(new DecelerateInterpolator());//插值器，先快后慢
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentStep = (float) animation.getAnimatedValue();//提供了float对象，在下面抢转
                qqs.setCurrentStep((int) currentStep);
            }
        });
            valueAnimator.start();
    }
}
