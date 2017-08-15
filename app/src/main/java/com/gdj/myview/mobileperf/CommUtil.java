package com.gdj.myview.mobileperf;

import android.content.Context;

/**
 * 内存泄漏
 */

public class CommUtil {
    private static CommUtil instance;
    private Context context;
    private CommUtil(Context context){
        this.context = context;
    }

    public static CommUtil getInstance(Context mContext){
        if(instance == null){
            instance = new CommUtil(mContext);
        }
//        else{
//            instance.setContext(mcontext);
//        }
        return instance;
    }

    public void setContext(Context context) {
        this.context = context;
    }

}
