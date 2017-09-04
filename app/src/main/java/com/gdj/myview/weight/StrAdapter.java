package com.gdj.myview.weight;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gdj.myview.R;
import com.gdj.myview.ui.listener.DragListener;

import java.util.Collections;
import java.util.List;

/**
 * Comment:
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/8/20
 */
public class StrAdapter extends RecyclerView.Adapter<StrAdapter.StrViewHolder> implements ItemTouchMoveListener {
    List<String> strs;

    public StrAdapter(DragListener dragListener, List<String> strs) {
        this.strs = strs;
        this.dragListener = dragListener;
    }

    @Override
    public StrViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StrViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_type, parent, false));
    }

    @Override
    public void onBindViewHolder(final StrViewHolder holder, int position) {
        holder.tv.setText(strs.get(position));
        holder.tv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //监听成功,传递触摸情况给callback
                    dragListener.startDragListener(holder);
                }

                return false;
            }
        });

    }


    @Override
    public int getItemCount() {
        return strs.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        //数据的交换,刷新
        Collections.swap(strs, fromPosition, toPosition);//reserve 反转
        Log.w("result", fromPosition + "result" + toPosition);
        notifyItemMoved(fromPosition, toPosition);//刷新
        return false;
    }

    @Override
    public boolean onItemRemove(int pos) {
        strs.remove(pos);
        notifyItemRemoved(pos);
        return false;
    }

    class StrViewHolder extends RecyclerView.ViewHolder {

        private final TextView tv;

        public StrViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.divider);
        }
    }

    DragListener dragListener;

//    public interface DragListener {
//        /**
//         * 该接口用于需要主动回调拖拽效果
//         **/
//        public void startDragListener(RecyclerView.ViewHolder viewHolder);
//    }
//
//    public void setDragListener(DragListener dragListenerl) {
//        this.dragListener = dragListenerl;
//    }


}
