package com.example.guilianghuang.testgradle.recyclerview.base;

import android.support.annotation.IdRes;

/**
 * Created by guilianghuang on 2017/2/27.
 */

public interface MultiTypeSupport<T> {

    public int getLayoutId(T itemData, int position);
}
