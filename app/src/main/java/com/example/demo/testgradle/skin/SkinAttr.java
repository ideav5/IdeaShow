package com.example.demo.testgradle.skin;

import android.view.View;

/**
 * Created by guilianghuang on 2017/4/4.
 */

public class SkinAttr {

    private  String mResName;
    private  SkinType mType;


    public void skin(View view) {
        mType.skin(view, mResName);
    }

    public SkinAttr(String resName, SkinType type) {
        mResName = resName;
        mType = type;
    }
}
