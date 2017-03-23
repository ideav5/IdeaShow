package com.example.demo.testgradle.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo.testgradle.R;

import java.util.Arrays;
import java.util.List;
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
              mTvShowTime.setText("时间:  "+l+"  array"+list.get(i));
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
                break;
            case R.id.btn_rx2:
                break;
            case R.id.btn_rx3:
                break;
        }
    }
}
