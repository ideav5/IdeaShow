package com.example.guilianghuang.testgradle.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.IdRes;
import android.util.SparseArray;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

class AlertController {
    private AlertDialog mAlertDialog;
    private Window mWindow;
    public DialogViewHelper mViewHelper;


    public AlertController(AlertDialog alertDialog, Window window) {
        mAlertDialog = alertDialog;
        mWindow = window;

    }

    public AlertDialog getAlertDialog() {
        return mAlertDialog;
    }

    public Window getWindow() {
        return mWindow;
    }


    public <T extends View> T getView(@IdRes int resId) {
        if (mViewHelper == null) {
            return null;
        }
        return mViewHelper.findView(resId);
    }


    public void setText(int i, CharSequence charSequence) {
        if (mViewHelper != null) {
            mViewHelper.setText(i,charSequence);
        }

    }

    public void setClickListener(int i, View.OnClickListener onClickListener) {
        if (mViewHelper != null) {
            mViewHelper.setClickListener(i,onClickListener);
        }

    }

    /**
     * Created by guilianghuang on 2017/3/11.
     */

    public static class AlertParams {
        public Context mContext;
        public int mThemeId;
        public boolean mCancelable = true;
        public DialogInterface.OnCancelListener mOnCancelListener;//取消监听
        public DialogInterface.OnDismissListener mOnDismissListener;//消失监听
        public DialogInterface.OnKeyListener mOnKeyListener;
        public View mView;//布局的View
        public int mViewLayoutId;//布局的ID
        //存放字体的修改
        public SparseArray<CharSequence> mTextArray = new SparseArray<>();
        //存放点击事件
        public SparseArray<View.OnClickListener> mClickListenerSparseArray = new SparseArray<>();
        public int mWidth= ViewGroup.LayoutParams.MATCH_PARENT;
        public int mHeight= ViewGroup.LayoutParams.WRAP_CONTENT;

        public int mGravity= Gravity.CENTER;

        public AlertParams(Context context, int themeWrapper) {
            this.mContext = context;
            this.mThemeId = themeWrapper;
        }

        /**
         * 绑定和设置参数
         *
         * @param mAlert
         */
        public void apply(AlertController mAlert) {
            //设置布局      DialogViewHelper
            DialogViewHelper viewHelper = null;
            if (mViewLayoutId != 0) {
                viewHelper = new DialogViewHelper(mContext, mViewLayoutId);

            }
            if (mView != null) {
                viewHelper = new DialogViewHelper(mContext);
                viewHelper.setContentView(mView);

            }
            if (viewHelper == null) {
                throw new IllegalArgumentException("请设置设置布局 setContentView");
            }
            mAlert.getAlertDialog().setContentView(viewHelper.getContentView());
            mAlert.setViewHelper(viewHelper);
            //设置文本
            int textsize = mTextArray.size();
            for (int i = 0; i < textsize; i++) {
                viewHelper.setText(mTextArray.keyAt(i), mTextArray.valueAt(i));

            }//设置点击事件
            int clicksize = mClickListenerSparseArray.size();
            for (int i = 0; i < clicksize; i++) {
                viewHelper.setClickListener(mClickListenerSparseArray.keyAt(i), mClickListenerSparseArray.valueAt(i));

            }
            //设置显示






            Window window = mAlert.getWindow();

            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width=mWidth;
            attributes.height=mHeight;
            attributes.gravity=mGravity;
         window.setAttributes(attributes);

        }
    }

    private void setViewHelper(DialogViewHelper viewHelper) {
        mViewHelper=viewHelper;

    }

}
