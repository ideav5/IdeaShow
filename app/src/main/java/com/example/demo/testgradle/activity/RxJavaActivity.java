package com.example.demo.testgradle.activity;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo.testgradle.R;
import com.example.demo.testgradle.bean.ListViewBean;
import com.example.demo.testgradle.http.base.HttpMethods;
import com.example.demo.testgradle.http.base.exception.ApiException;
import com.example.demo.testgradle.http.base.net.MyObserver;
import com.example.demo.testgradle.navigationbar.DefaultNavigationBar;
import com.example.demo.testgradle.util.LoggerUtils;
import com.example.demo.testgradle.util.ToasUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class RxJavaActivity extends AppCompatActivity {

    @BindView(R.id.tv_show_time)
    TextView mTvShowTime;
    private Subscriber<Long> mSubscriber;
    private Subscription mSubscription;

    int a=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);
        ButterKnife.bind(this);
        new DefaultNavigationBar.Builder(this).setTitle("RxJava").builder();

         List list = Arrays.asList("one", "two", "three", "four", "five");
        mSubscriber = new Subscriber<Long>() {
            @Override
            public void onCompleted() {
                Toast.makeText(RxJavaActivity.this, "Complete", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Long l) {
                int i = l.intValue();
//                        if (i == list.size()) {
//                            this.unsubscribe();
//                            onCompleted();
//                        }
                mTvShowTime.setText("时间:  " + l + "  array" );
            }
        };

//        RxView.clicks(findViewById(R.id.btn_throttle))
//                .throttleFirst(1, TimeUnit.SECONDS)
//                .subscribe(aVoid -> {
//                    System.out.println("click");
//                });


    }

    boolean isSend = true;

    @OnClick({R.id.btn_start, R.id.btn_rx1, R.id.btn_rx2, R.id.btn_rx3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                mSubscription = Observable.interval(2, 2, TimeUnit.SECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(mSubscriber);
                break;
            case R.id.btn_rx1:
                Map<String, String> mapParms = new HashMap<String, String>();
                mapParms.put("longitude", "113.953764");
                mapParms.put("latitude", "22.536882");
                mapParms.put("postType", "1");
                mapParms.put("sex", "2");
                mapParms.put("type", "11");
                mapParms.put("pageIndex", "1");
                mapParms.put("customerPlace", "深圳市-南山区");
                mapParms.put("eAge", "100");
                mapParms.put("pageItemCount", "20");
                mapParms.put("sAge", "0");
                mapParms.put("customerId", "");
                mapParms.put("token", "");
                mapParms.put("tokenCustomerId", "");
                mapParms.put("job", "");
                mapParms.put("chatTheme", "");
                Subscription subscribe = HttpMethods.getInstance().getListDate(mapParms).subscribe(new MyObserver<List<ListViewBean>>() {
                    @Override
                    protected void onError(ApiException ex) {
                        LoggerUtils.logger(ex.getCode() + "===========ApiException=====" + ex.getDisplayMessage());

                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        LoggerUtils.logger("===========onStart=====" + isSend);
                        if (isSend) {
                            this.unsubscribe();
                            isSend = false;
                        } else {
                            isSend = true;

                        }


                    }

                    @Override
                    public void onCompleted() {
                        LoggerUtils.logger("===========onCompleted=====");
                    }

                    @Override
                    public void onNext(List<ListViewBean> listViewBeen) {
                        LoggerUtils.logger("=====1======onNext=====");
                        SystemClock.sleep(1000);
                        LoggerUtils.logger("====2=======onNext=====");
                        mTvShowTime.setText(SystemClock.currentThreadTimeMillis()+listViewBeen.toString());
                    }
                });
                break;
            case R.id.btn_rx2:
//                mSubscriber.
                Observable.just(a,1,3).subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        ToasUtils.toast(RxJavaActivity.this,"wo 改变了"+integer);
                    }
                });
                break;
            case R.id.btn_rx3:
                a++;
                break;
        }
    }
}
