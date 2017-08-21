package com.gdj.myview.weight;

/**
 * Comment:
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/8/20
 */


public interface ItemTouchMoveListener {

    /**
     * 当拖拽的时候回调
     * 可以再次方法里面实现:拖拽条目并实现刷新效果
     */
    boolean onItemMove(int fromPosition, int toPosition);
    boolean onItemRemove(int pos);

}
