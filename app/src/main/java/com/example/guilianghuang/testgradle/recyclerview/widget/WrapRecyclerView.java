package com.example.guilianghuang.testgradle.recyclerview.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.example.guilianghuang.testgradle.recyclerview.base.ItemClickListener;
import com.example.guilianghuang.testgradle.recyclerview.base.ItemLongClickListener;

/**
 * Created by guilianghuang on 2017/3/12.
 */

public class WrapRecyclerView extends RecyclerView {


    private Adapter mAdapter;
    private WrapRecyclerAdapter mWrapAdapter;

    public WrapRecyclerView(Context context) {
        super(context);
    }

    public WrapRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WrapRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {

        if (mAdapter != null) {
            mAdapter.unregisterAdapterDataObserver(mAdapterDataObserver);
            mAdapter = null;
        }
        mAdapter = adapter;
        if (adapter == null) {
            return;
        }
        if (adapter instanceof WrapRecyclerAdapter) {
            mWrapAdapter = (WrapRecyclerAdapter) adapter;
        } else {
            mWrapAdapter = new WrapRecyclerAdapter(adapter);
        }
        mWrapAdapter.adjustSpanSize(this);
        super.setAdapter(mWrapAdapter);
        mAdapter.registerAdapterDataObserver(mAdapterDataObserver);

        if (mItemClickListener != null) {
            mWrapAdapter.setOnItemClickListener(mItemClickListener);
        }if (mLongClickListener != null) {
            mWrapAdapter.setOnLongClickListener(mLongClickListener);
        }


    }


    // 添加头部
    public void addHeaderView(View view) {
        // 如果没有Adapter那么就不添加，也可以选择抛异常提示
        // 让他必须先设置Adapter然后才能添加，这里是仿照ListView的处理方式
        if (mWrapAdapter != null) {
            mWrapAdapter.addHeaderView(view);
        }
    }

    // 添加底部
    public void addFooterView(View view) {
        if (mWrapAdapter != null) {
            mWrapAdapter.addFooterView(view);
        }
    }

    // 移除头部
    public void removeHeaderView(View view) {
        if (mWrapAdapter != null) {
            mWrapAdapter.removeHeaderView(view);
        }
    }

    // 移除底部
    public void removeFooterView(View view) {
        if (mWrapAdapter != null) {
            mWrapAdapter.removeFooterView(view);
        }
    }

    private AdapterDataObserver mAdapterDataObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            if (mAdapter==null)return;
            if (mWrapAdapter != mAdapter) {
                mWrapAdapter.notifyDataSetChanged();
            }

        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            if (mAdapter==null)return;
            if (mWrapAdapter != mAdapter) {
                mWrapAdapter.notifyItemRangeChanged(positionStart, itemCount);
            }
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            if (mAdapter==null)return;
            if (mWrapAdapter != mAdapter) {
                mWrapAdapter.notifyItemRangeChanged(positionStart, itemCount, payload);
            }
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            if (mAdapter==null)return;
            if (mWrapAdapter != mAdapter) {
                mWrapAdapter.notifyItemRangeInserted(positionStart, itemCount);
            }
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            if (mAdapter==null)return;
            if (mWrapAdapter != mAdapter) {
                mWrapAdapter.notifyItemRangeRemoved(positionStart, itemCount);
            }
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            if (mAdapter==null)return;
            if (mWrapAdapter != mAdapter) {
                mWrapAdapter.notifyItemMoved(fromPosition, toPosition);
            }
        }
    };

    /***************
     * 给条目设置点击和长按事件
     *********************/
    public ItemClickListener mItemClickListener;
    public ItemLongClickListener mLongClickListener;

    public void setOnItemClickListener(ItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    public void setOnLongClickListener(ItemLongClickListener longClickListener) {
        this.mLongClickListener = longClickListener;
    }


}
