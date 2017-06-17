package com.wxx;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.wxx.refreshlibrary.baseadapter.BaseAdapter;
import com.wxx.refreshlibrary.iterfaces.OnItemClickListener;
import com.wxx.refreshlibrary.iterfaces.OnLoadMoreListener;
import com.wxx.refreshlibrary.iterfaces.OnNetWorkErrorListener;
import com.wxx.refreshlibrary.recyclerView.TRecyclerView;
import com.wxx.refreshlibrary.recyclerView.TRecyclerViewAdapter;
import com.wxx.refreshlibrary.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnRefreshListener, OnItemClickListener,OnNetWorkErrorListener {

    TRecyclerView recyclerView;
    TRecyclerViewAdapter tAdapter;
    MyAdapter myAdapter;
    List<String> datas = new ArrayList<>();
    SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (TRecyclerView) findViewById(R.id.recyclerView);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        myAdapter = new MyAdapter(R.layout.view_itre,getData());
        tAdapter = new TRecyclerViewAdapter(myAdapter);
        tAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(tAdapter);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(android.R.color.background_light, android.R.color.holo_green_light);
        recyclerView.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");

        recyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                myAdapter.addMoreListData(more());
                                recyclerView.refreshComplete(1);
                                tAdapter.notifyDataSetChanged();
                            }
                        });

                    }
                }).start();


            }
        });


        recyclerView.setOnNetWorkErrorListener(this);

    }


    public List<String> getData() {
        for (int i = 0; i < 15; i++) {
            datas.add("TITLE" + i);
        }
        return datas;
    }

    public List<String> add() {
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            datas.add("我是新增的" + System.currentTimeMillis());
        }
        return datas;
    }

    public List<String> more() {
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            datas.add("我是下拉刷新的" + System.currentTimeMillis());
        }
        return datas;
    }

    @Override
    public void onRefresh() {
        myAdapter.addMoreListData(0,add());
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "position:" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void reload() {

    }


    class MyAdapter extends BaseAdapter<String> {

        private List<String> list = new ArrayList<>();

        public MyAdapter(int mLayoutID, List<String> mDatas) {
            super(mLayoutID, mDatas);
            this.list = mDatas;
        }

        @Override
        protected void convert(BaseViewHolder holder, int postion) {
            holder.setText(R.id.title, list.get(postion));
        }
    }
}
