package com.example.demo.testgradle.recyclerview.widget;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.example.demo.testgradle.recyclerview.base.ItemClickListener;
import com.example.demo.testgradle.recyclerview.base.ItemLongClickListener;

/**
 *
 * 在原来的Adapter上加上头 尾
 *
 * Created by guilianghuang on 2017/3/12.
 */

public class WrapRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    RecyclerView.Adapter mAdapter ;
    private  SparseArray<View> mFooterViews;
    private  SparseArray<View> mHeaderViews;
    private int HEADER_VIEWS_KEYS=1000000000;
    private int FOOTER_VIEWS_KEYS=2000000000;


    public WrapRecyclerAdapter(RecyclerView.Adapter adapter) {
        mAdapter = adapter;
        mHeaderViews = new SparseArray<>();
        mFooterViews = new SparseArray<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //先判断是否是头尾
        if (isHeaderViewType(viewType)) {
            return new RecyclerView.ViewHolder(mHeaderViews.get(viewType)) {
            };
        }
        if (isFooterViewType(viewType)) {
            return new RecyclerView.ViewHolder(mFooterViews.get(viewType)) {
            };
        }

        return mAdapter.createViewHolder(parent, viewType);
    }

    private boolean isFooterViewType(int viewType) {
        if (mFooterViews.indexOfKey(viewType) >=0) {
            return true;
        }
        return false;
    }

    private boolean isHeaderViewType(int viewType) {
        if (mHeaderViews.indexOfKey(viewType)>=0) {
            return true;
        }
        return false;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (isHeaderView(position) || isFooterView(position)) {
            return;
        }
        final int adapterPosition=position-mHeaderViews.size();

        mAdapter.onBindViewHolder(holder,adapterPosition);

        // 设置点击和长按事件
        if (mItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(adapterPosition);
                }
            });
        }
        if (mLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return mLongClickListener.onItemLongClick(adapterPosition);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mAdapter.getItemCount()+mHeaderViews.size()+mFooterViews.size();
    }


    @Override
    public int getItemViewType(int position) {
        if (isHeaderView(position)) {
          return  mHeaderViews.keyAt(position);
        }
        if (isFooterView(position)) {
//            mFooterViews.
            return mFooterViews.keyAt( position- mHeaderViews.size()-mAdapter.getItemCount());
        }
        position -= mHeaderViews.size();

        return mAdapter.getItemViewType(position);
    }


    private boolean isHeaderView(int position) {
        if (position<mHeaderViews.size()) {
            return true;
        }
        return false;

    }
      private boolean isFooterView(int position) {
          int headerSize = mHeaderViews.size();
          int itemCount = mAdapter.getItemCount();

          if (position>= headerSize+itemCount) {
            return true;

        }
        return false;

    }


    public void addHeaderView(View view) {
        mHeaderViews.put(HEADER_VIEWS_KEYS++,view);
        notifyDataSetChanged();
    }

    public void addFooterView(View view) {
        mFooterViews.put(FOOTER_VIEWS_KEYS++,view);
        notifyDataSetChanged();
    }

    public void removeHeaderView(View view) {
        int headerIndex = mHeaderViews.indexOfValue(view);
        if(headerIndex >0){
            mHeaderViews.removeAt(headerIndex);
            notifyDataSetChanged();
        }
    }

    public void removeFooterView(View view) {
        int footerIndex = mHeaderViews.indexOfValue(view);
        if(footerIndex >0){
            mHeaderViews.removeAt(footerIndex);
            notifyDataSetChanged();
        }
    }
    /**
     * 解决GridLayoutManager添加头部和底部不占用一行的问题
     *
     * @param recycler
     * @version 1.0
     */
    public void adjustSpanSize(RecyclerView recycler) {
        if (recycler.getLayoutManager() instanceof GridLayoutManager) {
            final GridLayoutManager layoutManager = (GridLayoutManager) recycler.getLayoutManager();
            layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    boolean isHeaderOrFooter =
                            isHeaderView(position) || isFooterView(position);
                    return isHeaderOrFooter ? layoutManager.getSpanCount() : 1;
                }
            });
        }
    }

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