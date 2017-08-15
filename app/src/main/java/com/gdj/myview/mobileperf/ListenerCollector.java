package com.gdj.myview.mobileperf;

import android.view.View;

import java.util.WeakHashMap;

/**
 * Comment: 设置监听也很容易内存泄漏
 *
 * 假设设置了很多监听,这些监听是需要回调的,在某种情况下所有的view都要回调监听或是某一个view的回调,所以保存下来
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/8/13
 */
public class ListenerCollector {
//屏幕旋转时,给view设置监听,view被干掉了,listener还存在,会有内存泄漏,去掉static可以解决问题,但是类也没用了
    //handler最后都会removeCALLBACK
    //所以要在调用MyListenerView 的界面的  onStop  清除
    //onstop 不可见 ,按下home键时app存在后面可能一直活着,监听就可以暂时不用管,所以清除以避免没必要的内存泄漏,  -----
// ----app在-onReStart时候,自定义控件就会被创建,也会创建监听
    static  private WeakHashMap<View,MyListenerView.MyListener> mListenerMap=new WeakHashMap<>();

    public static void setsListener(View view,MyListenerView.MyListener mListener){
        mListenerMap.put( view,mListener);
    }

    public void clearListener(){
        //移除所有,很多view

    }
}
