package com.example.demo.testgradle.http.base.net;

import com.example.demo.testgradle.http.base.exception.ServerException;

import rx.functions.Func1;

/**
 * Created by idea on 2017/3/23.
 */

 public class RabServerResultFunc<T> implements Func1<RabHttpResponse<T>, T> {
    @Override
    public T call(RabHttpResponse<T> httpResult) {
        if (httpResult.getCode() != 0) {
            throw new ServerException(httpResult.getCode(), httpResult.getMessage());
        }
        return httpResult.getData();
    }
}
