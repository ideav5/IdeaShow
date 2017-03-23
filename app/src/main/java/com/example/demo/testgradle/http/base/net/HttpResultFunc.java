package com.example.demo.testgradle.http.base.net;

import com.example.demo.testgradle.http.base.exception.ExceptionEngine;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by idea on 2017/3/23.
 */

 public class HttpResultFunc<T> implements Func1<Throwable, Observable<T>> {
    @Override
    public Observable<T> call(Throwable throwable) {
        return Observable.error(ExceptionEngine.handleException(throwable));
    }
}