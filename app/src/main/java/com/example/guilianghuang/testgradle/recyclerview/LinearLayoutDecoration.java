package com.example.guilianghuang.testgradle.recyclerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by guilianghuang on 2017/2/26.
 */

public class LinearLayoutDecoration  extends RecyclerView.ItemDecoration{


    private Context mContext;
    private Drawable mDiviver;

    public LinearLayoutDecoration(Context context,@DrawableRes int diviver) {
        mDiviver = ContextCompat.getDrawable(context,diviver);
        mContext = context;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int childAdapterPosition = parent.getChildAdapterPosition(view);
        if (childAdapterPosition != 0) {//给非第0个留出位置
            outRect.top=mDiviver.getIntrinsicHeight();
        }
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();

        Rect rect = new Rect();
        rect.left=parent.getPaddingLeft();
        rect.right=parent.getWidth()-parent.getPaddingRight();
        for (int i = 1; i < childCount; i++) {
            rect.bottom=parent.getChildAt(i).getTop();
            rect.top = rect.bottom - mDiviver.getIntrinsicHeight();
            mDiviver.setBounds(rect);
            mDiviver.draw(canvas);
        }

    }
}
