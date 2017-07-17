package com.gdj.myview.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gdj.myview.view.ParallaxLayoutInflater;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */

public class ParallaxFragment extends Fragment {

    //此fragment上所有的需要实现视差动画的视图
    private List<View> parallaxViews=new ArrayList<View>();

    public static ParallaxFragment getInstance(int i, int ids) {
        ParallaxFragment fragment = new ParallaxFragment();

        Bundle bundle = new Bundle();
        //页面索引
        bundle.putInt("index", i);
        //fragment中需要加载的布局文件id
        bundle.putInt("layoutId", ids);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Bundle args = getArguments();
        //页面索引
        int index = args.getInt("index");
        //fragment中需要加载的布局文件id
        int layoutId = args.getInt("layoutId");

        //布局加载器将布局加载进来
        //解析创建布局上所有的视图
        //我们自己搞定创建视图的过程
        //获取视图相关的自定义属性的值
        ParallaxLayoutInflater inflater1=new ParallaxLayoutInflater(inflater,getActivity(),this);

        return inflater1.inflate(layoutId, null);

    }

    public List<View> getParallaxViews() {
        return parallaxViews;
    }

    public void setParallaxViews(List<View> parallaxViews) {
        this.parallaxViews = parallaxViews;
    }
}
