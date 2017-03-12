package com.example.guilianghuang.testgradle.recyclerview.base;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

/**
 * Created by guilianghuang on 2017/2/27.
 */

public class ViewHolder extends RecyclerView.ViewHolder {

    SparseArray<View> mSparseArray;

    public ViewHolder(View itemView) {
        super(itemView);
        if (mSparseArray == null) {
            mSparseArray = new SparseArray<>();
        }

    }

    public <T extends View> T getView(@IdRes int id) {
        View view = mSparseArray.get(id);

        if (view != null) {
            return (T) view;
        } else {

            View viewById = this.itemView.findViewById(id);
            mSparseArray.put(id, viewById);
            return (T) viewById;

        }


    }


    public ViewHolder setText(@IdRes int id,String text){

        TextView textView = getView(id);
        if (textView!=null&& !TextUtils.isEmpty(text)) {
            textView.setText(text);
        }
        return this;
    }



}
