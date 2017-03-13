package com.example.guilianghuang.testgradle.recyclerview.base;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by guilianghuang on 2017/2/27.
 */

public abstract class BaseRecyclerViewAdapter<DATA> extends RecyclerView.Adapter<ViewHolder> {


    protected Context mContext;
    protected List<DATA> mListData;
    protected int layoutId;
    private ItemClickListener mItemClickListener;
    private ItemLongClickListener mItemLongClickListener;
    private LayoutInflater mLayoutInflater;
    private MultiTypeSupport mMultiTypeSupport;

    public BaseRecyclerViewAdapter(Context context, List<DATA> listData, @LayoutRes int layoutId) {
        mContext = context;
        mListData = listData;
        this.layoutId = layoutId;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    public BaseRecyclerViewAdapter(Context context, List<DATA> listData,MultiTypeSupport<DATA> multiSupport) {
        this(context, listData, -1);
        mMultiTypeSupport = multiSupport;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mMultiTypeSupport != null) {
            layoutId=viewType;
        }
        View inflate = mLayoutInflater.inflate(layoutId, parent, false);
        return new ViewHolder(inflate);
    }


    @Override
    public int getItemViewType(int position) {

        if (mMultiTypeSupport != null) {
            return mMultiTypeSupport.getLayoutId(mListData.get(position), position);
        }

        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        if (mItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(position);
                }
            });

        }
        if (mItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                   return mItemLongClickListener.onItemLongClick(position);
                }
            });

        }
        DATA data = mListData.get(position);
        convert(holder,data);

    }

    public abstract void convert(ViewHolder holder, DATA data);

    @Override
    public int getItemCount() {
        return mListData.size();
    }


    public void setOnItemClickListener(ItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    public void setOnItemLongClickListener(ItemLongClickListener itemLongClickListener) {
        this.mItemLongClickListener = itemLongClickListener;
    }
}
