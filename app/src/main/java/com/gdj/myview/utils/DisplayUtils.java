package com.gdj.myview.utils;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build.VERSION;
import android.provider.Settings.SettingNotFoundException;
import android.provider.Settings.System;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.gdj.myview.App;

import java.lang.reflect.Field;

public class DisplayUtils {
    public static int SCREEN_WIDTH_PIXELS;
    public static int SCREEN_HEIGHT_PIXELS;
    public static int SCREEN_DENSITY_DPI;
    public static float SCREEN_DENSITY;
    public static int SCREEN_WIDTH_DP;
    public static int SCREEN_HEIGHT_DP;
    private static boolean sInitialed;

    public DisplayUtils() {
    }

    public static void init(Context context) {
        if (!sInitialed && context != null) {
            sInitialed = true;
            DisplayMetrics dm = new DisplayMetrics();
            WindowManager wm = (WindowManager) context.getSystemService("window");
            wm.getDefaultDisplay().getMetrics(dm);
            SCREEN_WIDTH_PIXELS = dm.widthPixels;
            SCREEN_HEIGHT_PIXELS = dm.heightPixels;
            SCREEN_DENSITY = dm.density;
            SCREEN_DENSITY_DPI = dm.densityDpi;
            SCREEN_WIDTH_DP = (int) ((float) SCREEN_WIDTH_PIXELS / dm.density);
            SCREEN_HEIGHT_DP = (int) ((float) SCREEN_HEIGHT_PIXELS / dm.density);
        }
    }

    public static int dp2px(float dp) {
        float scale = SCREEN_DENSITY;
        return (int) (dp * scale + 0.5F);
    }

    public static int px2dp(float pxValue) {
        return (int) (pxValue / SCREEN_DENSITY + 0.5F);
    }

    public static int px2sp(Context context, float pxValue) {
        return (int) (pxValue / SCREEN_DENSITY + 0.5F);
    }

    public static int px2sp(float pxValue) {
        return (int) (pxValue / SCREEN_DENSITY + 0.5F);
    }

    public static int sp2px(float spValue) {
        return (int) (spValue * SCREEN_DENSITY + 0.5F);
    }

    public static void hideSystemUI(Activity activity) {
        short uiFlags = 3846;
        int uiFlags1;
        if (VERSION.SDK_INT >= 19) {
            uiFlags1 = uiFlags | 4096;
        } else {
            uiFlags1 = uiFlags | 1;
        }

        activity.getWindow().getDecorView().setSystemUiVisibility(uiFlags1);
    }

    public static void hideSystemUI(Window window) {
        short uiFlags = 3846;
        int uiFlags1;
        if (VERSION.SDK_INT >= 19) {
            uiFlags1 = uiFlags | 4096;
        } else {
            uiFlags1 = uiFlags | 1;
        }

        window.getDecorView().setSystemUiVisibility(uiFlags1);
    }

    public static int getStatusBarHeight(Context context) {
        Class c = null;
        Object obj = null;
        Field field = null;
        boolean x = false;
        boolean sbar = false;

        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            int x1 = Integer.parseInt(field.get(obj).toString());
            int sbar1 = context.getResources().getDimensionPixelSize(x1);
            return sbar1;
        } catch (Exception var7) {
            var7.printStackTrace();
            return 0;
        }
    }

    public static int getScreenWidth() {
        WindowManager windowManager = (WindowManager) App.context.getSystemService("window");
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    public static int getScreenHeight() {
        WindowManager windowManager = (WindowManager) App.context.getSystemService("window");
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    public static int getScreenWidth(Context context) {
        WindowManager windowMgr = (WindowManager) context.getSystemService("window");
        return windowMgr.getDefaultDisplay().getWidth();
    }

    public static int getScreenHeight(Context context) {
        WindowManager windowMgr = (WindowManager) context.getSystemService("window");
        return windowMgr.getDefaultDisplay().getHeight();
    }

    public static void setLandscape(Activity activity) {
        activity.setRequestedOrientation(0);
    }

    public static void setPortrait(Activity activity) {
        activity.setRequestedOrientation(1);
    }

    public static boolean isLandscape() {
        return App.context.getResources().getConfiguration().orientation == 2;
    }

    public static boolean isPortrait() {
        return App.context.getResources().getConfiguration().orientation == 1;
    }

    public static int getScreenRotation(Activity activity) {
        switch (activity.getWindowManager().getDefaultDisplay().getRotation()) {
            case 0:
            default:
                return 0;
            case 1:
                return 90;
            case 2:
                return 180;
            case 3:
                return 270;
        }
    }

    public static Bitmap captureWithStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Bitmap ret = Bitmap.createBitmap(bmp, 0, 0, dm.widthPixels, dm.heightPixels);
        view.destroyDrawingCache();
        return ret;
    }

    public static Bitmap captureWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int statusBarHeight = getStatusBarHeight(activity);
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Bitmap ret = Bitmap.createBitmap(bmp, 0, statusBarHeight, dm.widthPixels, dm.heightPixels - statusBarHeight);
        view.destroyDrawingCache();
        return ret;
    }

    public static boolean isScreenLock() {
        KeyguardManager km = (KeyguardManager) App.context.getSystemService("keyguard");
        return km.inKeyguardRestrictedInputMode();
    }

    public static void setSleepDuration(int duration) {
        System.putInt(App.context.getContentResolver(), "screen_off_timeout", duration);
    }

    public static int getSleepDuration() {
        try {
            return System.getInt(App.context.getContentResolver(), "screen_off_timeout");
        } catch (SettingNotFoundException var1) {
            var1.printStackTrace();
            return -123;
        }
    }
}
