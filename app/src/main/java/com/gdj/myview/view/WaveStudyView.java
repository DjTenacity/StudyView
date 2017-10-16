package com.gdj.myview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Comment:
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/10/16
 */
public class WaveStudyView extends View {
    private int mViewHeight;
    private int mVIeWidth;
    private Paint paint;
    private Path path;
    private Path dst;

    public WaveStudyView(Context context) {
        super(context);
        init();

    }

    public WaveStudyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public WaveStudyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewHeight = h;
        mVIeWidth = w;
    }


    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);

        path = new Path();
        dst = new Path();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mVIeWidth / 2, mViewHeight / 2);
//
//        path.lineTo(0, 200);
//        path.lineTo(200, 200);
//        path.lineTo(200, 0);
//
////    android:hardwareAccelerated="false"关闭硬件加速,否则path的多路径效果可能会出想问题
//        path.addRect(-200, 200, 200, 200, Path.Direction.CW);
//        path.addRect(-300, 300, 300, 300, Path.Direction.CW);
//
//        //forceclose:不管path绘制是否关闭,为true的时候都会自动测量闭合后的长度
//
//        PathMeasure pathMeasure = new PathMeasure(path, false);
//        PathMeasure pathMeasure2 = new PathMeasure(path, true);
//        Log.w("pathMeasure", pathMeasure.getLength() + "1---pathMeasure---2" + pathMeasure2.getLength());
//
//        float length = pathMeasure.getLength();
//        boolean contour = pathMeasure.nextContour();//获取下一个路径,可能没有
//        float length2 = pathMeasure.getLength();
//
        //-------------------------getSegment 截取片段---------------------------------
//        path.addRect(-300, 300, 300, 300, Path.Direction.CW);
//        PathMeasure pathMeasure = new PathMeasure(path, false);
//        float length = pathMeasure.getLength();
//        canvas.drawPath(path, paint);
//
//        paint.setColor(Color.BLUE);
//        dst.lineTo(-300,300);
//        //startWithMoveTo-->false:是否该起始点是否为上一个结束点(是否保持连续性)
//        pathMeasure.getSegment(200, 600, dst, true);
//        canvas.drawPath(dst, paint);


        //-------------------------getPOSTan  ---------------------------------
        path.addCircle(0, 0, 300, Path.Direction.CW);
        PathMeasure pathMeasure = new PathMeasure(path, false);
        float[] pos = new float[2];
        float[] tan = new float[2];//tan=y/x
        pathMeasure.getPosTan(pathMeasure.getLength() / 4, pos, tan);
        Log.i("getPOSTan", "position:x-"+pos[0]+", y-"+pos[1]);
        Log.i("getPOSTan", "tan:x-"+tan[0]+", y-"+tan[1]);

        canvas.drawPath(path, paint);
    }
}
