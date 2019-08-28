package com.gdj.myview.view;


import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.gdj.myview.R;


public class LoadingView extends FrameLayout {

    //画笔
    private Paint paint;
    //路径
    private Path path;
    //要输入的文本
    private String printText = "正在加载";
    //文本宽
    private float textWidth;
    //文本高
    private float textHeight;
    //测量文字宽高的时候使用的矩形
    private Rect rect;
    //最大弹力系数
    private float elastic = 1.5f;

    //最大弹力
    private float maxElasticFactor;

    //当前弹力
    private float nowElasticFactor;

    //用于记录当前图片使用数组中的哪张
    int indexImgFlag = 0;




    //下沉图片
    int allImgDown [] = {R.mipmap.ic_add_nor, R.mipmap.ic_add_sel,R.mipmap.ic_add_nor,R.mipmap.ic_add_sel};

    //动画效果一次下沉或上弹的时间 animationDuration*2=一次完整动画时间
    int animationDuration = 1000;

    //弹起来的图片
    ImageView iv;

    //图片下沉高度(即从最高点到最低点的距离)
    int downHeight = 2;
    private Animation translateDown;
    private Animation translateUp;

    private ObjectAnimator rotation;


    public LoadingView(Context context) {
        super(context);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        canvas.drawTextOnPath(printText, path, 0, 0, paint);
    }


    //用于播放文字下沉和上浮动画传入的数值必须是 图片下沉和上升的一次时间
    public void initAnimation(int duration){
        ValueAnimator animator =  ValueAnimator.ofFloat(maxElasticFactor/4, (float) (maxElasticFactor / 1.5),0);
        animator.setDuration(duration/2);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                calc();
                nowElasticFactor= (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
    }


    public void calc(){

        //重置路径
        path.reset();
        //由于画文字是由基准线开始
        path.moveTo(iv.getX()-textWidth/2+iv.getWidth()/2, textHeight+iv.getHeight()+downHeight*iv.getHeight());
        //画二次贝塞尔曲线
        path.rQuadTo(textWidth / 2, nowElasticFactor, textWidth, 0);

    }


    //初始化弹跳动画
    public void  MyAnmation(){
        //下沉效果动画
        translateDown = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,0,
                Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,downHeight);
        translateDown.setDuration(animationDuration);
        //设置一个插值器 动画将会播放越来越快 模拟重力
        translateDown.setInterpolator(new AccelerateInterpolator());


        //上弹动画
        translateUp = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,0,
                Animation.RELATIVE_TO_SELF,downHeight,Animation.RELATIVE_TO_SELF,0
        );

        translateUp.setDuration(animationDuration);
        ////设置一个插值器 动画将会播放越来越慢 模拟反重力
        translateUp.setInterpolator(new DecelerateInterpolator());


        //当下沉动画完成时播放启动上弹
        translateDown.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                iv.setImageResource(allImgDown[indexImgFlag]);
                rotation = ObjectAnimator.ofFloat(iv, "rotation", 180f, 360f);
                rotation.setDuration(1000);
                rotation.start();
            }


            @Override
            public void onAnimationEnd(Animation animation) {

                iv.startAnimation(translateUp);
                initAnimation(animationDuration);

            }


            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        //当上移动画完成时 播放下移动画
        translateUp.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                indexImgFlag = 1+indexImgFlag>=allImgDown.length?0:1+indexImgFlag;
                iv.setImageResource(allImgDown[indexImgFlag]);
                rotation = ObjectAnimator.ofFloat(iv, "rotation", 0.0f, 180f);
                rotation.setDuration(1000);

                rotation.start();
            }


            @Override
            public void onAnimationEnd(Animation animation) {
                //递归
                iv.startAnimation(translateDown);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });






    }

    boolean flagMeure;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        init();
    }

    private void init() {

        //初始化弹跳图片 控件
        iv = new ImageView(getContext());

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        iv.setLayoutParams(params);
        iv.setImageResource(allImgDown[0]);


        this.addView(iv);

        //画笔的初始化
        paint = new Paint();
        paint.setStrokeWidth(1);
        paint.setColor(Color.CYAN);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(50);
        paint.setAntiAlias(true);

        //矩形
        rect = new Rect();
        //将文字画入矩形目的是为了测量高度
        paint.getTextBounds(printText, 0, printText.length(), rect);

        //文本宽度
        textWidth = paint.measureText(printText);

        //获得文字高度
        textHeight = rect.bottom - rect.top;

        //初始化路径
        path = new Path();

        iv.setX(getWidth()/2);

        iv.measure(0,0);

        iv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                if (!flagMeure)
                {
                    flagMeure =true;
                    //由于画文字是由基准线开始
                    path.moveTo(iv.getX()-textWidth/2+iv.getWidth()/2, textHeight+iv.getHeight()+downHeight*iv.getHeight());

                    //计算最大弹力
                    maxElasticFactor = (float) (textHeight / elastic);
                    //初始化贝塞尔曲线
                    path.rQuadTo(textWidth / 2, 0, textWidth, 0);

                    //初始化上弹和下沉动画
                    MyAnmation();

                    iv.startAnimation(translateDown);
                }
            }
        });

    }
}
