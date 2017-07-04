
package com.gdj.myview.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;


import com.gdj.myview.R;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;



public abstract class BaseDetailActivity extends BaseActivity {

    protected ActionBar actionBar;
    protected TextView requestState;
    protected TextView requestHeaders;
    protected TextView responseData;
    protected TextView responseHeader;
    protected FrameLayout rootContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
        getDelegate().setContentView(R.layout.activity_base);
        Window window = getWindow();
        requestState = (TextView) window.findViewById(R.id.requestState);
        requestHeaders = (TextView) window.findViewById(R.id.requestHeaders);
        responseData = (TextView) window.findViewById(R.id.responseData);
        responseHeader = (TextView) window.findViewById(R.id.responseHeader);
        rootContent = (FrameLayout) window.findViewById(R.id.content);
        onActivityCreate(savedInstanceState);
    }

    protected abstract void onActivityCreate(Bundle savedInstanceState);

    @Override
    public void setTitle(CharSequence title) {
        if (actionBar != null) actionBar.setTitle(title);
    }

    @Override
    public void setTitle(int titleId) {
        if (actionBar != null) actionBar.setTitle(titleId);
    }

    @Override
    public View findViewById(int id) {
        return rootContent.findViewById(id);
    }

    private void clearContentView() {
        rootContent.removeAllViews();
    }

    @Override
    public void setContentView(int layoutResID) {
        clearContentView();
        getLayoutInflater().inflate(layoutResID, rootContent, true);
    }

    @Override
    public void setContentView(View view) {
        clearContentView();
        rootContent.addView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        clearContentView();
        rootContent.addView(view, params);
    }
}
