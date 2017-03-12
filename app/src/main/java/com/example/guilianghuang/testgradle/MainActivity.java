package com.example.guilianghuang.testgradle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.guilianghuang.testgradle.dialog.AlertDialog;
import com.example.guilianghuang.testgradle.recyclerview.BaseUseActivity;
import com.example.guilianghuang.testgradle.util.ToasUtils;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.base_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BaseUseActivity.class));
            }
        });


        findViewById(R.id.btn_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                        .setContentView(R.layout.normal_dialog_layout)
                        .setClickListener(R.id.btn_send, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToasUtils.toast(MainActivity.this, "发送按钮----");
                            }
                        }).setText(R.id.tv_title, "这是标题").
                                setText(R.id.tv_content, "我就是内容")
                        .setBottom()
                        .fullScreen()
                        .setBackShape(R.drawable.shape_normal_dialog_out)
                        .show();

                alertDialog.setText(R.id.tv_content,"9993333333333333333\n333333333339999999");
            }
        });

    }
}
