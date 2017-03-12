package com.example.guilianghuang.testgradle.dialog;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 *
 * Created by guilianghuang on 2017/3/11.
 */

class DialogViewHelper {

    private Context mContext;


    private SparseArray<WeakReference<View>> mViews = new SparseArray<>();
    private View mContentView;

    public DialogViewHelper(Context context, @LayoutRes int viewLayoutId) {
        this.mContext = context;
        mContentView = LayoutInflater.from(context).inflate(viewLayoutId, null);
    }

    public DialogViewHelper(Context context) {
        this.mContext = context;
    }

    public void setContentView(View view) {
        mContentView = view;
    }

    public void setText(int i, CharSequence charSequence) {
        TextView textView = findView(i);
        if (textView != null) {
            textView.setText(charSequence);
        }

    }

    public void setClickListener(int i, View.OnClickListener onClickListener) {
        View view = findView(i);
        if (view != null) {
            view.setOnClickListener(onClickListener);
        }

    }

    public  <T extends View> T findView(@IdRes int id) {

        WeakReference<View> viewWeakReference = mViews.get(id);
        if (viewWeakReference != null) {
            View view = viewWeakReference.get();
            return (T) view;
        }

        View viewById = getContentView().findViewById(id);
        if (viewById != null) {
            mViews.put(id, new WeakReference<View>(viewById));
        }

        return (T) viewById;
    }


    public View getContentView() {

        return mContentView;
    }


}
