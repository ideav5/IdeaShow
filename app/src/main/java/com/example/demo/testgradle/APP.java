package com.example.demo.testgradle;

import android.app.Application;

import com.example.demo.testgradle.di.component.AppComponent;
import com.example.demo.testgradle.di.component.DaggerAppComponent;
import com.example.demo.testgradle.di.module.AppModule;
import com.example.demo.testgradle.util.LoggerUtils;
import com.orhanobut.logger.Logger;

/**
 * Created by idea on 2017/3/13.
 */

public class APP extends Application {

    private Application mApplication;

    @Override
    public void onCreate() {
        super.onCreate();

        Logger.init();


        AppComponent appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();

        mApplication = appComponent.provideApp();
        LoggerUtils.logger(mApplication+"App");

    }
}
