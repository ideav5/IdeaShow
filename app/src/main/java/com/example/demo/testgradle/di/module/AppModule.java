package com.example.demo.testgradle.di.module;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by guilianghuang on 2017/3/16.
 */


@Module
public class AppModule {

    private final Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application provideApplicationContext() {
        return application;
    }

//    @Provides
//    @Singleton
//    RetrofitHelper provideRetrofitHelper(ZhihuApis zhihuApiService, GankApis gankApiService, WeChatApis wechatApiService,
//                                         MyApis myApiService, GoldApis goldApiService, VtexApis vtexApiService) {
//        return new RetrofitHelper(zhihuApiService, gankApiService, wechatApiService,
//                myApiService, goldApiService, vtexApiService);
//    }
//
//    @Provides
//    @Singleton
//    RealmHelper provideRealmHelper() {
//        return new RealmHelper(application);
//    }

}
