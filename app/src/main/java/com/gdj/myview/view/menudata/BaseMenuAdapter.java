package com.gdj.myview.view.menudata;

import android.view.View;
import android.view.ViewGroup;

/**
 * Comment:筛选菜单的 adapter
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2018/1/19
 */
public abstract class BaseMenuAdapter {


    /**
     * 获取总共有多少条
     */
    public abstract int getCount();

    /**
     * 获取当前的tabview
     **/
    public abstract View getTabView(int position, ViewGroup viewGroup);

    /**
     * 获取当前的Menuview
     **/
    public abstract View getMenuView(int position, ViewGroup viewGroup);

    /**
     * 菜单打开
     * @param tabView
     */
    public void menuOpen(View tabView) {

    }

    /**
     * 菜单关闭
     * @param tabView
     */
    public void menuClose(View tabView) {

    }

}
