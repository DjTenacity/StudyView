package com.gdj.myview.weight;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.gdj.myview.ui.fragment.ParallaxFragment;

import java.util.List;

/**
 * 作者：${LoveDjForever} on 2017/7/17 18:56
 *  * 邮箱： @qq.com
 */

public class parallaxPagerAdapter extends FragmentPagerAdapter {

    List<ParallaxFragment> fragments;

    public parallaxPagerAdapter(FragmentManager fm, List<ParallaxFragment> fragments) {
        super(fm);
        this.fragments=fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
