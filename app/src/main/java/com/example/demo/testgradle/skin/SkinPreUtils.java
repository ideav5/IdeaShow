package com.example.demo.testgradle.skin;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * Created by guilianghuang on 2017/4/4.
 */

public class SkinPreUtils {
    static  SkinPreUtils mSkinPreUtils;

    static {
        mSkinPreUtils=new SkinPreUtils();
    }

    private static SharedPreferences sSharedPreferences;

    public static SkinPreUtils getInstance(Context context) {
        sSharedPreferences = context.getApplicationContext()
                .getSharedPreferences(SkinConfig.SKIN_CHANGE, Context.MODE_PRIVATE);
        return mSkinPreUtils;
    }

    public String getSkinPath() {
      return   sSharedPreferences.getString(SkinConfig.SKIN_CURRENT_PATH, "");
    }

    public void saveSkinPath(String path) {
        sSharedPreferences.edit().putString(SkinConfig.SKIN_CURRENT_PATH, path).apply();
    }

    public boolean needChangeSkin() {
        return !TextUtils.isEmpty(getSkinPath());
    }

    public void clearSkinInfo() {
        sSharedPreferences.edit().clear().apply();
    }
}
