package com.gdj.camera.lisenter;

import android.graphics.Bitmap;

/**
 * =====================================

 * 版    本：1.1.4
 * 创建日期：2017/4/26
 * 描    述：
 * =====================================
 */
public interface JCameraLisenter {

    void captureSuccess(Bitmap bitmap);

    void recordSuccess(String url, Bitmap firstFrame);

    void quit();

}
