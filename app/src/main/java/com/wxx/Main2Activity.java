package com.wxx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.wxx.refreshlibrary.baseadapter.BaseAdapter;
import com.wxx.refreshlibrary.iterfaces.OnLoadMoreListener;
import com.wxx.refreshlibrary.iterfaces.OnRefreshListener;
import com.wxx.refreshlibrary.recyclerView.PullRefreshAndLoadMoreView;
import com.wxx.refreshlibrary.recyclerView.TRecyclerViewAdapter;
import com.wxx.refreshlibrary.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity implements OnRefreshListener, OnLoadMoreListener {

    private PullRefreshAndLoadMoreView pullRefreshAndLoadMoreView;
    TRecyclerViewAdapter tAdapter;
    MyAdapter myAdapter;
    List<String> datas = new ArrayList<>();
    View emptyView;
    private static final String TAG = "Main2Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        pullRefreshAndLoadMoreView = (PullRefreshAndLoadMoreView) findViewById(R.id.view);
        emptyView = findViewById(R.id.emptyView);
        pullRefreshAndLoadMoreView.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_orange_dark);
        pullRefreshAndLoadMoreView.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");
        pullRefreshAndLoadMoreView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new MyAdapter(R.layout.view_itre, datas);
        tAdapter = new TRecyclerViewAdapter(myAdapter);
        pullRefreshAndLoadMoreView.setAdapter(tAdapter);
        pullRefreshAndLoadMoreView.setOnLoadMoreListener(this);
        pullRefreshAndLoadMoreView.setOnRefreshListener(this);

        Log.e(TAG, "onCreate: MyAdapter"+myAdapter.getItemCount());
        Log.e(TAG, "onCreate: TRecyclerViewAdapter"+tAdapter.getItemCount());
        pullRefreshAndLoadMoreView.setEmpty(emptyView);
        emptyView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                myAdapter.addMoreListData(getData());
            }
        });


    }

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
                        pullRefreshAndLoadMoreView.refreshComplete(1);
                        tAdapter.notifyDataSetChanged();
                    }
                });

            }
        }).start();

    }

    @Override
    public void onRefresh() {
        myAdapter.addMoreListData(0, add());
        pullRefreshAndLoadMoreView.setRefreshing(false);
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
