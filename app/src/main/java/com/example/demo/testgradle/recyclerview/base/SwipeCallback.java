package com.example.demo.testgradle.recyclerview.base;

import android.graphics.Color;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.demo.testgradle.util.LoggerUtils;

import java.util.Collections;
import java.util.List;

/**
 * Created by idea on 2017/3/21.
 */

public class SwipeCallback<T> extends ItemTouchHelper.Callback {

    private final RecyclerView.Adapter mAdapter;
    private List<T> mDate;

    public SwipeCallback(List<T> date, RecyclerView.Adapter adapter) {
        this.mAdapter = adapter;
        mDate = date;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        //处理事件的返回拖拽 滑动的方向

        int swipeFlags = ItemTouchHelper.LEFT;
        int dragFlags = 0;
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            dragFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        } else {
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        }
        return makeFlag(swipeFlags, dragFlags);

    }

    /**
     * 拖动的时候不断的回调方法
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder fromViewHolder, RecyclerView.ViewHolder target) {

        int fromPosition = fromViewHolder.getAdapterPosition();
        int targetPosition = target.getAdapterPosition();
        LoggerUtils.logger(fromPosition+"===========>>>"+targetPosition);
        if (fromPosition < targetPosition) {
            for (int i = fromPosition; i < targetPosition; i++) {
                LoggerUtils.logger(i+1+"=========<<<<"+i);
                Collections.swap(mDate, i, i + 1);// 改变实际的数据集
            }
        } else {
            for (int i = fromPosition; i > targetPosition; i--) {
                LoggerUtils.logger(i-1+"=========<<<<"+i);
                Collections.swap(mDate, i, i - 1);// 改变实际的数据集
            }
        }
        mAdapter.notifyItemMoved(fromPosition, targetPosition);

        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
// 获取当前删除的位置
        int position = viewHolder.getAdapterPosition();
        mDate.remove(position);

        // adapter 更新notify当前位置删除
        mAdapter.notifyItemRemoved(position);
    }

    /**
     * 拖动选择状态改变回调
     */
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
