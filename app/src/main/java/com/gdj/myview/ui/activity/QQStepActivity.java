package com.gdj.myview.ui.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import com.gdj.myview.R;
import com.gdj.myview.view.SingleLineFlowLayout;
import com.gdj.myview.view.PregressView;
import com.gdj.myview.view.QQStepView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 防QQ步数
 */

public class QQStepActivity extends AppCompatActivity {
    @BindView(R.id.qqstep)
    QQStepView qqs;
    @BindView(R.id.view)
    PregressView view;
    @BindView(R.id.btn)
    Button btn;
    @BindView(R.id.single_line_layout)
    SingleLineFlowLayout mSingleLineFlowLayout;

    private String[] mItems = {"1311", "223222", "1131", "222322", "1131", "222232", "1411", "22222", "11133", "22222"};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        ButterKnife.bind(this);


     //   mSingleLineFlowLayout = (SingleLineFlowLayout) findViewById(R.id.single_line_layout);

        mSingleLineFlowLayout.post(new Runnable() {
            @Override
            public void run() {
                for (String item : mItems) {
                    TextView tv = (TextView) LayoutInflater.from(QQStepActivity.this).inflate(
                            R.layout.item_flow_layout, mSingleLineFlowLayout, false);
                    tv.setText(item);
                    mSingleLineFlowLayout.addView(tv);
                }
            }
        });
    }


    @OnClick(R.id.btn)
    public void onViewClicked() {

        view.setStepMax(6000);
        ValueAnimator animator = ObjectAnimator.ofFloat(0, 6000);
        animator.setDuration(3000);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                view.setStepCurrent((int) animatedValue);
            }
        });
        animator.start();

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
