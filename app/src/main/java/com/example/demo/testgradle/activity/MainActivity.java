package com.example.demo.testgradle.activity;

import android.Manifest;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.demo.testgradle.R;
import com.example.demo.testgradle.dialog.AlertDialog;
import com.example.demo.testgradle.heart.PeriscopeLayout;
import com.example.demo.testgradle.navigationbar.DefaultNavigationBar;
import com.example.demo.testgradle.rx.RxBus;
import com.example.demo.testgradle.util.LoggerUtils;
import com.example.demo.testgradle.util.ToasUtils;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.tbruyelle.rxpermissions.Permission;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.tencent.bugly.beta.Beta;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.base_btn)
    Button mBaseBtn;
    @BindView(R.id.refresh_load_btn)
    Button mRefreshLoadBtn;
    @BindView(R.id.btn_dialog)
    Button mBtnDialog;
    @BindView(R.id.btn_tinker)
    Button mBtnTinker;
    @BindView(R.id.activity_main)
    LinearLayout mActivityMain;
    @BindView(R.id.btn_rxjava)
    View mBtnRxjava;

    @BindView(R.id.ppl)
    PeriscopeLayout mPPL;

    @BindView(R.id.btn_anim)
    Button mBtnAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        DefaultNavigationBar defaultNavigationBar = new DefaultNavigationBar.Builder(this).setTitle("主页").setVisiBack(false).builder();
        RxBus.getDefault().toObservable(String.class).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                LoggerUtils.logger("========onCompleted========");
            }

            @Override
            public void onError(Throwable e) {
                LoggerUtils.logger(e.toString());
            }

            @Override
            public void onNext(String s) {
                LoggerUtils.logger(s);
                mBtnDialog.performClick();
            }


        });

        TextView view = new TextView(this);
        RxTextView.textChanges(view).skip(1).take(1).subscribe();
        RxView.clicks(mBtnRxjava).throttleFirst(2, TimeUnit.SECONDS)   //两秒钟之内只取一个点击事件，防抖操作
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Observable.timer(2, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long aLong) {
                                LoggerUtils.logger("Time===" + aLong);
                                startActivity(new Intent(MainActivity.this, RxJavaActivity.class));
                            }
                        });
                    }
                });
//        RxView.clicks(mBtnRxjava)
    }

    @OnClick({R.id.base_btn, R.id.refresh_load_btn, R.id.btn_dialog, R.id.btn_drag, R.id.btn_anim, R.id.btn_tinker, R.id.btn_tool_bar})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.base_btn:
                startActivity(new Intent(MainActivity.this, BaseUseActivity.class));
                break;
            case R.id.refresh_load_btn:
                startActivity(new Intent(MainActivity.this, RefreshLoadActivity.class));
                break;
            case R.id.btn_drag:
                startActivity(new Intent(MainActivity.this, SwipeRecyclerActivity.class));
                break;
            case R.id.btn_dialog:
                final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                        .setContentView(R.layout.normal_dialog_layout)
                        .setClickListener(R.id.btn_send, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToasUtils.toast(MainActivity.this, "发送按钮----");
                            }
                        }).setText(R.id.tv_title, "这是标题").
                                setText(R.id.tv_content, "我就是内容")
//                        .setBottom()
//                        .fullScreen()
                        .setSize(800, 450)
                        .setCancleable(true)
                        .addDefaultAnimation()
                        .setDimEnable(false)
                        .setBackShape(R.drawable.shape_normal_dialog_out)
                        .show();

                alertDialog.setText(R.id.tv_content, "9993333333333333333\n333333333339999999");
                alertDialog.setClickListener(R.id.iv_vb, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                break;
            case R.id.btn_tinker:
                Beta.applyTinkerPatch(MainActivity.this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/patch_signed_7zip.apk");
                break;
            case R.id.btn_tool_bar:
//                Observable.timer(2, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
//                    @Override
//                    public void call(Long aLong) {
//                        LoggerUtils.logger("Time===" + aLong);
//                        startActivity(new Intent(MainActivity.this, RxJavaActivity.class));
//                    }
//                });
                startActivity(new Intent(this,ToolBarDemoActivity.class));
                break;

            case R.id.btn_anim:

                RxPermissions rxPermissions = new RxPermissions(this);
                rxPermissions.requestEach(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(/*new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            String sFileFullPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/test.jpg";
                            File file = new File(sFileFullPath);
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                            Uri imageUri = FileProvider.getUriForFile(MainActivity.this, "com.example.demo.testgradle.fileProvider", file);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);


//                            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                            startActivityForResult(intent, 1);
                        } else {
                            ToasUtils.toast(MainActivity.this,"00000000");
                        }
                    }
                }*/new Action1<Permission>() {

                    @Override
                    public void call(Permission permission) {
                        if (permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            if (permission.granted) {
                                ToasUtils.toast(MainActivity.this, "00000000");
                            } else {
                                ToasUtils.toast(MainActivity.this, "11111111");
                            }
                        } else {
                            if (permission.granted) {
                                ToasUtils.toast(MainActivity.this, "22222222222");
                            } else {
                                ToasUtils.toast(MainActivity.this, "333333333");
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
//                startAnim();

                break;
        }
    }

    private void startAnim() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    SystemClock.sleep(200);
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mPPL.addFavor();
                        }
                    });
                }
            }
        }).start();

    }

    /**
     * 防止字体随手机系统的字体变大而变大
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }
}
