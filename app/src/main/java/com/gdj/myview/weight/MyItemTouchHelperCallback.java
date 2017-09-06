package com.gdj.myview.weight;

import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.gdj.myview.R;

/**
 * Comment:
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/8/20
 */
public class MyItemTouchHelperCallback extends ItemTouchHelper.Callback {

    ItemTouchMoveListener itemTouchMoveListener;
    private float alpha;

    public MyItemTouchHelperCallback(ItemTouchMoveListener itemTouchMoveListener) {
        this.itemTouchMoveListener = itemTouchMoveListener;
    }

    //CallBack回调监听时先调动用的,用来判断当前是什么动作,比如判断方向(监听哪个方向的拖动)
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        //方向: up  0 down 1  ;  left   right
        //常量 ItemTouchHelper.UP   0x0001  ItemTouchHelper.DOWN   0x001
        //return 0x0011;   ItemTouchHelper.UP   DOWN
        //makeMovementFlags()
        //拖拽方向是哪两个
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;

        //侧滑方向是哪两个
        int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;  //0


        int flags = makeMovementFlags(dragFlags, swipeFlags);
        return flags;//不想实现的就为0
    }

    //是否允许长按拖拽
    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    //当移动的时候,回调的方法---------  拖拽
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder srcHolder, RecyclerView.ViewHolder target) {
        //自拖拽的过程中不断地调用adapter.notifyMove
        if (srcHolder.getAdapterPosition() != target.getAdapterPosition()) {
            return false;
        }

        // 在拖拽的过程当中不断地调用adapter.notifyItemMoved(from,to);
        boolean result = itemTouchMoveListener.onItemMove(srcHolder.getAdapterPosition(), target.getAdapterPosition());
        Log.w("result", result + "result");
        return result;
    }

    //当侧滑的时候,回调的方法-
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        //监听侧滑 1 删除数据 2 调用adapter的notifyremove方法
        itemTouchMoveListener.onItemRemove(viewHolder.getAdapterPosition());
    }

    /***/
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        //判断选中状态
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {//只要不是静置
            viewHolder.itemView.setBackgroundColor(viewHolder.itemView.getContext().getResources().getColor(R.color.blue));
        }

        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

        //恢复
        viewHolder.itemView.setBackgroundColor(Color.WHITE);
        viewHolder.itemView.setAlpha(1);//1~0
        viewHolder.itemView.setScaleX(1);
        viewHolder.itemView.setScaleY(1);
        super.clearView(recyclerView, viewHolder);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            alpha = 1 - (Math.abs(dX) / viewHolder.itemView.getWidth());

            //透明度动画   dx 水平方向移动的增量(负左正右)   0~View.getWidth()
            viewHolder.itemView.setAlpha(alpha);//1~0
            viewHolder.itemView.setScaleX(alpha);
            viewHolder.itemView.setScaleY(alpha);
        }

        //super 自带transLate 效果
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}
/**
 * 滑动删除的时候,然后在滑动页面,结果有的条目会出现空白
 * 1.解决 bug：滑动删除的时候，然后再滑动页面，结果有的条目不出现了 有空白的地方。
 * 原因：ListView和RecyclerView都会有复用条目itemView。这样就会导致上面的问题。
 * 解决：很多。比如在clearView回调方法里面去恢复这些条目的状态
 *
 * @Override public void clearView(RecyclerView recyclerView, ViewHolder viewHolder) {
 * // 恢复
 * viewHolder.itemView.setBackgroundColor(Color.WHITE);
 * <p>
 * //		viewHolder.itemView.setAlpha(1);//1~0
 * //		viewHolder.itemView.setScaleX(1);//1~0
 * //		viewHolder.itemView.setScaleY(1);//1~0
 * super.clearView(recyclerView, viewHolder);
 * }
 * <p>
 * <p>
 * 2.类似QQ的条目侧滑一半的效果
 * 提示：
 * 方法一：判断
 * //判断是否超出或者达到width/2，就让其setTranslationX位一般的位置
 * if(Math.abs(dX)<=viewHolder.itemView.getWidth()/2){
 * viewHolder.itemView.setTranslationX(-0.5f*viewHolder.itemView.getWidth());
 * }else{
 * viewHolder.itemView.setTranslationX(dX);
 * }
 * 方法二：ItemView就是一个ViewPager，上面的view可以朝反方向设置TranslationX
 * <p>
 * 1.解决 bug：滑动删除的时候，然后再滑动页面，结果有的条目不出现了 有空白的地方。
 * 原因：ListView和RecyclerView都会有复用条目itemView。这样就会导致上面的问题。
 * 解决：很多。比如在clearView回调方法里面去恢复这些条目的状态    或者在onChildDraw方法中最后判断如果透明度为0,则设置为1
 * @Override public void clearView(RecyclerView recyclerView, ViewHolder viewHolder) {
 * // 恢复
 * viewHolder.itemView.setBackgroundColor(Color.WHITE);
 * <p>
 * //		viewHolder.itemView.setAlpha(1);//1~0
 * //		viewHolder.itemView.setScaleX(1);//1~0
 * //		viewHolder.itemView.setScaleY(1);//1~0
 * super.clearView(recyclerView, viewHolder);
 * }
 * <p>
 * <p>
 * 2.类似QQ的条目侧滑一半的效果
 * 提示：
 * 方法一：判断
 * //判断是否超出或者达到width/2，就让其setTranslationX位一般的位置
 * if(Math.abs(dX)<=viewHolder.itemView.getWidth()/2){
 * viewHolder.itemView.setTranslationX(-0.5f*viewHolder.itemView.getWidth());
 * }else{
 * viewHolder.itemView.setTranslationX(dX);
 * }
 * 方法二：ItemView就是一个ViewPager，上面的view可以朝反方向设置TranslationX
 */