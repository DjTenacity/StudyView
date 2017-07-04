package com.gdj.camera.lisenter;

/**
 * create by CJT2325
 *.
 */

public interface CaptureLisenter {
    void takePictures();

    void recordShort(long time);

    void recordStart();

    void recordEnd(long time);

    void recordZoom(float zoom);

    void recordError();
}
