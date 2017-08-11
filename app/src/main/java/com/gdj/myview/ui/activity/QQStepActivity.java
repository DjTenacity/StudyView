package com.gdj.myview.ui.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.PopupMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gdj.myview.R;
import com.gdj.myview.view.BrokenView;
import com.gdj.myview.view.PregressView;
import com.gdj.myview.view.QQStepView;
import com.gdj.myview.view.SingleLineFlowLayout;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 防QQ步数
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

    BrokenView menu_chart;

    private String[] mItems = {"1311", "223222", "1131", "222322", "1131", "222232",
            "1411", "1131", "222322", "1131", "222232", "22222", "11133", "22222"};
    private HashMap<Double, Double> map   = new HashMap<>();;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        ButterKnife.bind(this);
        menu_chart= (BrokenView) findViewById(R.id.menu_chart);

     //   mSingleLineFlowLayout = (SingleLineFlowLayout) findViewById(R.id.single_line_layout);

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
        map.put(0.0,0.0);
        map.put(1.0,3.0);
        map.put(2.0,13.0);
        map.put(3.0,23.0);
        map.put(4.0,33.0);
        map.put(5.0,14.0);
        map.put(6.0,46.0);
        map.put(7.0,36.0);
        map.put(8.0,33.0);

//       menu_chart.setView(map,50,10,20,50,"s","b" );
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

    void showListPop(View view){
        ArrayAdapter<String> adapter =new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mItems);
        final ListPopupWindow listPopupWindow=new ListPopupWindow(this);
        listPopupWindow.setAdapter(adapter);
        listPopupWindow.setAnchorView(view);
        listPopupWindow.setHeight(500);
        listPopupWindow.setWidth(300);


        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listPopupWindow.dismiss();
                Toast.makeText(QQStepActivity.this, "ListPopupWindow"+position, Toast.LENGTH_SHORT).show();

            }
        });
        listPopupWindow.show();
    }

    public void showPopupMenu(View view){
        PopupMenu popup=new PopupMenu(this,view);
        popup.getMenuInflater().inflate(R.menu.menu_web,popup.getMenu());
        popup.setGravity(Gravity.CENTER);
        popup.show();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
    }

}
