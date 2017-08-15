package com.gdj.myview.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gdj.myview.R;
import com.gdj.myview.ui.fragment.MYFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * CoordinatorLayout的用法2
 *   " rv的adapter没有填充数据，所以会有问题" +//
 * "而且对主题有一定要求 \n" +//
 * 作者：${LoveDjForever} on 2017/6/30 10:33
 *  * 邮箱： @qq.com
 */

public class CoordinatorActivity2 extends AppCompatActivity {

    @BindView(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;

    @BindView(R.id.toolbar_start)
    Toolbar mToolbar;

    @BindView(R.id.layout_start_title_root)
    ViewGroup mTitleContainerLine;
    @BindView(R.id.tv_start_toolbar_title)
    TextView mTvTitleToolbar;

    @BindView(R.id.rv_start_activity)
    RecyclerView recyclerView;

    private List<Pair<String, Fragment>> items;
    private NetworkChangeReceiver networkChangeReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator2);

        ButterKnife.bind(this);

        mAppBarLayout.addOnOffsetChangedListener(offsetChangedListener);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver, intentFilter);

        myAdapter myAdapter=new myAdapter();
        recyclerView.setAdapter(myAdapter);
    }

    /**
     * AppBarLayout的offset监听。
     */
    private AppBarLayout.OnOffsetChangedListener offsetChangedListener = new AppBarLayout.OnOffsetChangedListener() {
        @Override
        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            int maxScroll = appBarLayout.getTotalScrollRange();
            float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;
            ViewCompat.setAlpha(mTitleContainerLine, 1 - percentage);
            ViewCompat.setAlpha(mTvTitleToolbar, percentage);
            mToolbar.getBackground().mutate().setAlpha((int) (255 * percentage));

//        if (percentage >= 1 || percentage <= 0) {
//            int distance = getResources().getDimensionPixelSize(android.support.design.);
//            mContentRoot.animate().translationY(distance * percentage).start();
//        }
        }
    };

    class NetworkChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                Log.w("newWork", "netWork is available");
            } else {
                Log.w("newWork", "netWork is unavailable");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
    }

    public class myAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(CoordinatorActivity2.this).inflate(R.layout.item_main_list, null);
            return new Myholder(itemView);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            updateViews((Myholder) holder, position);
        }


        @Override
        public int getItemCount() {
            return 18;
        }

        private void updateViews(Myholder holder, int position) {
            holder.des.setText("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww \n");

        }

        public class Myholder extends RecyclerView.ViewHolder {
            public TextView des;

            public Myholder(View itemView) {
                super(itemView);
                des = (TextView) itemView.findViewById(R.id.des);
            }
        }

    }

}
