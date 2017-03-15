package com.example.demo.testgradle.recyclerview.base;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * Created by guilianghuang on 2017/2/26.
 */

public class GridLayoutDecoration extends RecyclerView.ItemDecoration {


    private Drawable mDivider;
    private int mSpanCount;

    public GridLayoutDecoration(Context context, @DrawableRes int diviver, RecyclerView recyclerView) {
        mDivider = ContextCompat.getDrawable(context, diviver);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            mSpanCount = gridLayoutManager.getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager gridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            mSpanCount = gridLayoutManager.getSpanCount();
        } else {
            mSpanCount = 1;
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int childAdapterPosition = parent.getChildAdapterPosition(view);

        if (mSpanCount != 1) {
            if (childAdapterPosition % mSpanCount != mSpanCount-1) {//不是第一列
                outRect.right = mDivider.getIntrinsicWidth();
            }
        }
        outRect.bottom += mDivider.getIntrinsicHeight();
    }


    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();

        drawHorizontal(canvas, parent, childCount);
        if (mSpanCount != 1) {
            drawVertical(canvas, parent, childCount);
        }

    }

    /**
     * @param canvas
     * @param parent
     * @param childCount
     */
    private void drawVertical(Canvas canvas, RecyclerView parent, int childCount) {
        Rect rect = new Rect();
        for (int i = 0; i < childCount; i++) {
            View childAt = parent.getChildAt(i);
            rect.left = childAt.getRight();
            rect.right = rect.left + mDivider.getIntrinsicWidth();
            rect.top = childAt.getTop();
            rect.bottom = childAt.getHeight() + rect.top;
            mDivider.setBounds(rect);
            mDivider.draw(canvas);
        }
    }

    private void drawHorizontal(Canvas canvas, RecyclerView parent, int childCount) {
        int raw = childCount / mSpanCount;//行数
        if (childCount % mSpanCount == 0) {
            raw--;
        }
        for (int i = 0; i < childCount; i++) {
            View childAt = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) childAt.getLayoutParams();
            int left = childAt.getLeft() - layoutParams.leftMargin;
           int right = childAt.getRight() + layoutParams.rightMargin;
            if (i % mSpanCount != 0) {
                left -= mDivider.getIntrinsicWidth();
            }
            int top = childAt.getBottom();
            int bottom= top + mDivider.getIntrinsicHeight();
            if (i>=raw*mSpanCount) {//最后一行
                bottom=top;
            }
            mDivider.setBounds(left,top,right,bottom);
            mDivider.draw(canvas);

        }
    }
}
