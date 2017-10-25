package com.gdj.myview.ui.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gdj.myview.R;
import com.gdj.myview.view.GallaryHorizonalScrollView;
import com.gdj.myview.view.RevealDrawable;
import com.gdj.myview.view.RevealDrawable2;
import com.gdj.myview.view.searchview.Controller1;
import com.gdj.myview.view.searchview.SearchView;
import com.gdj.myview.view.searchview.SearchView2;

/**
 * Comment:
 * 画板的变换:
 * 如何恢复画板:
 * canvas.save();//保存画布    ****变换绘制
 * canvas.restore()恢复到上次save
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/10/21
 */
public class CanvasStudyFragment extends Fragment {

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //performCreateView
        rootView = inflater.inflate(R.layout.fragment_canvas_study, null);
        ;
        initData();
        initView();

        return rootView;
    }

    private ImageView iv;
    private int[] mImgIds = new int[]{ //7个
            R.drawable.avft,
            R.drawable.box_stack,
            R.drawable.bubble_frame,
            R.drawable.bubbles,
            R.drawable.bullseye,
            R.drawable.circle_filled,
            R.drawable.circle_outline,

            R.drawable.avft,
            R.drawable.box_stack,
            R.drawable.bubble_frame,
            R.drawable.bubbles,
            R.drawable.bullseye,
            R.drawable.circle_filled,
            R.drawable.circle_outline
    };
    private int[] mImgIds_active = new int[]{
            R.drawable.avft_active, R.drawable.box_stack_active, R.drawable.bubble_frame_active,
            R.drawable.bubbles_active, R.drawable.bullseye_active, R.drawable.circle_filled_active,
            R.drawable.circle_outline_active,
            R.drawable.avft_active, R.drawable.box_stack_active, R.drawable.bubble_frame_active,
            R.drawable.bubbles_active, R.drawable.bullseye_active, R.drawable.circle_filled_active,
            R.drawable.circle_outline_active
    };

    public Drawable[] revealDrawables;
    protected int level = 5000;
    private GallaryHorizonalScrollView hzv;


    private void initData() {
        revealDrawables = new Drawable[mImgIds.length];
    }

    private void initView() {
        for (int i = 0; i < mImgIds.length ; i++) {
            RevealDrawable2 rd = new RevealDrawable2(
                    getResources().getDrawable(mImgIds[i]),
                    getResources().getDrawable(mImgIds_active[i]),
                    RevealDrawable.HORIZONTAL);
            revealDrawables[i] = rd;
        }
        hzv = (GallaryHorizonalScrollView) rootView.findViewById(R.id.hzv);
        hzv.addImageViews(revealDrawables);

        final SearchView2 sv2 = (SearchView2) rootView.findViewById(R.id.sv2);
        sv2.setCotroller(new Controller1());

        rootView.findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sv2.start();
            }
        });

        rootView.findViewById(R.id.restart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sv2.reset();
            }
        });
    }
}
