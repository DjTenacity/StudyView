package com.gdj.myview.ui.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gdj.myview.R;
import com.gdj.myview.entry.DataBean;
import com.gdj.myview.mobileperf.render.DroidCardsView;
import com.gdj.myview.view.AgreeView;
import com.gdj.myview.view.BrokenScrollView;
import com.gdj.myview.view.BrokenYearView;
import com.gdj.myview.view.CircleTextProgressbar;
import com.gdj.myview.view.CircularDataView;
import com.gdj.myview.view.CricleMenuLayout;
import com.gdj.myview.view.PregressView;
import com.gdj.myview.view.QQStepView;
import com.gdj.myview.view.RefundProgressView;
import com.gdj.myview.view.SingleLineFlowLayout;
import com.gdj.myview.view.TranslucentsScrollView;
import com.gdj.myview.view.XiuXiuView;
import com.gdj.myview.view.battery.MyBattery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 仿QQ步数
 * <p>
 * drawable里面的shape 就是利用的canvas
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
    @BindView(R.id.ll)
    LinearLayoutCompat ll;
    @BindView(R.id.scrollow)
    TranslucentsScrollView scrollow;

    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.tv)
    TextView tv;

    @BindView(R.id.text_input)
    TextInputLayout text_input;


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rpv)
    RefundProgressView rpv;
    @BindView(R.id.cdv)
    CircularDataView cdv;
    @BindView(R.id.xiuxiu)
    XiuXiuView xiuxiu;

    @BindView(R.id.menu_chart)
    BrokenScrollView menu_chart;

    @BindView(R.id.menu_chart2)
    BrokenYearView menu_chart2;

    @BindView(R.id.loading_progress)
    CircleTextProgressbar loading_progress;

    @BindView(R.id.myBattery)
    MyBattery myBattery;

    @BindView(R.id.agreeView3)
    AgreeView txtAgreeView;
    @BindView(R.id.agreeView4)
    AgreeView imgAgreeView ;

    private String[] mItems = {"1311", "223222", "1131", "222322", "1131", "222232",
            "1411", "1131", "222322", "1131", "222232", "22222", "11133", "22222"};
    private HashMap<Double, Double> map = new HashMap<>();
    List<DataBean> dataBeanList = new ArrayList<>();
    private CricleMenuLayout mCircleMenuLayout;//自定义圆盘菜单
    private String[] mItemTexts = new String[]{"放大镜 ", "尺子", "分贝测试仪", "手电筒",
            "计算器", "SOS"};//圆盘菜单显示文字
    private int[] mItemImgs = new int[]{ R.drawable.avft_active, R.drawable.box_stack_active,
            R.drawable.bubble_frame_active,  R.drawable.bubbles_active,
            R.drawable.bullseye_active, R.drawable.circle_filled_active, R.drawable.circle_outline_active};//圆盘菜单显示图片


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        ButterKnife.bind(this);
        // menu_chart = (BrokenView) findViewById(R.id.menu_chart);

        //   mSingleLineFlowLayout = (SingleLineFlowLayout) findViewById(R.id.single_line_layout);

        setSupportActionBar(toolbar);
        scrollow.setTranslucentsListener(new TranslucentsScrollView.TranslucentsListener() {
            @Override
            public void TranslucentsListener(float alpht) {
                toolbar.setAlpha(alpht);
            }
        });
        //学习调色板
        studyPalette();
        studyTextInputLayout();
        //初始化圆盘控件
        mCircleMenuLayout = (CricleMenuLayout) findViewById(R.id.id_menulayout);
        //初始化圆盘控件菜单
        mCircleMenuLayout.setMenuItemIconsAndTexts(mItemImgs, mItemTexts);
        //点击事件
        mCircleMenuLayout.setOnMenuItemClickListener(new CricleMenuLayout.OnMenuItemClickListener() {
            @Override
            public void itemClick(View view, int pos) {
                Toast.makeText(QQStepActivity.this,mItemTexts[pos],Toast.LENGTH_SHORT).show();
            }

            @Override
            public void itemCenterClick(View view) {

            }
        });

        loading_progress.setMaxProgress(10000);
        loading_progress.setProgress(300);
        loading_progress.setTimeMillis(5000);

     //   myBattery.animalStart(3000);
        myBattery.setBatteryValue(84);

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
        map.put(0.0, 0.5);
        map.put(1.0, 3.0);
        map.put(3.0, 13.0);
        map.put(5.0, 23.0);
        map.put(8.0, 33.0);

        map.put(9.0, 14.0);
        map.put(11.0, 33.0);
        map.put(15.0, 23.0);
        map.put(18.0, 33.0);
        map.put(19.0, 14.0);

        map.put(21.0, 33.0);
        map.put(25.0, 23.0);
        map.put(28.0, 33.0);
        map.put(29.0, 14.0);
        map.put(31.0, 33.0);

        map.put(32.0, 33.0);
        map.put(35.0, 23.0);
        map.put(38.0, 33.0);
        map.put(39.0, 14.0);
        map.put(41.0, 33.0);

        dataBeanList.add(new DataBean("2017-01", 5));
        dataBeanList.add(new DataBean("2017-03", 15));
        dataBeanList.add(new DataBean("2017-09", 30));
        dataBeanList.add(new DataBean("2017-10", 40));
        dataBeanList.add(new DataBean("2017-12", 45));


        cdv.setTopStr("2017年", true);
        cdv.setCenterStr("09月", true);
        cdv.setBottomStr("21日", true);

        xiuxiu.start();
        Log.w("menu_chart", menu_chart.getId() + "menu_chart");
        menu_chart.setView(map, 50, 10, 20, 50, "s", "cm");
        //10是每个单位
        menu_chart2.setView(dataBeanList, 50, 10, 20, 50, "cm");
        DroidCardsView deddd = new DroidCardsView(this);
        ll.addView(deddd);


        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0f, 1f);
        valueAnimator.setDuration(3000);
        valueAnimator.setInterpolator(new DecelerateInterpolator());//插值器，先快后慢
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentStep = (float) animation.getAnimatedValue();//提供了float对象，在下面抢转
                rpv.setCurrentStep(currentStep);
            }
        });
        valueAnimator.start();

        txtAgreeView.setClickListener(new AgreeView.AgreeViewClickListener() {
            @Override
            public void onAgreeClick(View view) {
            }
        });
        imgAgreeView.setClickListener(new AgreeView.AgreeViewClickListener() {
            @Override
            public void onAgreeClick(View view) {
            }
        });
    }

    private void studyTextInputLayout() {

        //开始计数
        //text_input.setCounterEnabled(true);
        // text_input.setCounterMaxLength(6);//最大输入限制
        text_input.getEditText().addTextChangedListener(new MinLengthTextChanged());
    }

    class MinLengthTextChanged implements TextWatcher {
        public MinLengthTextChanged() {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //文字变化前回调
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //改变时
        }

        @Override
        public void afterTextChanged(Editable s) {
            //改变后
            if (s.toString().length() <= 6) {

            } else {
                text_input.setCounterEnabled(true);
                text_input.setError("呵呵菜鸟最多6位");
            }
        }
    }

    private void studyPalette() {//iv
        BitmapDrawable drawable = (BitmapDrawable) iv.getDrawable();
        Bitmap bit = drawable.getBitmap();

        //通过palette类分析bitmap出来里面的一些色彩信息
        Palette.from(bit).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                //1.获取活力颜色值
                Palette.Swatch a = palette.getVibrantSwatch();
                //2.获取亮活力颜色值
                Palette.Swatch b = palette.getLightVibrantSwatch();

                //暗、柔和的颜色
                int darkMutedColor = palette.getDarkMutedColor(Color.BLUE);//如果分析不出来，则返回默认颜色
                //暗、柔和
                int lightMutedColor = palette.getLightMutedColor(Color.BLUE);
                //暗、鲜艳
                int darkVibrantColor = palette.getDarkVibrantColor(Color.BLUE);
                //亮、鲜艳
                int lightVibrantColor = palette.getLightVibrantColor(Color.BLUE);
                //柔和
                int mutedColor = palette.getMutedColor(Color.BLUE);
                //柔和
                int vibrantColor = palette.getVibrantColor(Color.BLUE);
                //获取某种特性颜色的样品
//				Swatch lightVibrantSwatch = palette.getLightVibrantSwatch();
                Palette.Swatch lightVibrantSwatch = palette.getVibrantSwatch();
                //谷歌推荐的：图片的整体的颜色rgb的混合值---主色调
                int rgb = lightVibrantSwatch.getRgb();
                //谷歌推荐：图片中间的文字颜色
                int bodyTextColor = lightVibrantSwatch.getBodyTextColor();
                //谷歌推荐：作为标题的颜色（有一定的和图片的对比度的颜色值）
                int titleTextColor = lightVibrantSwatch.getTitleTextColor();
                //颜色向量
                float[] hsl = lightVibrantSwatch.getHsl();
                //分析该颜色在图片中所占的像素多少值
                int population = lightVibrantSwatch.getPopulation();

                tv.setBackgroundColor(getTranslucentColor(0.6f, rgb));

                tv.setBackgroundDrawable(changedImageViewShape(a.getRgb(), b.getRgb()));
                tv.setTextColor(a.getTitleTextColor());
            }
        });
        //Palette palette = Palette.generate(bit);


    }

    /**
     * 1101 0111 1000 1011
     * 1111 1111
     * 1000 1011
     */
    protected int getTranslucentColor(float percent, int rgb) {
        // 10101011110001111
        int blue = Color.blue(rgb);
        int green = Color.green(rgb);
        int red = Color.red(rgb);
        int alpha = Color.alpha(rgb);
//		int blue = rgb & 0xff;
//		int green = rgb>>8 & 0xff;
//		int red = rgb>>16 & 0xff;
//		int alpha = rgb>>>24;

        alpha = Math.round(alpha * percent);
        myToast("alpha:" + alpha + ",red:" + red + ",green:" + green);
        return Color.argb(alpha, red, green, blue);
    }

    /* * 创建Drawable对象
 * @param RGBValues
 * @param two
 * @return
         */
    private Drawable changedImageViewShape(int RGBValues, int two) {
        GradientDrawable shape = new GradientDrawable(GradientDrawable.Orientation.TL_BR
                , new int[]{RGBValues, two});
        shape.setShape(GradientDrawable.RECTANGLE);
        //设置渐变方式
        shape.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        //圆角
        shape.setCornerRadius(8);
        return shape;
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
        showListPop(btn);
    }

    void showListPop(View view) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mItems);
        final ListPopupWindow listPopupWindow = new ListPopupWindow(this);
        listPopupWindow.setAdapter(adapter);
        listPopupWindow.setAnchorView(view);
        listPopupWindow.setHeight(500);
        listPopupWindow.setWidth(300);


        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listPopupWindow.dismiss();
                myToast("ListPopupWindow" + position);
            }
        });
        listPopupWindow.show();
    }

    public void showPopupMenu(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenuInflater().inflate(R.menu.menu_web, popup.getMenu());
        popup.setGravity(Gravity.CENTER);
        popup.show();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                myToast(item.getTitle().toString());
                return false;
            }
        });
    }

    void myToast(String str) {
        Toast result = new Toast(this);
//com.android.internal.R.layout.transient_notification
        LayoutInflater inflate = (LayoutInflater)
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.item_main_type, null);
        TextView tv = (TextView) v.findViewById(R.id.divider);
        tv.setText("自定义吐司" + str);

        result.setView(v);
        result.setDuration(Toast.LENGTH_SHORT);
        result.show();
    }

    public void showMySnackbar(final View view) {
        myToast("\"toast和snackbar\"");

        //Snackbar.LENGTH_INDEFINITE   无穷
        //传入view 是因为 snackbar 要依附于某一个parentView
        Snackbar snackbar = Snackbar.make(view, "只能一个Action,要不要隐藏", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("确定隐藏", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(view);
            }
        });

        snackbar.setCallback(new Snackbar.Callback() {
            @Override
            public void onShown(Snackbar sb) {
                // Stub implementation to make API check happy.
            }

            @Override
            public void onDismissed(Snackbar transientBottomBar, @DismissEvent int event) {
                // Stub implementation to make API check happy.

            }
        });

        snackbar.show();
    }

//    @Override
//    public void TranslucentsListener(float alpht) {
//        toolbar.setAlpha(alpht);
//    }

    boolean reverse = false;

    public void rotate(View view) {
        reverse = !reverse;
        float toDegree = reverse ? -180 : 180;
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "rotation", 0.0f, toDegree)
                .setDuration(500);
        objectAnimator.start();
    }

}