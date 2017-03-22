package com.example.demo.testgradle.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.example.demo.testgradle.R;
import com.example.demo.testgradle.navigationbar.DefaultNavigationBar;
import com.example.demo.testgradle.recyclerview.ListAdapter;
import com.example.demo.testgradle.recyclerview.base.GridLayoutDecoration;
import com.example.demo.testgradle.recyclerview.base.OnItemClickListener;
import com.example.demo.testgradle.recyclerview.widget.CustomItemTouchHelperCallback;
import com.example.demo.testgradle.recyclerview.widget.OnItemTouchCallbackListener;
import com.example.demo.testgradle.recyclerview.widget.WrapRecyclerView;
import com.example.demo.testgradle.util.LoggerUtils;
import com.example.demo.testgradle.util.ToasUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SwipeRecyclerActivity extends AppCompatActivity {

    private WrapRecyclerView mRecyclerView;
    private List<String> mStringList;
    private ListAdapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_use);
        DefaultNavigationBar defaultNavigationBar=new DefaultNavigationBar.Builder(this)
                .setTitle("拖拽RecyclerView").builder();
        mRecyclerView = (WrapRecyclerView) findViewById(R.id.rec_view);
//        mRecyclerView
        String[] listName = getResources().getStringArray(R.array.list_name);
        mStringList = new ArrayList<>();
        for (int i = 0; i < listName.length; i++) {
            mStringList.add(listName[i]);

        }
//        mStringList = Arrays.asList(listName);//不能移出


        mListAdapter = new ListAdapter(this, mStringList);
//        RecyclerView.LayoutManager layoutManager= LinearLayoutManager;
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mListAdapter);
        View headerView = View.inflate(this, R.layout.normal_dialog_layout, null);
//        headerView.setBackgroundColor(Color.GREEN);
        mRecyclerView.addHeaderView(headerView);
        //添加分割线
        mRecyclerView.addItemDecoration(new GridLayoutDecoration(this,R.drawable.item_grid_divider,mRecyclerView));
        CustomItemTouchHelperCallback itemTouchHelperCallback=new CustomItemTouchHelperCallback(new OnItemTouchCallbackListener() {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

                int fromPosition = viewHolder.getAdapterPosition();
                int targetPosition = target.getAdapterPosition();
//                WrapRecyclerAdapter adapter = (WrapRecyclerAdapter) mRecyclerView.getAdapter();
//                int fromPosition = adapter.getDatePosition(viewHolder.getAdapterPosition());
//                int targetPosition = adapter.getDatePosition(target.getAdapterPosition());
                if (fromPosition < targetPosition) {
                    for (int i = fromPosition; i < targetPosition; i++) {
                        LoggerUtils.logger(i+1+"=========<<<<"+i);
                        Collections.swap(mStringList, i, i + 1);// 改变实际的数据集
                    }
                } else {
                    for (int i = fromPosition; i > targetPosition; i--) {
                        LoggerUtils.logger(i-1+"=========<<<<"+i);
                        Collections.swap(mStringList, i, i - 1);// 改变实际的数据集
                    }
                }
                mListAdapter.notifyItemMoved(fromPosition, targetPosition);
               LoggerUtils.logger(recyclerView.getAdapter()+"---------onMove--------"+fromPosition+"------targetPosition------"+targetPosition);
                    return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int adapterPosition = viewHolder.getAdapterPosition();
                LoggerUtils.logger(adapterPosition+"-----------onSwiped-----");
//                int datePosition = ((WrapRecyclerAdapter) mRecyclerView.getAdapter()).getDatePosition(adapterPosition);
//                mStringList.remove(datePosition);
                mListAdapter.notifyItemRemoved(adapterPosition);
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ToasUtils.toast(SwipeRecyclerActivity.this,position+"==========="+ mStringList.get(position));
            }
        });
        LoggerUtils.logger(mRecyclerView.getAdapter()+"-----------mListAdapter-----"+mListAdapter);

    }


}
