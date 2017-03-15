package com.example.demo.testgradle.navigationbar;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.example.demo.testgradle.R;

/**
 * Created by idea on 2017/3/15.
 */

public class DefaultNavigationBar extends AbsNavigationBar<DefaultNavigationBar.Builder.DefaultNavigationParams> {

    private DefaultNavigationBar(Builder.DefaultNavigationParams params) {
        super(params);
    }

    @Override
    public int bindLayoutId() {
        return R.layout.title_bar;
    }

    @Override
    public void applyView() {
        setText(R.id.title, getParams().mTitle);
        setClickListener(R.id.back, getParams().mBackListener);
        setClickListener(R.id.right_text, getParams().mRightListener);
        setVisibility(R.id.back, getParams().mVisiBack);
    }

    public static class Builder extends AbsNavigationBar.Builder {

        private DefaultNavigationParams P;

        public Builder(Context context, ViewGroup parent) {
            super(context, parent);
            P = new DefaultNavigationParams(context, parent);

        }

        public Builder(Context context) {
            this(context, null);
        }


        public Builder setTitle(String title) {
            P.mTitle = title;

            return this;
        }

        public Builder setVisiBack(boolean isvisiable) {
            P.mVisiBack = isvisiable;
            return this;
        }

        public Builder setBackListener(View.OnClickListener backListener) {

            P.mBackListener = backListener;

            return this;
        }

        public Builder setRightClickListener(View.OnClickListener rightClickListener) {

            P.mRightListener = rightClickListener;

            return this;
        }


        @Override
        public DefaultNavigationBar builder() {
            DefaultNavigationBar navigationBar = new DefaultNavigationBar(P);
            return navigationBar;
        }


        public static class DefaultNavigationParams extends AbsNavigationParams {
            public String mTitle;
            public View.OnClickListener mBackListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Activity activity = (Activity) mContext;
                    activity.finish();
                }
            };
            public View.OnClickListener mRightListener;
            public boolean mVisiBack = true;

            public DefaultNavigationParams(Context context, ViewGroup parent) {
                super(context, parent);
            }
        }
    }


}
