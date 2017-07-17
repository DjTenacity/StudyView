package com.gdj.myview.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;

/**
 *
 */

public class ParallaxFragment extends Fragment {

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

}
