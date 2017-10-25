package com.gdj.myview.view.searchview;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.animation.LinearInterpolator;

public abstract class BaseController {
    public static final int STATE_ANIM_NONE = 0;
    public static final int STATE_ANIM_START = 1;
    public static final int STATE_ANIM_STOP = 2;
    public static final int DEFAULT_ANIM_TIME = 5000;
    public static final float DEFAULT_ANIM_STARTF = 0;
    public static final float DEFAULT_ANIM_ENDF = 1;
    private SearchView2 mySearchView;
    public int mState = STATE_ANIM_NONE;

    public abstract void draw(Canvas canvas, Paint paint);

    public void startAnim() {

    }

    public void resetAnim() {

    }

    public int getWidth() {
        return mySearchView.getWidth();
    }

    public int getHeight() {
        return mySearchView.getHeight();
    }

    public void setSearchView(SearchView2 mySearchView) {
        this.mySearchView = mySearchView;
    }

    public float mpro = -1;

    public ValueAnimator startViewAnimation() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(800l);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new AnimatorUpdateListener() {


            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.e("Animati","startViewAnimation");

                mpro = (float) animation.getAnimatedValue();
                mySearchView.invalidate();
            }
        });

        valueAnimator.start();
        mpro = 0;
        return valueAnimator;
    }

}
