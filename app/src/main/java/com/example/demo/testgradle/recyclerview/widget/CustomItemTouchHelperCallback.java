package com.example.demo.testgradle.recyclerview.widget;

import android.graphics.Color;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;


/**
 * Created by guilianghuang on 2017/3/22.
 */

public class CustomItemTouchHelperCallback extends ItemTouchHelper.Callback {
    private OnItemTouchCallbackListener onItemTouchCallbackListener;

    private boolean canDrag = true;
    private boolean canSwipe = true;

    public CustomItemTouchHelperCallback(OnItemTouchCallbackListener onItemTouchCallbackListener) {
        this.onItemTouchCallbackListener = onItemTouchCallbackListener;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int dragFlags = 0;
        int swipeFlags = 0;
        if (layoutManager instanceof GridLayoutManager) {
            // 如果是Grid布局，则不能滑动，只能上下左右拖动
            dragFlags =
                    ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            swipeFlags =ItemTouchHelper.LEFT ;
        } else if (layoutManager instanceof LinearLayoutManager) {
            // 如果是纵向Linear布局，则能上下拖动，左右滑动
            if (((LinearLayoutManager) layoutManager).getOrientation() == LinearLayoutManager.VERTICAL) {
                dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                swipeFlags = ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT;
            } else {
                // 如果是横向Linear布局，则能左右拖动，上下滑动
                swipeFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                dragFlags = ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT;
            }
        }
        return makeMovementFlags(dragFlags, swipeFlags); //该方法指定可进行的操作
    }

    /**
     * 拖动时回调，在这里处理拖动事件
     *
     * @param viewHolder 被拖动的view
     * @param target 目标位置的view
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                          RecyclerView.ViewHolder target) {
        //这里我们直接将参数传递到回调接口方法中，提高复用性
        return onItemTouchCallbackListener.onMove(recyclerView, viewHolder, target);
    }

    /**
     * 滑动时回调
     *
     * @param direction 回调方向
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        onItemTouchCallbackListener.onSwiped(viewHolder, direction);
    }

    /**
     * 在这个回调中，如果返回true，表示可以触发长按拖动事件，false则表示不能
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return canDrag;
    }

    /**
     * 在这个回调中，如果返回true，表示可以触发滑动事件，false表示不能
     */
    @Override
    public boolean isItemViewSwipeEnabled() {
        return canSwipe;
    }

    public void setCanDrag(boolean canDrag) {
        this.canDrag = canDrag;
    }

    public void setCanSwipe(boolean canSwipe) {
        this.canSwipe = canSwipe;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            // ItemTouchHelper.ACTION_STATE_IDLE 看看源码解释就能理解了
            // 侧滑或者拖动的时候背景设置为灰色
            viewHolder.itemView.setBackgroundColor(Color.GRAY);
        }
    }


    /**
     * 回到正常状态的时候回调
     */
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        // 正常默认状态下背景恢复默认
        viewHolder.itemView.setBackgroundColor(0);
        ViewCompat.setTranslationX(viewHolder.itemView,0);
    }
}