package com.example.guilianghuang.testgradle.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.guilianghuang.testgradle.R;

/**
 *
 * Created by guilianghuang on 2017/3/11.
 */

public class AlertDialog extends Dialog {


    private final AlertController mAlert;

    protected AlertDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);

        mAlert = new AlertController(this, getWindow());
    }

    public void setText(int i, CharSequence charSequence) {
        mAlert.setText(i, charSequence);

    }

    public void setClickListener(int i, View.OnClickListener onClickListener) {
        mAlert.setClickListener(i, onClickListener);

    }

    public static class Builder {

        private AlertController.AlertParams P = null;

        public Builder(Context context) {
            this(context, R.style.dialog);
        }

        public Builder(@NonNull Context context, @StyleRes int themeResId) {
            P = new AlertController.AlertParams(context, themeResId);
        }


        public Builder setContentView(View view) {
            P.mView = view;
            P.mViewLayoutId = 0;
            return this;

        }

        public Builder setContentView(@LayoutRes int layoutId) {
            P.mView = null;
            P.mViewLayoutId = layoutId;
            return this;

        }

        public Builder setOnCancelListener(OnCancelListener onCancelListener) {
            P.mOnCancelListener = onCancelListener;
            return this;
        }

        /**
         * Sets the callback that will be called when the dialog is dismissed for any reason.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setOnDismissListener(OnDismissListener onDismissListener) {
            P.mOnDismissListener = onDismissListener;
            return this;
        }

        /**
         * Sets the callback that will be called if a key is dispatched to the dialog.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setOnKeyListener(OnKeyListener onKeyListener) {
            P.mOnKeyListener = onKeyListener;
            return this;
        }

        public Builder setText(@IdRes int resId, String textStr) {
            P.mTextArray.put(resId, textStr);
            return this;
        }

        public Builder setClickListener(@IdRes int resId, View.OnClickListener listener) {
            P.mClickListenerSparseArray.put(resId, listener);
            return this;
        }


        public Builder fullScreen() {
            P.mWidth = ViewGroup.LayoutParams.MATCH_PARENT;
            return this;
        }


        public Builder setSize(int width,int height) {
            P.mWidth=width;
            P.mHeight = height;
            return this;
        }

        public Builder setBottom() {
            P.mGravity= Gravity.BOTTOM;
            return this;
        }

        /**
         * Creates an {@link android.support.v7.app.AlertDialog} with the arguments supplied to this
         * builder.
         * <p>
         * Calling this method does not display the dialog. If no additional
         * processing is needed, {@link #show()} may be called instead to both
         * create and display the dialog.
         */
        private AlertDialog create() {
            // We can't use Dialog's 3-arg constructor with the createThemeContextWrapper param,
            // so we always have to re-set the theme
            AlertDialog dialog = new AlertDialog(P.mContext, P.mThemeId);
            P.apply(dialog.mAlert);
            dialog.setCancelable(P.mCancelable);
            if (P.mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }
            dialog.setOnCancelListener(P.mOnCancelListener);
            dialog.setOnDismissListener(P.mOnDismissListener);
            if (P.mOnKeyListener != null) {
                dialog.setOnKeyListener(P.mOnKeyListener);
            }
            return dialog;
        }

        /**
         * Creates an {@link android.support.v7.app.AlertDialog} with the arguments supplied to this
         * builder and immediately displays the dialog.
         * <p>
         * Calling this method is functionally identical to:
         * <pre>
         *     AlertDialog dialog = builder.create();
         *     dialog.show();
         * </pre>
         */
        public AlertDialog show() {
            final AlertDialog dialog = create();
            dialog.show();
            return dialog;
        }
    }

}

