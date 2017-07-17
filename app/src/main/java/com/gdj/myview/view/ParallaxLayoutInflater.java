package com.gdj.myview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.gdj.myview.R;
import com.gdj.myview.ui.fragment.ParallaxFragment;

/**
 * Created by Administrator on 2017/7/17.
 */

public class ParallaxLayoutInflater extends LayoutInflater {

    private ParallaxFragment fragment;

    public ParallaxLayoutInflater(LayoutInflater original, Context newContext, ParallaxFragment parallaxFragment) {
        super(original, newContext);
        this.fragment = parallaxFragment;
        //重新设置布局加载的工厂
        //工厂：创建布局文件中所有的视图
        setFactory(new ParallaxFactory(this));
    }


    @Override
    public LayoutInflater cloneInContext(Context newContext) {
        return new ParallaxLayoutInflater(this, newContext,fragment);
    }

    class ParallaxFactory implements LayoutInflater.Factory {
        LayoutInflater layoutInflater;
        private final String[] sClassPrefix = {"android.weight.", "android.view."};

        public ParallaxFactory(LayoutInflater layoutInflater) {
            this.layoutInflater = layoutInflater;
        }

        //每一个视图都是通过这个方法来搞定的，现在是有我们自己搞定！——————自定义视图的创建过程
        @Override
        public View onCreateView(String name, Context context, AttributeSet attrs) {
            //只需要传入name，有什么属性attrs，就会创建一个视图，然后将其返回，就会有自定义属性了
            //    public final View createView(String name, String prefix, AttributeSet attrs)
            //
            // android.weight.TextView

            View view = null;

            //1，自定义控件不需要前缀——————自定义控件本来就是完整类名，直接根据完整类名去加载这个类，通过构造方法实例化
            //2，系统视图需要加上前缀android.view 和android.weight
            //    layoutInflater.createView(name, prefix, attrs);
            if (view == null) {
                view = createViewOrFailQuietly(name, context, attrs);
            }
//已经视图实例化完成
            if (view != null) {
                //获取自定义属性，通过标签关联到视图上
                setViewTag(view, context, attrs);
            }
            return view;
        }

        private void setViewTag(View view, Context context, AttributeSet attrs) {
            int[] attrIds = {R.attr.a_in, R.attr.a_out, R.attr.x_in, R.attr.x_out, R.attr.y_in, R.attr.y_out,};

            TypedArray a = context.obtainStyledAttributes(attrs, attrIds);

            if (a != null) {
                //获取自定义属性的值
                if (a.length() > 0) {
                    //无非是透明度和位置的变化
                    ParallaxViewTag tag = new ParallaxViewTag();
                    tag.alphaIn = a.getFloat(0, 0f);
                    tag.alphaOut = a.getFloat(1, 0f);
                    tag.xIn = a.getFloat(2, 0f);
                    tag.xOut = a.getFloat(3, 0f);
                    tag.yIn = a.getFloat(4, 0f);
                    tag.yOut = a.getFloat(5, 0f);

                    //假设有多个tag的话，要制定tag 的id，如果直接setTage(tag),只能算是一个tag，
                    // 相当于一个map，根据key得到tag
                    view.setTag(R.id.parallax_view_tag, tag);
                    //现在要做的就是和fragment关联起来
                    fragment.getParallaxViews().add(view);
                }
                a.recycle();
            }

        }

        private View createViewOrFailQuietly(String name, String prefix, Context context, AttributeSet attrs) {
            try {
                return layoutInflater.createView(name, prefix, attrs);
            } catch (ClassNotFoundException e) {
                return null;
            }

        }

        private View createViewOrFailQuietly(String name, Context context, AttributeSet attrs) {
            if (name.contains(".")) {//自定义视图不需要前缀
                createViewOrFailQuietly(name, null, context, attrs);

            }
            //系统视图需要加上前缀
            for (String prefix : sClassPrefix) {
                View view = createViewOrFailQuietly(name, prefix, context, attrs);
                if (view != null) {
                    return view;
                }
            }
            return null;
        }
    }

}
