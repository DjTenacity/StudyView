package com.gdj.myview.view.menuview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Comment:
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2018/1/17
 */

/*****
 *
 *    mListDataScreenView = (ListDataScreenView) findViewById(R.id.view_data_screen_view);
 mListDataScreenView.setAdapter(new ListScreenMenuAdapter(this));
 */

public class ListDataScreenView extends LinearLayout implements View.OnClickListener {
    private Context mContext;
    // 1.1 创建头部用来存放 Tab
    private LinearLayout mMenuTabView;
    // 1.2 创建 FrameLayout 用来存放 = 阴影（View） + 菜单内容布局(FrameLayout)
    private FrameLayout mMenuMiddleView;
    // 阴影
    private View mShadowView;
    // 创建菜单用来存放菜单内容
    private FrameLayout mMenuContainerView;
    // 阴影的颜色
    private int mShadowColor = 0x88888888;
    // 筛选菜单的 Adapter
    private BaseMenuAdapter mAdapter;
    // 内容菜单的高度
    private int mMenuContainerHeight;
    // 当前打开的位置
    private int mCurrentPosition = -1;
    private long DURATION_TIME = 350;
    // 动画是否在执行
    private boolean mAnimatorExecute;

    public ListDataScreenView(Context context) {
        this(context, null);
    }

    public ListDataScreenView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ListDataScreenView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initLayout();
    }

    /**
     * 1.布局实例化好 （组合控件）
     */
    private void initLayout() {
        //  1. 先创建一个 xml 布局 ，再加载，findViewById
        //  2. 简单的效果用代码去创建  早期IOS 用代码创建布局
        setOrientation(VERTICAL);
        // 1.1 创建头部用来存放 Tab
        mMenuTabView = new LinearLayout(mContext);
        mMenuTabView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(mMenuTabView);


        // 1.2 创建 FrameLayout 用来存放 = 阴影（View） + 菜单内容布局(FrameLayout)
        mMenuMiddleView = new FrameLayout(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0);
        params.weight = 1;
        mMenuMiddleView.setLayoutParams(params);
        addView(mMenuMiddleView);


        // 创建阴影 可以不用设置 LayoutParams 默认就是 MATCH_PARENT ，MATCH_PARENT
        mShadowView = new View(mContext);
        mShadowView.setBackgroundColor(mShadowColor);
        mShadowView.setAlpha(0f);
        mShadowView.setOnClickListener(this);
        mShadowView.setVisibility(GONE);
        mMenuMiddleView.addView(mShadowView);


        // 创建菜单用来存放菜单内容
        mMenuContainerView = new FrameLayout(mContext);
        mMenuContainerView.setBackgroundColor(Color.WHITE);

        mMenuMiddleView.addView(mMenuContainerView);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e("TAG", "onMeasure");
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (mMenuContainerHeight == 0 && height > 0) {
            // 内容的高度应该不是全部  应该是整个 View的 75%
            mMenuContainerHeight = (int) (height * 75f / 100);
            ViewGroup.LayoutParams params = mMenuContainerView.getLayoutParams();
            params.height = mMenuContainerHeight;
            mMenuContainerView.setLayoutParams(params);
            // 进来的时候阴影不显示 ，内容也是不显示的（把它移上去）
            mMenuContainerView.setTranslationY(-mMenuContainerHeight);
        }
    }
    /**具体的观察者类对象**/
    private class AdapterMenuObserver extends  MenuObserver{
        @Override
        public void closeMenu() {
        //如果有注册就会收到通知
            ListDataScreenView.this.closeMenu();
        }
    }
    private  AdapterMenuObserver adapterMenuObserver;


    /**
     * 设置 Adapter
     *
     * @param adapter
     */
    public void setAdapter(BaseMenuAdapter adapter) {


        //观察者,微信的公众号用户
        if(mAdapter!=null && adapterMenuObserver!=null){
            //取消订阅
            mAdapter.unregisterDataSetObserver(adapterMenuObserver);
        }

        this.mAdapter = adapter;
        //注册观察者 具体的观察者实例对象
        adapterMenuObserver =new AdapterMenuObserver();
        //订阅
        mAdapter.registerDataSetObserver(adapterMenuObserver);
        // 获取有多少条
        int count = mAdapter.getCount();
        for (int i = 0; i < count; i++) {
            // 获取菜单的Tab
            View tabView = mAdapter.getTabView(i, mMenuTabView);
            mMenuTabView.addView(tabView);
            LinearLayout.LayoutParams params = (LayoutParams) tabView.getLayoutParams();
            params.weight = 1;
            tabView.setLayoutParams(params);

            // 设置tab点击事件
            setTabClick(tabView, i);

            // 获取菜单的内容
            View menuView = mAdapter.getMenuView(i, mMenuContainerView);
            menuView.setVisibility(GONE);
            mMenuContainerView.addView(menuView);
        }

        // 挤到一堆去了 ，菜单的 Tab 不见了(解决) ，宽度不是等宽，weight要是为1
        // 内容的高度应该不是全部  应该是整个 View的 75%
        // 进来的时候阴影不显示 ，内容也是不显示的（把它移上去）
        // 内容还没有显示出来,打开的时候显示当前位置的菜单，关闭的时候隐藏，阴影点击应该要关闭菜单
        // 动画在执行的情况下就不要在响应动画事件
        // 打开和关闭 变化tab的显示 ， 肯定不能把代码写到 ListDataScreen 里面来
        // 当菜单是打开的状态 不要执行动画只要切换
    }

    /**
     * 设置tab的点击
     *
     * @param tabView
     * @param position
     */
    private void setTabClick(final View tabView, final int position) {
        tabView.setOnClickListener(new OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onClick(View v) {
                if (mCurrentPosition == -1) {
                    // 没打开
                    openMenu(position, tabView);
                } else {
                    if (mCurrentPosition == position) {
                        // 打开了,关闭
                        closeMenu();
                     //   adapterMenuObserver.closeMenu();
                    } else {
                        // 切换一下显示
                        View currentMenu = mMenuContainerView.getChildAt(mCurrentPosition);
                        currentMenu.setVisibility(View.GONE);
                        mAdapter.menuClose(mMenuTabView.getChildAt(mCurrentPosition));
                        mCurrentPosition = position;

                        currentMenu = mMenuContainerView.getChildAt(mCurrentPosition);
                        currentMenu.setVisibility(View.VISIBLE);
                        mAdapter.menuOpen(mMenuTabView.getChildAt(mCurrentPosition));
                    }
                }
            }
        });
    }

    /**
     * 关闭菜单
     */
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    private void closeMenu() {
        if (mAnimatorExecute) {
            return;
        }
        // 关闭动画  位移动画  透明度动画
        ObjectAnimator translationAnimator = ObjectAnimator.ofFloat(mMenuContainerView, "translationY", 0, -mMenuContainerHeight);
        translationAnimator.setDuration(DURATION_TIME);
        translationAnimator.start();

        mShadowView.setVisibility(View.VISIBLE);

        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mShadowView, "alpha", 1f, 0f);
        alphaAnimator.setDuration(DURATION_TIME);
        // 要等关闭动画执行完才能去隐藏当前菜单
        alphaAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                View menuView = mMenuContainerView.getChildAt(mCurrentPosition);
                menuView.setVisibility(View.GONE);
                mCurrentPosition = -1;
                mShadowView.setVisibility(GONE);
                mAnimatorExecute = false;
            }

            @Override
            public void onAnimationStart(Animator animation) {
                mAnimatorExecute = true;
                mAdapter.menuClose(mMenuTabView.getChildAt(mCurrentPosition));
            }
        });
        alphaAnimator.start();
    }

    /**
     * 打开菜单
     *
     * @param position
     * @param tabView
     */
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    private void openMenu(final int position, final View tabView) {

        if (mAnimatorExecute) {
            return;
        }

        mShadowView.setVisibility(View.VISIBLE);
        // 获取当前位置显示当前菜单，菜单是加到了菜单容器
        View menuView = mMenuContainerView.getChildAt(position);
        menuView.setVisibility(View.VISIBLE);

        // 打开开启动画  位移动画  透明度动画
        ObjectAnimator translationAnimator = ObjectAnimator.ofFloat(mMenuContainerView, "translationY", -mMenuContainerHeight, 0);
        translationAnimator.setDuration(DURATION_TIME);
        translationAnimator.start();

        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mShadowView, "alpha", 0f, 1f);
        alphaAnimator.setDuration(DURATION_TIME);

        alphaAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mAnimatorExecute = false;
                mCurrentPosition = position;
            }

            @Override
            public void onAnimationStart(Animator animation) {
                mAnimatorExecute = true;
                // 把当前的 tab 传到外面
                mAdapter.menuOpen(tabView);
            }
        });
        alphaAnimator.start();
    }

    @Override
    public void onClick(View v) {
        closeMenu();
    }
}

