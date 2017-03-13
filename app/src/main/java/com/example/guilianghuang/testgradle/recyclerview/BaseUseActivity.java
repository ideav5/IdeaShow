package com.example.guilianghuang.testgradle.recyclerview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.guilianghuang.testgradle.R;
import com.example.guilianghuang.testgradle.recyclerview.widget.WrapRecyclerView;

import java.util.Arrays;
import java.util.List;

public class BaseUseActivity extends AppCompatActivity {

    private WrapRecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_use);
        mRecyclerView = (WrapRecyclerView) findViewById(R.id.rec_view);
//        mRecyclerView
        String[] listName = getResources().getStringArray(R.array.list_name);
        List<String> stringList = Arrays.asList(listName);


        ListAdapter adapter = new ListAdapter(stringList, this);
//        RecyclerView.LayoutManager layoutManager= LinearLayoutManager;
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
        View headerView = View.inflate(this, R.layout.normal_dialog_layout, null);
        headerView.setBackgroundColor(Color.GREEN);
        mRecyclerView.addHeaderView(headerView);
        //添加分割线
//        mRecyclerView.addItemDecoration(new LinearLayoutDecoration(this,R.drawable.item_divider));
        mRecyclerView.addItemDecoration(new GridLayoutDecoration(this,R.drawable.item_grid_divider,mRecyclerView));
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

//    class MyItemDecoration extends RecyclerView.ItemDecoration {
//
//        private final Paint mPaint;
//
//        public MyItemDecoration() {
//
//            mPaint = new Paint();
//            mPaint.setColor(Color.GREEN);
//            mPaint.setAntiAlias(true);
//
//        }
//
//        @Override
//        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//            int childAdapterPosition = parent.getChildAdapterPosition(view);
//            if (childAdapterPosition!=0) {
//                outRect.top=10;
//            }
//        }
//
//        @Override
//        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
//            int childCount = parent.getChildCount();
//            Rect rect = new Rect();
//
//            rect.left = parent.getPaddingLeft() ;
//            rect.right = parent.getWidth() - parent.getPaddingRight();
//            for (int i=1;i<childCount;i++){
//                rect.bottom=parent.getChildAt(i).getTop();
//                rect.top=rect.bottom-10;
//                c.drawRect(rect, mPaint);
//
//            }
//
//        }
//    }mPaint


}
