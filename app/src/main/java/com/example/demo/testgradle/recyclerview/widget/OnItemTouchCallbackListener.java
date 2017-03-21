package com.example.demo.testgradle.recyclerview.widget;

import android.support.v7.widget.RecyclerView;

/**
 * Created by guilianghuang on 2017/3/22.
 */

interface OnItemTouchCallbackListener {
    boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target);

    void onSwiped(RecyclerView.ViewHolder viewHolder, int direction);
}
