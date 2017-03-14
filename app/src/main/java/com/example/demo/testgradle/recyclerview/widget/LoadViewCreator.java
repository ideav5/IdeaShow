package com.example.demo.testgradle.recyclerview.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by idea on 2017/3/13.
 */

public abstract class LoadViewCreator {
    /**
     * 获取下拉刷新的View
     *
     * @param context 上下文
     * @param parent  RecyclerView
     */
    public abstract View getLoadView(Context context, ViewGroup parent);

    /**
     * 正在上拉
     * @param currentDragHeight   当前拖动的高度
     * @param refreshViewHeight  总的刷新高度
     * @param currentRefreshStatus 当前状态
     */
    public abstract void onPull(int currentDragHeight, int refreshViewHeight, int currentRefreshStatus);

    /**
     * 正在刷新中
     */
    public abstract void onLoading();

    /**
     * 停止刷新
     */
    public abstract void onStopLoad();


}
