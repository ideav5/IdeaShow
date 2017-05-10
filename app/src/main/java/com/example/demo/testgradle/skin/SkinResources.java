package com.example.demo.testgradle.skin;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import java.lang.reflect.Method;

/**
 *
 * Created by guilianghuang on 2017/4/4.
 */

public class SkinResources {

    private Resources mSkinResources;
    private  String mPackageName;

    public SkinResources(Context context, String path) {


        try {
            Resources superRes = context.getResources();
            // 创建AssetManager，但是不能直接new所以只能通过反射
            AssetManager assetManager = AssetManager.class.newInstance();
            // 反射获取addAssetPath方法
            Method addAssetPathMethod = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
            // 皮肤包的路径:  本地sdcard/plugin.skin
//            String skinPath = Environment.getExternalStorageDirectory().getAbsoluteFile()+ File.separator+"plugin.skin";
            // 反射调用addAssetPath方法
            addAssetPathMethod.invoke(assetManager, path);
            // 创建皮肤的Resources对象
            mSkinResources = new Resources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());
            // 通过资源名称,类型，包名获取Id

            mPackageName = context.getPackageName();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Drawable getDrawableByName(String resName) {
        try {
            int resId = mSkinResources.getIdentifier(resName, "drawable", mPackageName);
            return mSkinResources.getDrawable(resId);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }

    public ColorStateList getColorByName(String resName) {
        try {
            int resId = mSkinResources.getIdentifier(resName, "color", mPackageName);

            ColorStateList colorStateList = mSkinResources.getColorStateList(resId);
            return colorStateList;
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }
}
