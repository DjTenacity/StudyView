package com.gdj.myview.ui.listener;

import android.support.v7.widget.RecyclerView;

/**
 * Comment:该接口用于需要主动回调拖拽效果
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/9/4
 */
public interface DragListener {

    void startDragListener(RecyclerView.ViewHolder viewHolder);
}
