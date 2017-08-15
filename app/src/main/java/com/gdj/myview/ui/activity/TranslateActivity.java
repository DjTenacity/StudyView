package com.gdj.myview.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.gdj.myview.R;
import com.gdj.myview.ui.fragment.TranslateFragment;
import com.gdj.myview.view.WelcompagerTransformer;

/**
 * Comment:  平行空间
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/8/14
 */
public class TranslateActivity extends FragmentActivity {

    private ViewPager vp;
    private int[] layouts = {
            R.layout.welcome11,
            R.layout.welcome2,
            R.layout.welcome3
//			R.layout.welcome1,
//			R.layout.welcome2,
//			R.layout.welcome3
    };
    private WelcompagerTransformer transformer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        vp = (ViewPager) findViewById(R.id.vp);

        WelcomePagerAdapter adapter = new WelcomePagerAdapter(getSupportFragmentManager());
        System.out.println("offset:" + vp.getOffscreenPageLimit());
        vp.setOffscreenPageLimit(3);
        vp.setAdapter(adapter);

        transformer = new WelcompagerTransformer();
        vp.setPageTransformer(true, transformer);

        vp.setOnPageChangeListener(transformer);
    }

    class WelcomePagerAdapter extends FragmentPagerAdapter {

        public WelcomePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment f = new TranslateFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("layoutId", layouts[position]);
            bundle.putInt("pageIndex", position);
            f.setArguments(bundle);
            return f;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return 3;
        }

    }

}
