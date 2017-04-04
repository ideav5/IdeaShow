package com.example.demo.testgradle.skin;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guilianghuang on 2017/4/4.
 */

public class SkinManager {
    private static SkinManager mInstance;
    private SkinResources mSkinResources;
    private Context mContext;

    private Map<ISkinChangeListener, List<SkinView>> mSkinViews = new HashMap<>();
    // 把View交给它管理

    private SkinManager() {
    }

    /**
     * 初始化 一般都在Application中配置
     * @param context
     */
    public void init(Context context) {
        this.mContext = context.getApplicationContext();
        String skinPath = SkinPreUtils.getInstance(mContext).getSkinPath();

        if (TextUtils.isEmpty(skinPath)) {
            return;
        }

        File skinFile = new File(skinPath);
        if (!skinFile.exists()) {
            clearSkinInfo();
            return;
        }

        initSkinResource(skinPath);
    }

    static {
        mInstance = new SkinManager();
    }

    public static SkinManager getInstance() {
        return mInstance;
    }


    /**
     * 加载皮肤
     *
     * @param path 当前皮肤的路径
     */
    public int loadSkin(String path) {
        String currentSkinPath = SkinPreUtils.getInstance(mContext).getSkinPath();
        if (currentSkinPath.equals(path)) {
            return SkinConfig.SKIN_LOADED;
        }

        File skinFile = new File(path);
        if(!skinFile.exists()){
            return SkinConfig.SKIN_PATH_ERROR;
        }

        // 判断签名是否正确，后面增量更新再说
        initSkinResource(path);
        changeSkin(path);
        saveSkinInfo(path);
        // 加载成功
        return SkinConfig.SKIN_LOAD_SUCCESS;
    }

    /**
     * 切换皮肤
     *
     * @param path 当前皮肤的路径
     */
    private void changeSkin(String path) {
        for (ISkinChangeListener skinChangeListener : mSkinViews.keySet()) {
            List<SkinView> skinViews = mSkinViews.get(skinChangeListener);
            for (SkinView skinView : skinViews) {
                skinView.skin();
            }
            skinChangeListener.changeSkin(path);
        }
    }

    /**
     * 初始化皮肤的Resource
     *
     * @param path
     */
    private void initSkinResource(String path) {
        mSkinResources = new SkinResources(mContext, path);
    }

    /**
     * 保存当前皮肤的信息
     *
     * @param path 当前皮肤的路径
     */
    private void saveSkinInfo(String path) {
        SkinPreUtils.getInstance(mContext).saveSkinPath(path);
    }

    /**
     * 恢复默认皮肤
     */
    public void restoreDefault() {
        String currentSkinPath = SkinPreUtils.getInstance(mContext).getSkinPath();
        if (TextUtils.isEmpty(currentSkinPath)) {
            return;
        }

        String path = mContext.getPackageResourcePath();
        initSkinResource(path);
        changeSkin(path);
        clearSkinInfo();
    }

    /**
     * 清空皮肤信息
     */
    private void clearSkinInfo() {
        SkinPreUtils.getInstance(mContext).clearSkinInfo();
    }

    public SkinResources getSkinResources() {
        return mSkinResources;
    }

    /**
     * 注册监听回调
     */
    public void register(List<SkinView> skinViews, ISkinChangeListener skinChangeListener) {
        mSkinViews.put(skinChangeListener, skinViews);
    }

    public List<SkinView> getSkinViews(Activity activity) {
        return mSkinViews.get(activity);
    }

    public boolean isChangeSkin() {
        return SkinPreUtils.getInstance(mContext).needChangeSkin();
    }

    public void changeSkin(SkinView skinView) {
        skinView.skin();
    }

    /**
     * 移除回调，怕引起内存泄露
     */
    public void unregister(ISkinChangeListener skinChangeListener) {
        mSkinViews.remove(skinChangeListener);
    }

    public void registerSkinView(List<SkinView> skinViews, BaseSkinActivity baseSkinActivity) {


    }

    public boolean needChangeSkin() {
        return false;
    }
}
