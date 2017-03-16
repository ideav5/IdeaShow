package com.example.demo.testgradle.di.component;

import android.app.Application;

import com.example.demo.testgradle.di.module.AppModule;

import javax.inject.Scope;
import javax.inject.Singleton;

import dagger.Component;
import dagger.Provides;

/**
 * Created by guilianghuang on 2017/3/16.
 */


@Component(modules = AppModule.class)
@Singleton
public interface AppComponent {

//    @Provides
//    @Singleton
    Application provideApp();

}
