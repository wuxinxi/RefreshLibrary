package com.wxx.refreshlibrary.recyclerView;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.wxx.refreshlibrary.R;
import com.wxx.refreshlibrary.iterfaces.OnLoadMoreListener;
import com.wxx.refreshlibrary.iterfaces.OnNetWorkErrorListener;
import com.wxx.refreshlibrary.iterfaces.OnRefreshListener;

/**
 * Created by Administrator on 2017/6/16 0016.
 */

public class PullRefreshAndLoadMoreView extends LinearLayout implements SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener {

    private SwipeRefreshLayout refreshLayout;
    private TRecyclerView recyclerView;
    private View emptyView;
    private View netErrorView;

    private int[] colorRes = new int[]{android.R.color.holo_blue_bright,
            android.R.color.holo_red_light,
            android.R.color.holo_green_light};

    private OnRefreshListener onRefreshListener;
    private OnLoadMoreListener onLoadMoreListener;


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
        emptyView = view.findViewById(R.id.emptyView);
        netErrorView = view.findViewById(R.id.netError);
        refreshLayout.setOnRefreshListener(this);
        recyclerView.setOnLoadMoreListener(this);
        refreshLayout.setColorSchemeResources(colorRes);
    }

    /**
     * 设置LayoutManager
     *
     * @param layoutManager
     */
    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        if (recyclerView != null)
            recyclerView.setLayoutManager(layoutManager);
    }

    /**
     * 设置SwipeRefreshLayout的颜色
     *
     * @param colorResIds
     */
    public void setColorSchemeResources(@ColorRes int... colorResIds) {
        if (colorResIds.length > 3)
            throw new IllegalArgumentException("The length must be within 3 ");
        refreshLayout.setColorSchemeResources(colorResIds);
    }

    /**
     * @param adapter
     */
    public void setAdapter(RecyclerView.Adapter adapter) {
        if (recyclerView != null)
            recyclerView.setAdapter(adapter);
    }

    /**
     * 设置底部文字
     *
     * @param loading
     * @param noMore
     * @param noNetWork
     */
    public void setFooterViewHint(String loading, String noMore, String noNetWork) {
        if (recyclerView != null)
            recyclerView.setFooterViewHint(loading, noMore, noNetWork);
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;

    }

    /**
     * 数据加载完毕
     *
     * @param pageSize
     */
    public void refreshComplete(int pageSize) {
        if (recyclerView != null)
            recyclerView.refreshComplete(pageSize);
    }

    public void setRefreshing(boolean refreshing) {
        if (refreshLayout != null)
            refreshLayout.setRefreshing(refreshing);
    }

    /**
     * 已无更多数据
     *
     * @param noMore
     */
    public void setNoMore(boolean noMore) {
        if (recyclerView != null)
            recyclerView.setNoMore(noMore);
    }


    /**
     * 空数据
     *
     * @param emptyView
     */
    public void setEmpty(View emptyView) {
        if (emptyView == null)
            throw new IllegalArgumentException("Empty view must no null");
        if (recyclerView != null)
            recyclerView.setEmptyView(emptyView);
    }

    public void setEmpty() {
        if (recyclerView != null)
            recyclerView.setEmptyView(emptyView);
    }

    public void setNetError() {
        if (recyclerView != null)
            recyclerView.setEmptyView(netErrorView);
    }


    /**
     * 设置Footer文字颜色
     *
     * @param indicatorColor
     * @param hintColor
     * @param backgroundColor
     */
    public void setFooterViewColor(int indicatorColor, int hintColor, int backgroundColor) {
        if (recyclerView != null)
            recyclerView.setFooterViewColor(indicatorColor, hintColor, backgroundColor);
    }

    @Override
    public void onRefresh() {
        if (onRefreshListener != null)
            onRefreshListener.onRefresh();
    }

    @Override
    public void onLoadMore() {
        if (onLoadMoreListener != null)
            onLoadMoreListener.onLoadMore();
    }

    public void setOnNetWorkErrorListener(OnNetWorkErrorListener listener) {
        if (recyclerView != null)
            recyclerView.setOnNetWorkErrorListener(listener);
    }
}
