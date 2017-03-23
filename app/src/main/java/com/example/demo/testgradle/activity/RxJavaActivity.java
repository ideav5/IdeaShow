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
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RxJavaActivity extends AppCompatActivity {

    @BindView(R.id.tv_show_time)
    TextView mTvShowTime;
    private Subscriber<Long> mSubscriber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);
        ButterKnife.bind(this);
        new DefaultNavigationBar.Builder(this).setTitle("RxJava").builder();



        final List list = Arrays.asList("one", "two", "three", "four", "five");
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
                if (i == list.size()) {
                    this.unsubscribe();
                    onCompleted();
                }
                mTvShowTime.setText("时间:  " + l + "  array" + list.get(i));
            }
        };


//        RxView.clicks(findViewById(R.id.btn_throttle))
//                .throttleFirst(1, TimeUnit.SECONDS)
//                .subscribe(aVoid -> {
//                    System.out.println("click");
//                });
    }

    @OnClick({R.id.btn_start, R.id.btn_rx1, R.id.btn_rx2, R.id.btn_rx3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                Observable.interval(2, 2, TimeUnit.SECONDS)
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
                HttpMethods.getInstance().getListDate(mapParms).subscribe(new MyObserver<List<ListViewBean>>() {
                    @Override
                    protected void onError(ApiException ex) {
                        LoggerUtils.logger(ex.getCode() + "===========ApiException=====" + ex.getDisplayMessage());

                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        LoggerUtils.logger("===========onStart=====");
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
                        mTvShowTime.setText(listViewBeen.toString());
                    }
                });
                break;
            case R.id.btn_rx2:
                break;
            case R.id.btn_rx3:
                break;
        }
    }
}
