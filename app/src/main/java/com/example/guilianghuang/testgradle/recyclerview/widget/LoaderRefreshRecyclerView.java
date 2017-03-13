package com.example.guilianghuang.testgradle.recyclerview.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by idea on 2017/3/13.
 */

public class LoaderRefreshRecyclerView extends RefreshRecyclerView {
    // 上拉刷新的辅助类
    private LoadViewCreator mLoadviewCreator;
    // 上拉刷新头部的高度
    private int mLoadViewHeight = 0;
    // 上拉刷新的头部View
    private View mLoadView;
    // 手指按下的Y位置
    private int mFingerDownY;
    // 当前是否正在拖动
    private boolean mCurrentDrag = false;
    // 当前的状态
    private int mCurrentLoadStatus;
    // 默认状态
    public int LOAD_STATUS_NORMAL = 0x0011;
    // 下拉刷新状态
    public static int LOAD_STATUS_PULL_DOWN_REFRESH = 0x0022;
    // 松开刷新状态
    public static int LOAD_STATUS_LOOSEN_LOADING = 0x0033;
    // 正在刷新状态
    public int LOAD_STATUS_LOADING = 0x0044;

    public LoaderRefreshRecyclerView(Context context) {
        super(context);
    }

    public LoaderRefreshRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LoaderRefreshRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void addLoadCreator(LoadViewCreator loadViewCreator) {
        if (loadViewCreator != null) {
            mLoadviewCreator = loadViewCreator;
            addLoadView();
        }
    }

    private void addLoadView() {
        RecyclerView.Adapter adapter = getAdapter();
        if (adapter != null && mLoadviewCreator != null) {
            View loadView = mLoadviewCreator.getLoadView(getContext(), this);
            if (loadView != null) {
                mLoadView = loadView;
                addFooterView(loadView);
            }
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        addLoadView();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mFingerDownY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                if (mCurrentDrag) {
                    restoreLoadView();
                }
                break;
        }

        return super.dispatchTouchEvent(ev);
    }

    private void restoreLoadView() {
        if (mLoadView == null) {
            return;
        }
        //恢复状态
        MarginLayoutParams layoutParams = (MarginLayoutParams) mLoadView.getLayoutParams();
        int bottomMargin = layoutParams.bottomMargin;
        int finalBottom = -mLoadViewHeight + 1;
        //用位置判断当前的状态
        if (mCurrentLoadStatus == LOAD_STATUS_LOOSEN_LOADING) {
            finalBottom = 0;
            mCurrentLoadStatus = LOAD_STATUS_LOADING;
            if (mLoadviewCreator != null) {
                mLoadviewCreator.onLoading();
            }
            if (mListener != null) {
                mListener.onLoad();
            }

        }

        int duringTime = bottomMargin - finalBottom;
        ValueAnimator animator = ValueAnimator.ofFloat(bottomMargin, finalBottom).setDuration(Math.abs(duringTime));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                setLoadViewMarginBottom((int) value);
            }
        });
        animator.start();
        mCurrentDrag = false;

    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (canScrollDown() || mLoadviewCreator == null || mLoadView == null || mCurrentLoadStatus == LOAD_STATUS_LOADING) {
                    return super.onTouchEvent(e);
                }
                // 解决上拉加载更多自动滚动问题
                if (mCurrentDrag) {
                    scrollToPosition(getAdapter().getItemCount() - 1);
                }

                // 获取手指触摸拖拽的距离
                int distanceY = (int) ((e.getRawY() - mFingerDownY) * mDragIndex);//小于0
                if (distanceY < 0) {
                    int marginBottom = -distanceY - mLoadViewHeight;
                    setLoadViewMarginBottom(marginBottom);
                    updateLoadStatus(-distanceY);
                    mCurrentDrag = true;
                    return true;
                }
                break;
        }

        return super.onTouchEvent(e);
    }

    private void updateLoadStatus(int distanceY) {
        if (distanceY <= 0) {
            mCurrentLoadStatus = LOAD_STATUS_NORMAL;
        } else if (distanceY < mLoadViewHeight) {
            mCurrentLoadStatus = LOAD_STATUS_PULL_DOWN_REFRESH;
        } else {
            mCurrentLoadStatus = LOAD_STATUS_LOOSEN_LOADING;
        }

        if (mLoadviewCreator != null) {
            mLoadviewCreator.onPull(distanceY, mLoadViewHeight, mCurrentLoadStatus);
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (mLoadView != null && mLoadViewHeight <= 0) {
            mLoadViewHeight = mLoadView.getMeasuredHeight();
            if (mLoadViewHeight > 0) {
                // 隐藏尾部刷新的View  marginTop  多留出1px防止无法判断是不是滚动到头部问题
                setLoadViewMarginBottom(-mLoadViewHeight + 1);
            }
        }
    }

    private void setLoadViewMarginBottom(int marginBottom) {
        MarginLayoutParams layoutParams = (MarginLayoutParams) mLoadView.getLayoutParams();
        if (marginBottom < -mLoadViewHeight + 1) {
            marginBottom = -mLoadViewHeight + 1;
        }
        layoutParams.topMargin = marginBottom;
        mLoadView.setLayoutParams(layoutParams);
    }


    /**
     * @return Whether it is possible for the child view of this layout to
     * scroll up. Override this if the child view is a custom view.
     * 判断是不是滚动到了最顶部，这个是从SwipeRefreshLayout里面copy过来的源代码
     */
    public boolean canScrollDown() {
        return ViewCompat.canScrollVertically(this, 1);
    }

    /**
     * 停止刷新
     */
    public void onStopLoad() {
        mCurrentLoadStatus = LOAD_STATUS_NORMAL;
        restoreLoadView();
        if (mLoadviewCreator != null) {
            mLoadviewCreator.onStopLoad();
        }
    }

    // 处理刷新回调监听
    private OnLoadMoreListener mListener;

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        this.mListener = listener;
    }

    public interface OnLoadMoreListener {
        void onLoad();
    }

}
