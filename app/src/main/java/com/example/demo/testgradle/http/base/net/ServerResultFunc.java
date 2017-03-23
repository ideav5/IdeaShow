package com.example.demo.testgradle.http.base.net;

import com.example.demo.testgradle.http.base.exception.ServerException;

import rx.functions.Func1;

/**
 * Created by idea on 2017/3/23.
 */

 public class ServerResultFunc<T> implements Func1<VideoHttpResponse<T>, T> {
    @Override
    public T call(VideoHttpResponse<T> httpResult) {
        if (httpResult.getCode() != 200) {
            throw new ServerException(httpResult.getCode(), httpResult.getMsg());
        }
        return httpResult.getRet();
    }
}
