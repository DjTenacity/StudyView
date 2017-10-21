package com.gdj.myview.view.searchview;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

public class Controller1 extends BaseController {
    private String mColor = "#4DAF50";
    private int cx, cy, cr;
    private RectF mRectF;
    private int j = 15;

    public Controller1() {
        mRectF = new RectF();
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawColor(Color.parseColor(mColor));
        Log.e("Animati","startViewAn44444imation"+mState);

        switch (mState) {

            case STATE_ANIM_NONE:

                drawNormalView(paint, canvas);
                break;
            case STATE_ANIM_START:
                drawStartAnimView(paint, canvas);
                break;
            case STATE_ANIM_STOP:
           	drawNormalView(paint, canvas);
                drawStopAnimView(paint, canvas);
                break;
        }
    }

    private void drawStopAnimView(Paint paint, Canvas canvas) {
    }

    private void drawStartAnimView(Paint paint, Canvas canvas) {
        canvas.save();
        //0~1
        if (mpro <= 0.5f) {
//			canvas.drawArc(
            //			r,
            //			startAngle, //起始角度，相对X轴正方向
            //			sweepAngle, //画多少角度的弧度
            //			useCenter, //boolean, false：只有一个纯弧线；true：闭合的边
            //			paint)；
//			canvas.drawArc(r, 0, 90, true, paint);//顺时针旋转90度
            /**
             * -360 ~ 0 需要变换的范围
             * 	 0  ~ 0.5 mpro实际的变化范围
             * 转换公式：360*(mpro*2-1),
             */
            //绘制圆和把手
            canvas.drawArc(
                    mRectF,
                    45,
                    360 * (mpro * 2 - 1),
                    false,
                    paint);
            canvas.drawLine(
                    mRectF.right - j,
                    mRectF.bottom - j,
                    mRectF.right + cr - j,
                    mRectF.bottom + cr - j,
                    paint);
        } else {
            /**
             *   0    ~ 1 需要变换的范围
             * 	 0.5  ~ 1 mpro实际的变化范围
             * 转换公式：(mpro*2-1),
             */
            //绘制把手
            canvas.drawLine(
                    mRectF.right - j + cr * (mpro * 2 - 1),
                    mRectF.bottom - j + cr * (mpro * 2 - 1),
                    mRectF.right - j + cr,
                    mRectF.bottom + cr - j,
                    paint);
        }
        //绘制下面的横线
        canvas.drawLine(
                (mRectF.right - j + cr) * (1 - mpro * 0.8f),
                mRectF.bottom + cr - j,
                mRectF.right - j + cr,
                mRectF.bottom + cr - j,
                paint);
        canvas.restore();

        mRectF.left = cx - cr + mpro * 250;
        mRectF.right = cx + cr + mpro * 250;
        mRectF.top = cy - cr;
        mRectF.bottom = cy + cr;

    }

    private void drawNormalView(Paint paint, Canvas canvas) {


        cr = getWidth() / 20;
        cx = getWidth() / 2;
        cy = getHeight() / 2;

        mRectF.left = cx - cr;
        mRectF.right = cx + cr;
        mRectF.top = cy - cr;
        mRectF.bottom = cy + cr;
//		canvas.drawArc(
//		r,
//		startAngle, //起始角度，相对X轴正方向
//		sweepAngle, //画多少角度的弧度
//		useCenter, //boolean, false：只有一个纯弧线；true：闭合的边
//		paint)；
//		canvas.drawArc(r, 0, 90, true, paint);//顺时针旋转90度

        canvas.save();
        paint.reset();
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);

        canvas.rotate(45, cx, cy);
        canvas.drawLine(cx + cr, cy, cx + cr * 2, cy, paint);
        canvas.drawArc(
                mRectF,
                0,
                360,
                false,
                paint);
        canvas.restore();
    }

    @Override
    public void startAnim() {
        // TODO Auto-generated method stub
        super.startAnim();
        mState = STATE_ANIM_START;

        Log.e("Animati","startViewAnimation");
        startViewAnimation();
    }

    @Override
    public void resetAnim() {
        // TODO Auto-generated method stub
        super.resetAnim();
        mState = STATE_ANIM_STOP;
        startViewAnimation();
    }

}
