package com.gdj.myview.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.Toast;

import com.gdj.myview.R;
import com.lilei.springactionmenu.ActionMenu;
import com.lilei.springactionmenu.OnActionItemClickListener;

/**
 * Comment:
 *
 * @author : DJ鼎尔东/ 1757286697@qq.com
 * @version : ${user} 1.0
 * @date : 2017/8/8 14:10
 */
public class AnimationActivity extends AppCompatActivity {
    int position = 0;
    private View ll_frist;
    private View second;
    private Button btn;
    private ActionMenu actionMenuTop;
    private ActionMenu actionMenuBottom;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_animation);

        ll_frist = findViewById(R.id.ll_frist);
        second = findViewById(R.id.second);
        btn = (Button) findViewById(R.id.btn);
        setActionMenu();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //21
            Slide slide=new Slide();
            slide.setDuration(500);
            getWindow().setExitTransition(slide);//出去的动画
            getWindow().setEnterTransition(slide);//进来的动画
        }
    }

    private void setActionMenu() {
        actionMenuTop = (ActionMenu) findViewById(R.id.actionMenuTop);
        actionMenuBottom = (ActionMenu) findViewById(R.id.actionMenuBottom);

        // add menu items
        actionMenuTop.addView(R.drawable.ic_zoom_in_black_24dp, getItemColor(R.color.menuNormalInfo), getItemColor(R.color.menuPressInfo));
        actionMenuTop.addView(R.drawable.like, getItemColor(R.color.menuNormalRed), getItemColor(R.color.menuPressRed));
        actionMenuTop.addView(R.drawable.write);

        actionMenuBottom.addView(R.drawable.search, getItemColor(R.color.menuNormalInfo), getItemColor(R.color.menuPressInfo));
        actionMenuBottom.addView(R.drawable.like, getItemColor(R.color.menuNormalRed), getItemColor(R.color.menuPressRed));
        actionMenuBottom.addView(R.drawable.write);


        actionMenuBottom.setItemClickListener(new OnActionItemClickListener() {
            @Override
            public void onItemClick(int index) {
                Toast.makeText(AnimationActivity.this, "Click " + index, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationEnd(boolean isOpen) {
                //       showMessage("animation end : " + isOpen);
            }
        });
    }
    private int getItemColor(int colorID) {
        return getResources().getColor(colorID);
    }



    //第一个 view 动画:翻转动画,透明度,缩小放大
    public void startFrist(View v) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //圆形水波纹揭露效果
            Animator ani = ViewAnimationUtils.createCircularReveal(btn, 0, 0, 0, (float) Math.hypot(btn.getWidth(), btn.getHeight()));
            ani.setDuration(1000);
            ani.setInterpolator(new AccelerateDecelerateInterpolator());
            ani.start();
        }


//        ObjectAnimator rotationFAnimation = ObjectAnimator.ofFloat(ll_frist, "rotationX", 0, 25f);
//        rotationFAnimation.setDuration(500);

        ObjectAnimator alphaFAnimation = ObjectAnimator.ofFloat(ll_frist, "alpha", 1f, 0.5f);
        alphaFAnimation.setDuration(500);

        ObjectAnimator scaleXFAnimation = ObjectAnimator.ofFloat(ll_frist, "scaleX", 1f, 0.8f);
        scaleXFAnimation.setDuration(500);

        ObjectAnimator scaleYFAnimation = ObjectAnimator.ofFloat(ll_frist, "scaleY", 1f, 0.8f);
        scaleYFAnimation.setDuration(500);

//        //执行完毕后再执行反向旋转
//        ObjectAnimator rotationFAnimation2 = ObjectAnimator.ofFloat(ll_frist, "rotationX", 25f, 0f);
//        rotationFAnimation2.setDuration(500);
//        rotationFAnimation2.setStartDelay(500);//延迟执行

        //由于缩放造成了离顶部一段距离,需要平移上去, 不乘以0.1f,就飞没了  (自己高度的10%,getHeight()缩放后不变)
        ObjectAnimator translationFAnimation2 = ObjectAnimator.ofFloat(ll_frist, "translationY", 0f, -0.1f * ll_frist.getHeight());
        translationFAnimation2.setDuration(500);
        translationFAnimation2.setStartDelay(500);//延迟执行

        ObjectAnimator translationSecAnimation = ObjectAnimator.ofFloat(second, "translationY", second.getHeight(), 0f);
        translationSecAnimation.setDuration(500);
        translationSecAnimation.setStartDelay(500);//延迟执行

        translationSecAnimation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                second.setVisibility(View.VISIBLE);

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                btn.setClickable(false);
            }
        });   //  rotationFAnimation,  rotationFAnimation2,

        AnimatorSet set = new AnimatorSet();
        set.playTogether(alphaFAnimation, scaleXFAnimation, scaleYFAnimation, translationFAnimation2, translationSecAnimation);
        set.start();


    }

    //第二个 view 动画:平移动画
    public void startSecond(View v) {
        ObjectAnimator rotationFAnimation = ObjectAnimator.ofFloat(ll_frist, "rotationX", 0, 30);
        rotationFAnimation.setDuration(500);

        ObjectAnimator alphaFAnimation = ObjectAnimator.ofFloat(ll_frist, "alpha", 0.5f, 1f);
        alphaFAnimation.setDuration(500);

        ObjectAnimator scaleXFAnimation = ObjectAnimator.ofFloat(ll_frist, "scaleX", 0.8f, 1f);
        scaleXFAnimation.setDuration(500);

        ObjectAnimator scaleYFAnimation = ObjectAnimator.ofFloat(ll_frist, "scaleY", 0.8f, 1f);
        scaleYFAnimation.setDuration(500);

        //执行完毕后再执行反向旋转
        ObjectAnimator rotationFAnimation2 = ObjectAnimator.ofFloat(ll_frist, "rotationX", 30f, 0);
        rotationFAnimation2.setDuration(500);
        rotationFAnimation2.setStartDelay(500);//延迟执行

        //由于缩放造成了离顶部一段距离,需要平移上去, 不乘以0.1f,就飞没了  (自己高度的10%,getHeight()缩放后不变)
        ObjectAnimator translationFAnimation2 = ObjectAnimator.ofFloat(ll_frist, "translationY", -0.1f * ll_frist.getHeight(), 0f);
        translationFAnimation2.setDuration(500);
        translationFAnimation2.setStartDelay(500);//延迟执行

        ObjectAnimator translationSecAnimation = ObjectAnimator.ofFloat(second, "translationY", 0f, second.getHeight());
        translationSecAnimation.setDuration(500);
        translationSecAnimation.setStartDelay(500);//延迟执行

        translationFAnimation2.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                btn.setClickable(true);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                second.setVisibility(View.INVISIBLE);
            }
        });

        AnimatorSet set = new AnimatorSet();//rotationFAnimation2,  rotationFAnimation2,
        set.playTogether(alphaFAnimation, scaleXFAnimation, scaleYFAnimation, translationFAnimation2, translationSecAnimation);
        set.start();
    }


    public void translateBtn(final View v) {

        //只是假象,点击终点会没有作用
//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.translate);
//        //  animation.start();
//        Toast.makeText(this, "animation", Toast.LENGTH_SHORT).show();
//        findViewById(R.id.btn).startAnimation(animation);

        //属性动画  Alpha  Translation Rotation
//        position += 100;
//        v.setTranslationX(position);
//        //0 - 1
//        v.setAlpha((float) Math.random());

        //这种情况下设置其他的动画,会覆盖掉
        // ObjectAnimator extends ValueAnimator

        //方法一:  没有这个属性的时候,就是ValueAnimator,等同方法2
//        ObjectAnimator oa = ObjectAnimator.ofFloat(v, "hhhhh", 0f, 300f);
//        oa.setDuration(500);
//        oa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                //不断地调用此方法
//                // animation.getAnimatedFraction();// 动画执行百分比,动画执行到了多少
//
//                float vaule = (float) animation.getAnimatedValue();//得到 Duration时间段内, values中的某一个中间值
//                v.setAlpha(0.5f + vaule / 600);
//                v.setScaleX(0.5f + vaule / 600);
//                v.setScaleY(0.5f + vaule / 600);
//            }
//        });
//        oa.start();

        //方法er:
//        ValueAnimator va = ValueAnimator.ofFloat(0f, 300f);
//        va.setDuration(500);
//        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                //不断地调用此方法
//                // animation.getAnimatedFraction();// 动画执行百分比,动画执行到了多少
//
//                float vaule = (float) animation.getAnimatedValue();//得到 Duration时间段内, values中的某一个中间值
//                v.setAlpha(0.5f + vaule / 600);
//                v.setScaleX(0.5f + , holer1, holer2, holer3);
//        op.setDuration(500);
//        op.start();vaule / 600);
//                v.setScaleY(0.5f + vaule / 600);
//            }
//        });
//        va.start();

        //方法3   values 关键帧
//        PropertyValuesHolder holer1 = PropertyValuesHolder.ofFloat("alpha", 1f, 0.3f, 1f);
//        PropertyValuesHolder holer2 = PropertyValuesHolder.ofFloat("scaleX", 0f, 0.3f, 1f);
//        PropertyValuesHolder holer3 = PropertyValuesHolder.ofFloat("scaleY", 0f, 0.3f, 1f);
//
//        ObjectAnimator op = ObjectAnimator.ofPropertyValuesHolder(v

        //方法4   动画集合
//        ObjectAnimator oan1 = ObjectAnimator.ofFloat(v, "alpha", 1f, 0.3f, 1f);
//        ObjectAnimator oan2 = ObjectAnimator.ofFloat(v, "scaleX", 0f, 0.3f, 1f);
//        ObjectAnimator oan3 = ObjectAnimator.ofFloat(v, "scaleY", 0f, 0.3f, 1f);
//
//        AnimatorSet anmSet = new AnimatorSet();
//        //anmSet.play(oan1); 执行单个动画
//        //anmSet.playSequentially(); 依次执行动画
//        anmSet.playTogether(oan1, oan2, oan3);//同时执行三个动画
//
//        anmSet.start();

        ValueAnimator vm = new ValueAnimator();
        vm.setObjectValues(new PointF(0, 0));
        vm.setDuration(4000);
        //估值期
        vm.setEvaluator(new TypeEvaluator<PointF>() {

            //定义计算规则     百分比    起点   终点
            @Override
            public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
                //拿到每一个时间点的左边  .Point里面是int值,PointF而是float

                PointF pointF = new PointF();
                pointF.x = 150f * (fraction * 4);//初始速度 * 时间执行的百分比
                pointF.y = 0.5f * 9.8f * (fraction * 4) * (fraction * 4);  //  1/2 * g * t * t

                return pointF;
            }
        });

        vm.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                //得到时间点的坐标
                PointF poinf = (PointF) animation.getAnimatedValue();

                v.setX(poinf.x);
                v.setY(poinf.y);
            }
        });

        vm.start();

//        oa.addListener(new Animator.AnimatorListener() {  });
    }

    public void translateBtn2(final View v) {
        ObjectAnimator oa = ObjectAnimator.ofFloat(v, "translationX", 0f, 300f);
        oa.setDuration(500);
        oa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //不断地调用此方法
                // animation.getAnimatedFraction();// 动画执行百分比,动画执行到了多少
                float vaule = (float) animation.getAnimatedValue();//得到 Duration时间段内, values中的某一个中间值
                v.setAlpha(0.5f + vaule / 600);
                v.setScaleX(0.5f + vaule / 600);
                v.setScaleY(0.5f + vaule / 600);
                v.setRotation(60 * vaule / 600);
            }
        });
        oa.start();
    }

    public void translateBtn3(final View v) {
        ObjectAnimator oa = ObjectAnimator.ofFloat(v, "translationX", 0f, 300f);
        oa.setDuration(1000);
        //设置加速器
        //oa.setInterpolator(new AccelerateInterpolator(6));
        //oa.setInterpolator(new AccelerateDecelerateInterpolator());//先加速后减速
        //会反方向回弹一段距离
        //oa.setInterpolator(new AnticipateInterpolator(8));
        //oa.setInterpolator(new AnticipateOvershootInterpolator(8));//两边都会回弹一段距离
        //同向缓存回弹
        //oa.setInterpolator(new OvershootInterpolator( ));
        //运动三次再回来
        //oa.setInterpolator(new CycleInterpolator(5));
        //弹跳
        oa.setInterpolator(new BounceInterpolator());

        oa.start();
    }
}
