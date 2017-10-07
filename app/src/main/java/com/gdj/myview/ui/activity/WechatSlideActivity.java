package com.gdj.myview.ui.activity;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gdj.myview.R;

import java.io.IOException;

/**
 * Comment:
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/10/6
 */
public class WechatSlideActivity extends AppCompatActivity implements SurfaceHolder.Callback, View.OnClickListener, View.OnTouchListener {

    private RelativeLayout rl2;
    private ImageView mEye;
    private TextView tv_del;
    private LinearLayout bottom;

    SurfaceView sv;
    private Bitmap bitmap;
    private float downY;
    private ValueAnimator animator;
    float time = 500;

    boolean isMoving;
    private float screenHeight;
    private android.hardware.Camera camera;
    private View tv_title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wechat_slide);

        //消息列表界面
        rl2 = (RelativeLayout) findViewById(R.id.rl2);
        mEye = (ImageView) findViewById(R.id.eye);
        tv_del = (TextView) findViewById(R.id.tv_del);
        tv_title = findViewById(R.id.tv_title);
        bottom = (LinearLayout) findViewById(R.id.bottom);

        sv = (SurfaceView) findViewById(R.id.sv);

        //初始化眼睛图片
        //在眼睛图片上不断的绘制圆圈
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_eye);

        animator = ValueAnimator.ofFloat(0, 500);
        animator.setDuration((long) time);

        //初始化surfaceView    用于拍照预览
        SurfaceHolder holder = sv.getHolder();
        //监听用于预览的SurfaceView,初始化完成之后,自动打开相机
        holder.addCallback(this);

        //设置按钮的监听
        //点击之后关闭录制视频界面
        tv_del.setOnClickListener(this);
        rl2.setOnTouchListener(this);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenHeight = displayMetrics.heightPixels;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (isMoving) {
            return true;
        }

        float dy;
        //获取屏幕上的绝对坐标
        int[] location = new int[2];
        mEye.getLocationOnScreen(location);

        //滑动距离的限制
        int bound = location[1] + mEye.getHeight();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //获取手指按下的y坐标
                downY = event.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
                //获取滑动的距离
                dy = event.getRawY() - downY;
                //如果移动的距离没有发生改变

                if (dy <= 0) {
                    break;
                }
                //给消息列表布局来一个动画
                rl2.animate().translationY(dy * 0.6f);

                //给眼睛绘制一个效果,在眼睛上层绘制一个圆圈
                // mEye.setImageBitmap(Uti);

                break;
            //松开手指之后判断华东的距离
            case MotionEvent.ACTION_UP:
                dy = event.getRawY() - downY;
                //当滑动的距离超过眼睛的Y坐标+眼睛图片的高度是,打开视频界面
                if (dy > bound) {
                    showCaptureView(event);
                } else {
                    showChatView(event);
                }
                break;
        }
        return true;
    }

    /***打开拍摄*/
    private void showCaptureView(MotionEvent event) {
        isMoving = true;
        //获取聊天界面的坐标---相对坐标
        final float oldY = rl2.getY();
        //绝对坐标
        final int[] location = new int[2];
        rl2.getLocationOnScreen(location);
        //开始动画
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //获取滑动的进度
                float value = Float.parseFloat(animation.getAnimatedValue().toString());
                //聊天界面的坐标要个动画的进度保持一致
                rl2.setY(oldY + value * (screenHeight - location[1]));

                //眼睛的高度也在发生改变
                mEye.setY(value * 200);

                //设置标题栏的空间的透明度
                tv_del.setAlpha(value);
                bottom.setVisibility(View.GONE);
                tv_title.setAlpha(1 - value);
                //可见
                sv.setVisibility(View.VISIBLE);
                mEye.setVisibility(View.VISIBLE);

                if (value >= 1) {
                    tv_del.setEnabled(true);
                }
            }
        });
    }

    /**
     * 打开消息列表
     */
    private void showChatView(MotionEvent event) {
        isMoving = true;
        //消息列表原始的坐标
        final float oldY = rl2.getY();

        //执行动画,监听,消息列表要移动
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //获取滑动的进度
                float value = Float.parseFloat(animation.getAnimatedValue().toString()) / time;

                // float value = (float) animation.getAnimatedValue() / time;
                //聊天界面的坐标要个动画的进度保持一致
                rl2.setY(oldY * (1 - value));
                if (value > 1) {
                    isMoving = false;
                    //滑动结束
                }
            }
        });
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //打开相机
        camera = android.hardware.Camera.open();

        //相机的预览控件
        try {
            camera.setPreviewDisplay(sv.getHolder());
            //开始预览
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

}
