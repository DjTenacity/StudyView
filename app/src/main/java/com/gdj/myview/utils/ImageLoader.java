package com.gdj.myview.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;

/**
 * Comment: 管理图片的工具类
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/10/5
 */
public class ImageLoader {

    /**
     * 图片缓存技术的核心类,用于缓存所有下载好的图片,在程序内存大道设定值时会将最少最近使用的图片移除掉
     */
    private static LruCache<String, Bitmap> mMemoryCache;

    private static ImageLoader mImageLoader;


    private ImageLoader() {
        //获取应用程序最大的可用内存

        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;

        //设置图片缓存大小为程序最大可用内存的1/8
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    /**
     * 获取ImageLoader的实例
     */
    public static ImageLoader getInstance() {
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader();
        }
        return mImageLoader;
    }

    /**
     * 将一张图片缓存到Lrucache中
     * <p>
     * key--->传入图片的URL地址
     * bitmao--->传入从网络下载的bitmap
     **/
    public void addBitmapTempToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMomeryCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    /**
     * 从Lrucache中获取一张图片,如果不存在就返回null
     */
    private Bitmap getBitmapFromMomeryCache(String key) {
        return mMemoryCache.get(key);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth) {
        //源图片的宽度
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (width > reqWidth) {
            //计算出实际宽度和目标宽度的比率
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = widthRatio;
        }
        return inSampleSize;
    }

}
