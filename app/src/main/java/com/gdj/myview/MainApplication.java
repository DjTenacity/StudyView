package com.gdj.myview;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;


/**
 * @author 刘琛慧
 *         date 2015/10/12.
 */
public class MainApplication extends Application {
    public static final String USER_MOBILE = "USER_MOBILE";
    public static final String SERVICE_PHONE = "SERVICE_PHONE";
    public static final String USER_NICK_NAME = "USER_NICK_NAME";
    public static final String MAIN_PREFERENCE = "INFO_PREFERENCE";
    public static final String LAST_ACTIVE_TIME = "LAST_ACTIVE_TIME";
    public static final long SESSION_VALID_PERIOD = 15 * 60 * 1000;
    public SharedPreferences mainPreferences;
    public static final String USER_NAME = "USER_NAME";
    public static final String USER_PASSWORD = "USER_PASSWORD";
    public static final String USER_EXCHANGE = "USER_EXCHANGE";

    //主线程handler
    private static Handler mMainThreadHandler = new Handler();
    //主线程
    private static Thread mMainThread = Thread.currentThread();
    //主线程Id
    private static int mMainThreadId = android.os.Process.myTid();
    //context
    private static MainApplication instance;
    //支付相关密匙信息
    //内存检测watcher
//    private RefWatcher refWatcher;

    private boolean isLogin;

    //    PatchManager patchManager;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
//        patchManager = new PatchManager(this);
//        patchManager.init(BuildConfig.VERSION_NAME);//current version

        ImagePicker imagePicker = ImagePicker.getInstance();
        //  imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(1);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }


    public static Thread getMainThread() {
        return mMainThread;
    }

    public static int getMainThreadId() {
        return mMainThreadId;
    }

    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    public static MainApplication getInstance() {
        return instance;
    }

    public static SharedPreferences getMainPreferences() {
        return getInstance().mainPreferences;
    }

/*    public static RefWatcher getRefWatcher() {
        MainApplication application = (MainApplication) MainApplication.getInstance();
        return application.refWatcher;
    }*/

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

}