package com.gdj.videoselect.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.gdj.videoselect.R;

/**
 * 第一个页面,显示选择条件和选择结果
 */

public class SelectEntranceActivity extends AppCompatActivity {

    Button btn_entrance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_select_entrance);

        initView();
    }

    private void initView() {

    }

    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_entrance) {



        }
    }

}
