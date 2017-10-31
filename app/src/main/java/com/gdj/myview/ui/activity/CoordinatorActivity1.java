package com.gdj.myview.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.View;

import com.gdj.myview.R;
import com.gdj.myview.ui.fragment.CanvasStudyFragment;
import com.gdj.myview.ui.fragment.FreshDownloadFragment;
import com.gdj.myview.ui.fragment.MYFragment;
import com.gdj.myview.ui.fragment.PathStudyFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.support.design.widget.Snackbar.LENGTH_SHORT;

/**
 * 作者：${LoveDjForever} on 2017/6/30 10:33
 *  * 邮箱： @qq.com
 */

public class CoordinatorActivity1 extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tab)
    TabLayout tab;

    private List<Pair<String, Fragment>> items;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator1);
        initToolBar(toolbar, false, "");

        items = new ArrayList<>();
        items.add(new Pair<String, Fragment>("学习控件", new MYFragment()));
        items.add(new Pair<String, Fragment>("Path之波浪", new PathStudyFragment()));
        items.add(new Pair<String, Fragment>("Canvas学习", new CanvasStudyFragment()));
        items.add(new Pair<String, Fragment>("FreshDownloadView", new FreshDownloadFragment()));




        viewPager.setAdapter(new MainAdapter(getSupportFragmentManager()));
        tab.setupWithViewPager(viewPager);
        for (int i = 0; i < tab.getTabCount(); i++) {
            TabLayout.Tab tab2 = tab.getTabAt(i);
            //tab.setText(Html.toHtml(text))
            // View view = View.inflate(this, R.layout.bottom_navigation, null);
            //  TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
            //   tv_name.setText(title[i]);
            // tab2.setCustomView(view);
            tab2.setIcon(getResources().getDrawable(R.drawable.drawable_gradient));
        }
    }

    @OnClick(R.id.fab)
    public void fab(View view) {
        //FloatingActionButton 与 Snackbar 会出现覆盖问题,已经在CoordinatorLayout中不会出现,是在snackbar里面解决的
        //view是锚点,为了追溯父容器
        Snackbar.make(view, "要去看看我的GitHub么", LENGTH_SHORT).setAction("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                WebActivity.runActivity(CoordinatorActivity1.this, "我的Github,欢迎star", "https://github.com/DjTenacity");
            }
        }).show();
    }

    @Override
    protected boolean translucentStatusBar() {
        return true;
    }

    private class MainAdapter extends FragmentPagerAdapter {

        MainAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return items.get(position).second;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return items.get(position).first;
        }
    }
}
