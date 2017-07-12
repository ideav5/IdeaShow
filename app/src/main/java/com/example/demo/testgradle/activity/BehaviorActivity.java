package com.example.demo.testgradle.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.demo.testgradle.R;
import com.example.demo.testgradle.recyclerview.base.BaseRecyclerViewAdapter;
import com.example.demo.testgradle.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class BehaviorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_behavior);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rec_view);

        List<String> listStr = new ArrayList<>();
        for (int i = 0; i < 50; i++) {

            listStr.add("listStr====="+i);
        }

        MyAdapter myAdapter = new MyAdapter(this, listStr);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);


    }


    class MyAdapter extends BaseRecyclerViewAdapter<String> {
        public MyAdapter(Context context, List<String> listData) {
            super(context, listData, R.layout.item_list);
        }

        @Override
        public void convert(ViewHolder holder, String s) {
            holder.setText(R.id.item_tv, s);
        }
    }
}
