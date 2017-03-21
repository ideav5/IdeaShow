package com.example.demo.testgradle;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.demo.testgradle.banner.BannerAdapter;
import com.example.demo.testgradle.banner.BannerView;
import com.example.demo.testgradle.banner.BannerViewPager;
import com.example.demo.testgradle.navigationbar.DefaultNavigationBar;
import com.example.demo.testgradle.recyclerview.base.BaseRecyclerViewAdapter;
import com.example.demo.testgradle.recyclerview.base.OnItemClickListener;
import com.example.demo.testgradle.recyclerview.base.RecyclerGridSpaceDecoration;
import com.example.demo.testgradle.recyclerview.base.ViewHolder;
import com.example.demo.testgradle.recyclerview.refreshLoad.DefaultLoadCreator;
import com.example.demo.testgradle.recyclerview.refreshLoad.DefaultRefreshCreator;
import com.example.demo.testgradle.recyclerview.widget.LoaderRefreshRecyclerView;
import com.example.demo.testgradle.recyclerview.widget.RefreshRecyclerView;
import com.example.demo.testgradle.rx.RxBus;
import com.example.demo.testgradle.util.LoggerUtils;
import com.example.demo.testgradle.util.ToasUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Darren on 2016/12/29.
 * Email: 240336124@qq.com
 * Description:  RecyclerView下拉刷新上拉加载
 */

public class RefreshLoadActivity extends AppCompatActivity implements RefreshRecyclerView.OnRefreshListener, LoaderRefreshRecyclerView.OnLoadMoreListener {
    private LoaderRefreshRecyclerView mRecyclerView;
    private List<String> mDatas;
    private HomeAdapter mAdapter;
String []photos={
        "http://7xi8d6.com1.z0.glb.clouddn.com/2017-03-13-17267506_264626920661300_5781854075880472576_n.jpg",
        "http://7xi8d6.com1.z0.glb.clouddn.com/2017-03-08-17126216_1253875034703554_7520300169779216384_n.jpg",
        "http://7xi8d6.com1.z0.glb.clouddn.com/2017-03-06-tumblr_ombicf0KXi1vpybydo6_540.jpg",
        "http://7xi8d6.com1.z0.glb.clouddn.com/2017-02-28-15057157_446684252387131_4267811446148038656_n.jpg",
        "http://7xi8d6.com1.z0.glb.clouddn.com/2017-02-22-16789541_749566998525174_1194252851069583360_n.jpg",
        "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png",
        "http://img3.imgtn.bdimg.com/it/u=1329571997,1178095420&fm=23&gp=0.jpg",
        "http://pic10.nipic.com/20101014/3367900_101327028816_2.jpg"


};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        DefaultNavigationBar defaultNavigationBar = new DefaultNavigationBar.Builder(this).setTitle("RecyclerView").builder();
        mDatas = new ArrayList<>();
        initData();

        mRecyclerView = (LoaderRefreshRecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.addRefreshCreator(new DefaultRefreshCreator());
        mRecyclerView.addLoadCreator(new DefaultLoadCreator());
        mRecyclerView.setOnRefreshListener(this);
        mRecyclerView.setOnLoadMoreListener(this);


        // 设置正在获取数据页面和无数据页面
//        mRecyclerView.addLoadingView(findViewById(R.id.load_view));
//        mRecyclerView.addEmptyView(findViewById(R.id.empty_view));

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
//        mRecyclerView.addItemDecoration(new GridLayoutDecoration(this, R.drawable.item_grid_divider, mRecyclerView));
        mRecyclerView.addItemDecoration(new RecyclerGridSpaceDecoration(3));
        mAdapter = new HomeAdapter(RefreshLoadActivity.this, mDatas);
//                mAdapter.
        mRecyclerView.setAdapter(mAdapter);
        View banner = LayoutInflater.from(this).inflate(R.layout.ui_banner_layout2, null);
        mRecyclerView.addHeaderView(banner);

        BannerView bannerview = (BannerView) banner.findViewById(R.id.banner_vp2);
        bannerview.setAdapter(new BannerAdapter() {
            @Override
            public View getView(int position, View convertView) {
                ImageView imageView = new ImageView(RefreshLoadActivity.this);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setAdjustViewBounds(true);
                Glide.with(RefreshLoadActivity.this).load(photos[position]).centerCrop().into(imageView);
//                imageView.setImageResource(R.mipmap.ic_launcher);
                return imageView;
            }

            @Override
            public int getCount() {
                return photos.length;
            }
        });
        bannerview.setOnBannerItemClickListener(new BannerViewPager.BannerItemClickListener() {
            @Override
            public void click(int position) {
                ToasUtils.toast(RefreshLoadActivity.this,position+"==position===");
            }
        });
//        bannerViewPager.setAdapter(new BannerAdapter() {
//            @Override
//            public View getView(int position, View convertView) {
//                ImageView imageView = new ImageView(RefreshLoadActivity.this);
//                imageView.setImageResource(R.mipmap.ic_launcher);
//                return imageView;
//            }
//
//            @Override
//            public int getCount() {
//                return 5;
//            }
//        });
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mAdapter = new HomeAdapter(RefreshLoadActivity.this, mDatas);
////                mAdapter.
//                mRecyclerView.setAdapter(mAdapter);
//            }
//        },200);



        /*mAdapter = new HomeAdapter(RefreshLoadActivity.this, mDatas);
        mRecyclerView.setAdapter(mAdapter);*/

//        mRecyclerView.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                mDatas.clear();
//                mAdapter.notifyDataSetChanged();
//            }
//        });
        mRecyclerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                RxBus.getDefault().post("hhhh");
                LoggerUtils.logger("6666666666666666666");
            }
        });
        // mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
    }

    protected void initData() {
        for (int i = 'A'; i < 'z'; i++) {
            mDatas.add("" + (char) i);
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.id_action_gridview:
//                mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
//                break;
//            case R.id.id_action_listview:
//                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//                break;
//        }
//        return true;
//    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.onStopRefresh();
            }
        }, 2000);
    }

    @Override
    public void onLoad() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initData();
                mRecyclerView.onStopLoad();
                mAdapter.notifyDataSetChanged();
            }
        }, 2000);
    }

    class HomeAdapter extends BaseRecyclerViewAdapter<String> {

        public HomeAdapter(Context context, List<String> data) {
            super(context, data, R.layout.item_home);
        }

        @Override
        public void convert(ViewHolder holder, String item) {
            holder.setText(R.id.id_num, item);
        }
    }
}
