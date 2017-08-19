package com.example.demo.testgradle.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.demo.testgradle.R;

import java.lang.ref.WeakReference;

/**
 * Created by GL on 2017/7/12.
 * 注:
 */

public class HeaderBehavior extends CoordinatorLayout.Behavior<View> {
    private static final String TAG = "HeaderBehavior";
    private WeakReference<View> mDependencyView;


    public HeaderBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
//        if (isDepend(dependency)) {
//            Log.i("HeaderBehavior", "isDeoendent : true");
//            mDependencyView = new WeakReference<View>(dependency);
//            return true;
//        }

//        Log.d(TAG, child+"LogAAAAAAA"+dependency );
        return dependency != null && dependency.getId() == R.id.rec_view;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY, boolean consumed) {
//        if (!consumed) {

            return true;
//        }else
//            return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    }


//    @Override
//    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY) {
//        return true;
//    }


    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
//        Log.d("HeaderBehavior",child+"-------"+target);
//                      child ---要处理的VIEW     target recycleView
//        Log.d(TAG,"transY:"+dyConsumed+"++++child.getTranslationY():"+child.getTranslationY()+"---->dy:"+dyUnconsumed);
        //dyConsumed   <0   向下

        boolean b = ViewCompat.canScrollVertically(target, 1);


//        boolean b1 = ViewCompat.canScrollVertically(target, -1);



//
//        if (dyConsumed < 0) {
//            child.setVisibility(View.GONE);
//        } else {
//            child.setVisibility(View.VISIBLE);
//        }

        Log.d(TAG, b+"Log.d(TAG," );

    }



    public boolean isDepend(View dependency) {
        return dependency != null && dependency.getId() == R.id.rec_view;
    }
}
