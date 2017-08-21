package com.gdj.myview.utils;

/**
 * Comment:
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/8/20
 */

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.graphics.Palette;

/**
 * Created by yuan on 2016/8/28.
 * 获取图片背景色
 */
public class PaletteUtil implements Palette.PaletteAsyncListener {

    private static PaletteUtil instance;

    private PatternCallBack patternCallBack;

    public static PaletteUtil getInstance() {
        if (instance == null) {
            instance = new PaletteUtil();
        }
        return instance;
    }

    public synchronized void init(Bitmap bitmap, PatternCallBack patternCallBack) {
        Palette.from(bitmap).generate(this);
        this.patternCallBack = patternCallBack;
    }

    public synchronized void init(Resources resources, int resourceId, PatternCallBack patternCallBack) {
        Bitmap bitmap = BitmapFactory.decodeResource(resources, resourceId);
        Palette.from(bitmap).generate(this);
        this.patternCallBack = patternCallBack;
    }

    @Override
    public synchronized void onGenerated(Palette palette) {
        Palette.Swatch a = palette.getVibrantSwatch();
        Palette.Swatch b = palette.getLightVibrantSwatch();
        int colorEasy = 0;
        if (b != null) {
            colorEasy = b.getRgb();
        }
        patternCallBack.onCallBack(changedImageViewShape(a.getRgb(), colorEasy)
                , a.getTitleTextColor());
    }

    /**
     * 创建Drawable对象
     *
     * @param RGBValues
     * @param two
     * @return
     */
    private Drawable changedImageViewShape(int RGBValues, int two) {
        if (two == 0) {
            two = colorEasy(RGBValues);
        } else {
            two = colorBurn(two);
        }
        GradientDrawable shape = new GradientDrawable(GradientDrawable.Orientation.TL_BR
                , new int[]{RGBValues, two});
        shape.setShape(GradientDrawable.RECTANGLE);
        //设置渐变方式
        shape.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        //圆角
        shape.setCornerRadius(8);
        return shape;
    }


    /**
     * 颜色变浅处理
     *
     * @param RGBValues
     * @return
     */
    private int colorEasy(int RGBValues) {
        int red = RGBValues >> 16 & 0xff;
        int green = RGBValues >> 8 & 0xff;
        int blue = RGBValues & 0xff;
        if (red == 0) {
            red = 10;
        }
        if (green == 0) {
            green = 10;
        }
        if (blue == 0) {
            blue = 10;
        }
        red = (int) Math.floor(red * (1 + 0.1));
        green = (int) Math.floor(green * (1 + 0.1));
        blue = (int) Math.floor(blue * (1 + 0.1));
        return Color.rgb(red, green, blue);
    }

    /**
     * 颜色加深处理
     *
     * @param RGBValues
     * @return
     */
    private int colorBurn(int RGBValues) {
        int red = RGBValues >> 16 & 0xff;
        int green = RGBValues >> 8 & 0xff;
        int blue = RGBValues & 0xff;
        red = (int) Math.floor(red * (1 - 0.1));
        green = (int) Math.floor(green * (1 - 0.1));
        blue = (int) Math.floor(blue * (1 - 0.1));
        return Color.rgb(red, green, blue);
    }


    public interface PatternCallBack {
        void onCallBack(Drawable drawable, int titleColor);
    }

}

/**
 * 使用方法 ：
 * （1） init(Resource res,int resId,callback) 方式
 * PaletteUtil.getInstance().init(getResources(),R.mipmap.icon_one,new PaletteUtil.PatternCallBack() {
 *
 * @Override public void onCallBack(Drawable drawable, int titleColor) {
 * tv.setTextColor(titleColor);
 * tv.setBackgroundDrawable(drawable);
 * }});
 * 
 * （2） init(bitmap,callback) 方式
 * Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.icon_one);
 * PaletteUtil.getInstance().init(bitmap, new PaletteUtil.PatternCallBack() {
 * @Override public void onCallBack(Drawable drawable, int titleColor) {
 * }
 * });
 **/