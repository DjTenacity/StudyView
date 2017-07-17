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

public class ParallaxPagerAdapter extends FragmentPagerAdapter {

    List<ParallaxFragment> fragments;

    public ParallaxPagerAdapter(FragmentManager fm, List<ParallaxFragment> fragments) {
        super(fm);
        this.fragments=fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
