package com.example.demo.testgradle;

import android.app.Application;

import com.orhanobut.logger.Logger;

/**
 * Created by idea on 2017/3/13.
 */

public class APP extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Logger.init();

    }
}
