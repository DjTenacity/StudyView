package com.gdj.myview.view.battery;

import android.animation.ObjectAnimator;

/**
 * @author kongdy
 * @date 2016-07-28 23:16
 * @TIME 23:16
 **/

public class ChartAnimator {
    private MyAnimalUpdateListener mListener;

    protected float mPhaseX = 1f;
    protected float mPhaseY = 1f;

    private long startDelay = 400;

    private ObjectAnimator.AnimatorListener animatorListener;

    public ChartAnimator(MyAnimalUpdateListener mListener) {
        this.mListener = mListener;
    }

    /**
     * 设置X轴动画
     * @param duration
     */
    public void animalX(long duration) {
        // 避免动画倒置
        mPhaseX = 0f;
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(this,"phaseX",0f,1f);
        objectAnimator1.setDuration(duration);
        objectAnimator1.setStartDelay(startDelay);
        objectAnimator1.addUpdateListener(mListener);
        if(animatorListener != null)
            objectAnimator1.addListener(animatorListener);
        objectAnimator1.start();
    }

    /**
     * 设置Y轴动画
     * @param duration
     */
    public void animalY(long duration) {
        mPhaseY = 0F;
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(this,"phaseY",0f,1f);
        objectAnimator1.setDuration(duration);
        objectAnimator1.setStartDelay(startDelay);
        objectAnimator1.addUpdateListener(mListener);
        if(animatorListener != null)
            objectAnimator1.addListener(animatorListener);
        objectAnimator1.start();
    }

    /**
     * 设置X,Y轴动画
     * @param duration
     */
    public void animalXY(long duration) {
        mPhaseY = 0F;
        mPhaseX = 0F;
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(this,"phaseX",0f,1f);
        objectAnimator1.setDuration(duration);
        objectAnimator1.setStartDelay(startDelay);
        objectAnimator1.addUpdateListener(mListener);

        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(this,"phaseY",0f,1f);
        objectAnimator2.setDuration(duration);
        objectAnimator2.setStartDelay(startDelay);
        objectAnimator2.addUpdateListener(mListener);
        if(animatorListener != null) {
            objectAnimator1.addListener(animatorListener);
            objectAnimator2.addListener(animatorListener);
        }
        objectAnimator1.start();
        objectAnimator2.start();
    }

    public void pause() {
        mListener.pause();
    }

    public void resume() {
        mListener.resume();
    }

    public float getPhaseX() {
        return mPhaseX;
    }

    public void setPhaseX(float phaseX) {
        this.mPhaseX = phaseX;
    }

    public float getPhaseY() {
        return mPhaseY;
    }

    public void setPhaseY(float phaseY) {
        this.mPhaseY = phaseY;
    }

    public void setAnimatorListener(ObjectAnimator.AnimatorListener animatorListener) {
        this.animatorListener = animatorListener;
    }

    public long getStartDelay() {
        return startDelay;
    }

    public void setStartDelay(long startDelay) {
        this.startDelay = startDelay;
    }
}
