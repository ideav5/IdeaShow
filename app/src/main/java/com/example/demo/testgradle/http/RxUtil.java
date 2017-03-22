package com.example.demo.testgradle.http;

import com.example.demo.testgradle.bean.UrlBean;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by idea on 2017/1/17.
 */

public class RxUtil {


    public  static String Host1="https://app.rablive.cn";
    public  static String HOST = "http://192.168.1.72:8080/folkcam-rablive-app2/";

    /**
     * 统一线程处理
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<T, T> rxSchedulerHelper() {    //compose简化线程
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    /**
     * 统一返回结果处理
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<UrlBean<T>, T> handleResult() {   //compose判断结果
        Observable.Transformer<UrlBean<T>, T> transformer = new Observable.Transformer<UrlBean<T>, T>() {
            @Override
            public Observable<T> call(Observable<UrlBean<T>> httpResponseObservable) {
                return httpResponseObservable.flatMap(new Func1<UrlBean<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(UrlBean<T> tGankHttpResponse) {
                        if (tGankHttpResponse.code.equals("0")) {
                            return createData(tGankHttpResponse.data);
                        } else {
                            return Observable.error(new ApiException(tGankHttpResponse.code,"服务器返回error"));
                        }
                    }
                });
            }
        };

        return transformer;
    }


    /**
     * 生成Observable
     * @param <T>
     * @return
     */
    public static <T> Observable<T> createData(final T t) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(t);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
