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

import android.app.Activity;
import android.content.Intent;


import com.gdj.myview.entry.ItemModel;
import com.gdj.myview.ui.activity.CoordinatorActivity2;
import com.gdj.myview.ui.activity.MainActivity;
import com.gdj.myview.ui.activity.MultiButtonActivity;

import java.util.List;


public class MYFragment extends MainFragment {

    @Override
    public void fillData(List<ItemModel> items) {

        items.add(new ItemModel("手机信息与照相机",//
                                "sdk版本与手机名字\n" +//
                                "可以切换摄像头\n" +//
                                "类似于微信" ));

        items.add(new ItemModel("CoordinatorLayout的用法2",//
                                " " +//
                                " \n" +//
                                " \n" +//
                                " "));

        items.add(new ItemModel("具有3D视差效果的多选按钮。",//
                                "https://github.com/gjiazhe/MultiChoicesCircleButton\n" + //
                                "灵感来自Nicola Felasquez Felaco的发布会，并引用JustinFincher / JZMultiChoicesCircleButton。"));

        items.add(new ItemModel("文件下载",//
                                "1.支持大文件或小文件下载，无论多大文件都不会发生OOM\n" +//
                                "2.支持监听下载进度和下载网速\n" +//
                                "3.支持自定义下载目录和下载文件名"));

        items.add(new ItemModel("文件上传",//
                                "1.支持上传单个文件\n" +//
                                "2.支持同时上传多个文件\n" +//
                                "3.支持多个文件多个参数同时上传\n" +//
                                "4.支持大文件上传,无论多大都不会发生OOM\n" +//
                                "5.支持监听上传进度和上传网速"));
    }

    @Override
    public void onItemClick(int position) {
        if (position == 0) startActivity(new Intent(context, MainActivity.class));
        if (position == 1) startActivity(new Intent(context, CoordinatorActivity2.class));
        if (position == 2) startActivity(new Intent(context, MultiButtonActivity.class));

    }
}
