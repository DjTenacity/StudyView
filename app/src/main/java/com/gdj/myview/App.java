package com.gdj.myview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.gdj.myview.utils.DisplayUtils;

/**
 * 作者：${LoveDjForever} on 2017/7/7 18:28
 *  * 邮箱： @qq.com
 */

public class App extends Application {

    public static Context context;
    public static Activity activity;
    public static boolean logFlag = false;
    public static boolean isSave = true;
    public static int DEFAULT_LEVEL = 2;


    public App() {
    }

    public static void init(Context cxt) {
        init(cxt, true);
    }

    public static void init(Context cxt, boolean debug) {
        context = cxt;
        if(debug) {
            logFlag = true;
            isSave = false;
            DEFAULT_LEVEL = 2;
        } else {
            logFlag = false;
            isSave = true;
            DEFAULT_LEVEL = 5;
        }

        DisplayUtils.init(context);
    }
}
