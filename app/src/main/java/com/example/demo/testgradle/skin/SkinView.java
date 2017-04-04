package com.example.demo.testgradle.skin;

import android.view.View;

import java.util.List;

/**
 * Created by guilianghuang on 2017/4/4.
 */

public class SkinView {
    private  List<SkinAttr> mSkinAttrs;
    private  View mView;

    public SkinView(View view, List<SkinAttr> skinAttrs) {
        this.mView = view;
        this.mSkinAttrs = skinAttrs;

    }

    public void skin() {
        for (SkinAttr skinAttr : mSkinAttrs) {

            skinAttr.skin(mView);
        }

    }
}
