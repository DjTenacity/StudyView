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
import com.gdj.myview.ui.activity.CoordinatorActivity2;
import com.gdj.myview.ui.activity.FlightSeatActivity;
import com.gdj.myview.ui.activity.KTLockPatternActivity;
import com.gdj.myview.ui.activity.MainActivity;
import com.gdj.myview.ui.activity.MultiButtonActivity;
import com.gdj.myview.ui.activity.MyAnimationActivity;
import com.gdj.myview.ui.activity.PaintViewActivity;
import com.gdj.myview.ui.activity.QQStepActivity;
import com.gdj.myview.ui.activity.QQzoneActivity;
import com.gdj.myview.ui.activity.SplashActivity;
import com.gdj.myview.ui.activity.YahooNewsActivity;

import java.util.List;


public class MYFragment extends MainFragment {

    @Override
    public void fillData(List<ItemModel> items) {

        items.add(new ItemModel("手机信息与照相机",//
                "sdk版本与手机名字\n" +//
                        "类似于微信，可以切换摄像头\n" +//
                        ""));

        items.add(new ItemModel("CoordinatorLayout的用法2",//
                " rv的adapter没有填充数据，所以会有问题" +//
                        " \n" +//
                        "而且对主题有一定要求 \n" +//
                        " "));
//2
        items.add(new ItemModel("具有3D视差效果的多选按钮。",//
                "https://github.com/gjiazhe/MultiChoicesCircleButton\n" + //
                        "灵感来自Nicola Felasquez Felaco的发布会，并引用JustinFincher / JZMultiChoicesCircleButton。"));

        items.add(new ItemModel("加载动画",//
                "防雅虎新闻加载过渡动画之小球旋转跳跃然后团灭的故事3\n"));

        items.add(new ItemModel("qq空间头部",//
                "仿QQ控件值打造个性化可拉伸头部空间\n"+"共享元素跳转"));
//5
        items.add(new ItemModel("运动进度条", " 和一些小控件\n"));

        items.add(new ItemModel("高仿小红书欢迎界面", " \n"));

        items.add(new ItemModel("九宫格", " 微信下拉拍照自定义Behavior玩转特效--滑动的卡片\n"));

        items.add(new ItemModel("2222", " 微信下拉拍照自定义Behavior玩转特效--滑动的卡片\n"));
//9
        items.add(new ItemModel("飞机选位", ""));
        items.add(new ItemModel("动画", ""));
    }

    //给予erp的二次开发  以及周边app的开发  卖用户产品
    @Override
    public void onItemClick(int position) {
        if (position == 0) startActivity(new Intent(context, MainActivity.class));
        if (position == 1) startActivity(new Intent(context, CoordinatorActivity2.class));
        if (position == 2) startActivity(new Intent(context, MultiButtonActivity.class));
        if (position == 3) startActivity(new Intent(context, YahooNewsActivity.class));
        //qq空间头部
        if (position == 4) startActivity(new Intent(context, QQzoneActivity.class));
        if (position == 5) startActivity(new Intent(context, QQStepActivity.class));
        if (position == 6) startActivity(new Intent(context, SplashActivity.class));

        if (position == 7) startActivity(new Intent(context, KTLockPatternActivity.class));

        if (position == 8) startActivity(new Intent(context, PaintViewActivity.class));

        if (position == 9) startActivity(new Intent(context, FlightSeatActivity.class));
        if (position == 10) {
            startActivity(new Intent(context, MyAnimationActivity.class));


        }

        //
    }
}
