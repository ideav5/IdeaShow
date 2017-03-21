package com.example.demo.testgradle.recyclerview;

import android.content.Context;

import com.example.demo.testgradle.R;
import com.example.demo.testgradle.recyclerview.base.BaseRecyclerViewAdapter;
import com.example.demo.testgradle.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by guilianghuang on 2017/2/25.
 */

public class ListAdapter extends BaseRecyclerViewAdapter<String> {
    public ListAdapter(Context context, List<String> listData) {
        super(context, listData, R.layout.item_list);
    }


    @Override
    public void convert(ViewHolder holder, String s) {
        holder.setText(R.id.item_tv,s);
    }


}
