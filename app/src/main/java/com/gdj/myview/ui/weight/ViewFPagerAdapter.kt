package com.hulubao.android.tyc.ui.widget

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import java.util.*

/**
 * Comment:  Fragment+ViewPager 适配器
 *
 * @author : DJ鼎尔东/ 1757286697@qq.com
 * @version : ${user} 1.0
 * @date : 2017/8/24 16:12
 */

class ViewFPagerAdapter : FragmentPagerAdapter {

    private var mFragments: List<Fragment> = LinkedList()
    private var mTitles: List<String> = LinkedList()

    constructor(fm: FragmentManager) : super(fm) {}

    constructor(fm: FragmentManager, titles: List<String>, fragments: List<Fragment>) : super(fm) {
        this.mTitles = titles
        this.mFragments = fragments
    }

    override fun getPageTitle(position: Int): CharSequence {
        return if (mTitles == null || mTitles.size == 0) {
            ""
        } else mTitles[position]
    }

    override fun getCount(): Int {
        return mTitles.size
    }

    override fun getItem(position: Int): Fragment? {
        return if (mFragments == null || mFragments.size == 0) {
            null
        } else mFragments[position]
    }

    fun setData(mTitles: List<String>, mFragments: List<Fragment>) {
        this.mTitles = mTitles
        this.mFragments = mFragments
        notifyDataSetChanged()
    }
}
