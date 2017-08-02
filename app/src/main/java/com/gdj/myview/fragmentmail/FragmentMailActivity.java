package com.gdj.myview.fragmentmail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.gdj.myview.R;

/**
 * Comment:
 *
 * @author : ${user} DJ鼎尔东/ 1757286697@qq.com
 * @version : 1.0
 * @date : 2017/8/2 18:33
 */
public class FragmentMailActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_mail);
    }

    public void setFunctionsForFragment(String fragmentTag){
        FragmentManager manager=getSupportFragmentManager();

    }



}
