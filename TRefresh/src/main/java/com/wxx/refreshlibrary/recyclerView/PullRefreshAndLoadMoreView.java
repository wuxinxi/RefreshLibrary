package com.wxx.refreshlibrary.recyclerView;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.wxx.refreshlibrary.R;
import com.wxx.refreshlibrary.iterfaces.OnLoadMoreListener;

/**
 * Created by Administrator on 2017/6/16 0016.
 */

public class PullRefreshAndLoadMoreView extends LinearLayout implements SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener {

    private SwipeRefreshLayout refreshLayout;
    private TRecyclerView recyclerView;

    private int[] colorRes = new int[]{android.R.color.holo_blue_bright,
            android.R.color.holo_red_light,
            android.R.color.holo_green_light};

    public PullRefreshAndLoadMoreView(Context context) {
        this(context, null);
    }

    public PullRefreshAndLoadMoreView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullRefreshAndLoadMoreView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = LayoutInflater.from(context).inflate(R.layout.layout_refresh_load_view, this, true);
        recyclerView = (TRecyclerView) view.findViewById(R.id.recyclerView);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(this);
        recyclerView.setOnLoadMoreListener(this);
        refreshLayout.setColorSchemeResources(colorRes);
    }


    public void setColorSchemeResources(@ColorRes int... colorResIds) {
        if (colorResIds.length > 3)
            throw new IllegalArgumentException("The length must be within 3 ");
        for (int i = 0; i < colorResIds.length; i++) {
            colorRes[i] = colorResIds[i];
        }
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
}
