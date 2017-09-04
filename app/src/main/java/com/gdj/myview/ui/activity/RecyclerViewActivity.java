package com.gdj.myview.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.gdj.myview.R;
import com.gdj.myview.ui.listener.DragListener;
import com.gdj.myview.weight.MyItemTouchHelperCallback;
import com.gdj.myview.weight.StrAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Comment:RecyclerView   交互效果
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/8/20
 */
public class RecyclerViewActivity extends AppCompatActivity implements DragListener {

    @BindView(R.id.recyclerView)
    RecyclerView rv;
    private ItemTouchHelper itemTouchHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv);
        ButterKnife.bind(this);
        rv.setLayoutManager(new LinearLayoutManager(this));

        List<String> Strs = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            Strs.add(Math.random() + "望断天涯路" + i);
        }

        StrAdapter strAdapter = new StrAdapter(this, Strs);
        rv.setAdapter(strAdapter);
        //条目触摸帮助类
        ItemTouchHelper.Callback callback = new MyItemTouchHelperCallback(strAdapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(rv);

    }

    @Override
    public void startDragListener(RecyclerView.ViewHolder viewHolder) {
        if (itemTouchHelper != null) itemTouchHelper.startDrag(viewHolder);
    }
}
