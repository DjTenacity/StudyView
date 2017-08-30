package com.gdj.myview.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.gdj.myview.R;

/**
 * Created by Administrator on 2017/7/15.
 */

public class ContentView extends ImageView {

    public ContentView(Context context) {
        super(context);

        //主界面
        setImageResource(R.mipmap.ic_sc2);

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
