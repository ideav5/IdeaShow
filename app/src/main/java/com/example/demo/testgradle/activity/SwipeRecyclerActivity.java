package com.example.demo.testgradle.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.demo.testgradle.R;
import com.example.demo.testgradle.recyclerview.ListAdapter;
import com.example.demo.testgradle.recyclerview.base.GridLayoutDecoration;
import com.example.demo.testgradle.recyclerview.base.OnItemClickListener;
import com.example.demo.testgradle.recyclerview.base.SwipeCallback;
import com.example.demo.testgradle.recyclerview.widget.WrapRecyclerView;
import com.example.demo.testgradle.util.ToasUtils;

import java.util.Arrays;
import java.util.List;

public class SwipeRecyclerActivity extends AppCompatActivity {

    private WrapRecyclerView mRecyclerView;
    private List<String> mStringList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_use);
        mRecyclerView = (WrapRecyclerView) findViewById(R.id.rec_view);
//        mRecyclerView
        String[] listName = getResources().getStringArray(R.array.list_name);
        mStringList = Arrays.asList(listName);


        ListAdapter adapter = new ListAdapter(this, mStringList);
//        RecyclerView.LayoutManager layoutManager= LinearLayoutManager;
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
        View headerView = View.inflate(this, R.layout.normal_dialog_layout, null);
//        headerView.setBackgroundColor(Color.GREEN);
        mRecyclerView.addHeaderView(headerView);
        //添加分割线
        mRecyclerView.addItemDecoration(new GridLayoutDecoration(this,R.drawable.item_grid_divider,mRecyclerView));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeCallback<String>(mStringList,adapter));
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ToasUtils.toast(SwipeRecyclerActivity.this,position+"==========="+ mStringList.get(position));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.item1:
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                break;
            case R.id.item2:
                mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));

                break;
        }
        return true;
    }

}
