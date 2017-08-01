package com.gdj.myview.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.gdj.myview.R;

import gdj.com.djknife.DjKnife;
import gdj.com.djknife.annotation.ViewInjector;

/**
 *
 */

public class DjKnifeActivity extends AppCompatActivity {

    @ViewInjector(R.id.tv_author)
    TextView tv_author;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        DjKnife.inject(this);
    }


}
