package com.example.demo.testgradle.http;

import rx.Subscription;

/**
 * Created by idea on 2017/3/24.
 */

public interface RxActionManager<T> {

    void add(T tag, Subscription subscription);
    void remove(T tag);

    void cancel(T tag);

    void cancelAll();
}