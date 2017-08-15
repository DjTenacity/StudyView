package com.gdj.myview.view;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.view.ViewPager.PageTransformer;
import android.view.View;
import android.view.ViewGroup;

import com.gdj.myview.R;


public class WelcompagerTransformer implements PageTransformer, OnPageChangeListener {
	private static final float ROT_MOD = -15f;
	private int pageIndex;
	private boolean pageChanged = true;

	/**
	 * 此方法是滑动的时候每一个页面View都会调用该方法
	 * view:当前的页面
	 * position:当前滑动的位置
	 * 视差效果：在View正常滑动的情况下，给当前View或者当前view里面的每一个子view来一个加速度
	 * 而且每一个加速度大小不一样。
	 *
	 */
	@Override
	public void transformPage(View view, float position) {
		ViewGroup v = (ViewGroup) view.findViewById(R.id.rl);
		final MyScrollView mscv = (MyScrollView) v.findViewById(R.id.mscv);
		View bg1 = v.findViewById(R.id.imageView0);
		View bg2 = v.findViewById(R.id.imageView0_2);
		View bg_container = v.findViewById(R.id.bg_container);

		int bg1_green = view.getContext().getResources().getColor(R.color.bg1_green);
		int bg2_blue = view.getContext().getResources().getColor(R.color.bg2_blue);
//			int bg3_green = view.getContext().getResources().getColor(R.color.bg3_green);

		Integer tag = (Integer) view.getTag();
		View parent = (View) view.getParent();
//		if(parent instanceof ViewPager){
//			System.out.println("yes~~~~~~~~~~~tag:"+tag+", position:"+position);
//		}
		//颜色估值器,,,,Math.abs(position)百分比--->-1~0~1
		ArgbEvaluator evaluator = new ArgbEvaluator();
		int color = bg1_green;
		if(tag.intValue()==pageIndex){
			switch (pageIndex) {
				case 0:
					color = (int) evaluator.evaluate(Math.abs(position), bg1_green, bg2_blue);
					break;
				case 1://存在两种情况,上一页下一页
					color = (int) evaluator.evaluate(Math.abs(position), bg2_blue, bg1_green);
					break;
				case 2:
					color = (int) evaluator.evaluate(Math.abs(position), bg1_green, bg2_blue);
					break;
				default:
					break;
			}
			//设置整个viewpager的背景颜色
			parent.setBackgroundColor(color);

			//动画 变色
//		    ObjectAnimator colorAnim = ObjectAnimator.ofInt(this, "backgroundColor", bg1_green, bg2_blue);
////		    ObjectAnimator colorAnim = ObjectAnimator.ofInt(this, "backgroundColor", CYAN, BLUE, RED);
//		    colorAnim.setTarget(parent);
//		    colorAnim.setEvaluator(new ArgbEvaluator());
//		    colorAnim.setRepeatCount(ValueAnimator.INFINITE);
//		    colorAnim.setRepeatMode(ValueAnimator.REVERSE);
//		    colorAnim.setDuration(3000);
//		    colorAnim.start();
		}
		//position也代表着滑动的程度.
		if(position==0){
			System.out.println("position==0");
			//pageChanged作用--解决问题：只有在切换页面的时候才展示平移动画，如果不判断则会只是移动一点点当前页面松开也会执行一次平移动画
			if(pageChanged){
				bg1.setVisibility(View.VISIBLE);
				bg2.setVisibility(View.VISIBLE);

				ObjectAnimator animator_bg1 = ObjectAnimator.ofFloat(bg1, "translationX", 0,-bg1.getWidth());
				animator_bg1.setDuration(400);
				animator_bg1.addUpdateListener(new AnimatorUpdateListener() {

					@Override
					public void onAnimationUpdate(ValueAnimator animation) {
						mscv.smoothScrollTo((int) (mscv.getWidth()*animation.getAnimatedFraction()), 0);
					}
				});
				animator_bg1.start();

				ObjectAnimator animator_bg2 = ObjectAnimator.ofFloat(bg2, "translationX", bg2.getWidth(),0);
				animator_bg2.setDuration(400);
				animator_bg2.start();
				pageChanged= false;
			}
		}else
		if(position==-1||position==1){//所有效果复原
			bg2.setTranslationX(0);
			bg1.setTranslationX(0);
			mscv.smoothScrollTo(0, 0);
		}else
		if(position<1&&position>-1){

			final float width = bg1.getWidth();
			final float height = bg1.getHeight();
			final float rotation = ROT_MOD * position * -1.25f;

//			bg1.setPivotX(width * 0.5f);
//			bg1.setPivotY(height);
//			bg1.setRotation(rotation);
//			bg2.setPivotX(width * 0.5f);
//			bg2.setPivotY(height);
//			bg2.setRotation(rotation);
			//这里不去分别处理bg1、bg2，而是用包裹的父容器执行动画，目的是避免难以处理两个bg的属性位置恢复。
			bg_container.setPivotX(width * 0.5f);
			bg_container.setPivotY(height);
			bg_container.setRotation(rotation);
		}
	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
							   int positionOffsetPixels) {
		System.out.println("position:"+position+", positionOffset:"+positionOffset);
	}

	@Override
	public void onPageSelected(int position) {
		pageIndex = position;
		pageChanged = true;
		System.out.println("position_selected:"+position);
	}

	@Override
	public void onPageScrollStateChanged(int state) {
	}

}
