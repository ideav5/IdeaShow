package com.example.demo.testgradle.navigationbar;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by idea on 2017/3/15.
 */

public abstract class AbsNavigationBar<P extends AbsNavigationBar.Builder.AbsNavigationParams> implements INavigationBar {


    private P mParams;
    private View mNavigationView;

    public AbsNavigationBar(P params) {
        mParams = params;
        createNavigationBar();
    }

    public P getParams() {
        return mParams;
    }


    public <T extends View> T findView(@IdRes int resId) {
        return (T) mNavigationView.findViewById(resId);
    }

    public void setText(@IdRes int resId, String str) {
        TextView tv = findView(resId);
        if (tv != null && str != null) {
            tv.setText(str);
        }
    }

    public void setVisibility(@IdRes int resId, boolean isvisiable) {

        findView(resId).setVisibility(isvisiable ? View.VISIBLE : View.GONE);
    }

    public void setClickListener(@IdRes int resId, View.OnClickListener listener) {
        View view = findView(resId);
        if (view != null && listener != null) {
            view.setOnClickListener(listener);
        }
    }

    private void createNavigationBar() {
        // 1. 创建View

        if (mParams.mParent == null) {
            // 获取activity的根布局，View源码
//            ViewGroup activityRoot = (ViewGroup) ((Activity) (mParams.mContext)).findViewById(android.R.id.content);
            ViewGroup activityRoot = (ViewGroup) ((Activity) (mParams.mContext)).getWindow().getDecorView();
            mParams.mParent = (ViewGroup) activityRoot.getChildAt(0);
            Log.e("TAG", mParams.mParent + "");
        }

        // 处理Activity的源码，后面再去看


        if (mParams.mParent == null) {
            return;
        }

        mNavigationView = LayoutInflater.from(mParams.mContext).inflate(bindLayoutId(), mParams.mParent, false);// 插件换肤

        // 2.添加
        mParams.mParent.addView(mNavigationView, 0);

        applyView();
    }

    public abstract static class Builder {
        public Builder(Context context, ViewGroup parent) {
        }

        public abstract AbsNavigationBar builder();

        public static class AbsNavigationParams {
            public Context mContext;
            public ViewGroup mParent;

            public AbsNavigationParams(Context context, ViewGroup parent) {
                this.mContext = context;
                this.mParent = parent;
            }
        }
    }


}
