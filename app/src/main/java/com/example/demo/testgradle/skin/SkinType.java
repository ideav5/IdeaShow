package com.example.demo.testgradle.skin;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by guilianghuang on 2017/4/4.
 */

 enum  SkinType {
    TEXT_COLOR("textColor") {
        @Override
        public void skin(View view, String resName) {
            SkinResources skinRes = getSkinRes();
            ColorStateList colorByName = skinRes.getColorByName(resName);
            if (colorByName != null) {
                TextView tv= (TextView) view;
                tv.setTextColor(colorByName);
            }
        }
    },BACKGROUND("background") {
        @Override
        public void skin(View view, String resName) {
            SkinResources skinRes = getSkinRes();
            Drawable drawableByName = skinRes.getDrawableByName(resName);
            if (drawableByName != null) {
                ImageView imageView = (ImageView) view;
                imageView.setBackground(drawableByName);
            } else {
                ColorStateList colorByName = skinRes.getColorByName(resName);
                if (colorByName != null) {
                   view.setBackgroundColor(colorByName.getDefaultColor());
                }
            }



        }
    },SRC("src") {
        @Override
        public void skin(View view, String resName) {
            SkinResources skinRes = getSkinRes();
            Drawable drawableByName = skinRes.getDrawableByName(resName);
            if (drawableByName != null) {
                ImageView imageView = (ImageView) view;
                imageView.setImageDrawable(drawableByName);
            }

        }
    };

    private static SkinResources getSkinRes() {
        return SkinManager.getInstance().getSkinResources();
    }

    private  String mResName;

    SkinType(String resName) {
        this.mResName=resName;


    }

    public abstract void skin(View view, String resName) ;

    public String getResName() {

        return mResName;
    }
}
