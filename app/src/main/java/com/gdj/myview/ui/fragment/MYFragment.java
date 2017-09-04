/*
 * Copyright 2016 jeasonlzy(廖子尧)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gdj.myview.ui.fragment;

import android.content.Intent;

import com.gdj.myview.entry.ItemModel;
import com.gdj.myview.fileup.UploadFileActivity;
import com.gdj.myview.ui.activity.AnimationActivity;
import com.gdj.myview.ui.activity.CVActivity;
import com.gdj.myview.ui.activity.KTLockPatternActivity;
import com.gdj.myview.ui.activity.MainActivity;
import com.gdj.myview.ui.activity.MultiButtonActivity;
import com.gdj.myview.ui.activity.QQStepActivity;
import com.gdj.myview.ui.activity.QQzoneActivity;
import com.gdj.myview.ui.activity.RecyclerViewActivity;
import com.gdj.myview.ui.activity.SVGViewActivity;
import com.gdj.myview.ui.activity.TranslateActivity;
import com.gdj.myview.ui.activity.YahooNewsActivity;

import java.util.List;


public class MYFragment extends MainFragment {

    @Override
    public void fillData(List<ItemModel> items) {

        items.add(new ItemModel("0手机信息与照相机",//
                "sdk版本与手机名字\n" +//
                        "类似于微信，可以切换摄像头\n" +//
                        ""));

        items.add(new ItemModel("平行空间", " "));
//2
        items.add(new ItemModel("2具有3D视差效果的多选按钮。",//
                "https://github.com/gjiazhe/MultiChoicesCircleButton\n" + //
                        "灵感来自Nicola Felasquez Felaco的发布会，并引用JustinFincher / JZMultiChoicesCircleButton。"));

        items.add(new ItemModel("3加载动画",//
                "防雅虎新闻加载过渡动画之小球旋转跳跃然后团灭的故事3\n"));

        items.add(new ItemModel("4qq空间头部",//
                "仿QQ控件值打造个性化可拉伸头部空间\n" + "共享元素跳转"));
//5
        items.add(new ItemModel("5 运动进度条", " 和一些小控件\n"));

        items.add(new ItemModel("6  RecyclerView   交互效果", " \n"));

        items.add(new ItemModel("7九宫格", " \n"));

        items.add(new ItemModel("8 Paint", "  \n"));
//9
        items.add(new ItemModel("9 TabLayout 和viewpager", ""));
        items.add(new ItemModel("10属性动画的学习与实现", ""));
        items.add(new ItemModel("11", ""));
    }

    //给予erp的二次开发  以及周边app的开发  卖用户产品
    @Override
    public void onItemClick(int position) {
        if (position == 0) startActivity(new Intent(context, MainActivity.class));
        if (position == 1) startActivity(new Intent(context, TranslateActivity.class));
        if (position == 2) startActivity(new Intent(context, MultiButtonActivity.class));
        if (position == 3) startActivity(new Intent(context, YahooNewsActivity.class));
        //qq空间头部
        if (position == 4) startActivity(new Intent(context, QQzoneActivity.class));
        if (position == 5) startActivity(new Intent(context, QQStepActivity.class));
        if (position == 6) startActivity(new Intent(context, RecyclerViewActivity.class));

        if (position == 7) startActivity(new Intent(context, KTLockPatternActivity.class));

        if (position == 8) startActivity(new Intent(context, SVGViewActivity.class));

        if (position == 9) startActivity(new Intent(context, CVActivity.class));

        if (position == 10)
            startActivity(new Intent(context, AnimationActivity.class));

        if (position == 11)
            startActivity(new Intent(context, UploadFileActivity.class));


        //
    }
}
