package com.wxx.refreshlibrary.iterfaces;

import android.view.View;

/**
 * Created by Administrator on 2017/6/15 0015.
 */

public interface ILoadMoreFooter {

    //回复初始设置
    void onReset();

    //加载中
    void onLoading();

    //加载完成
    void onComplete();

    //已全部加载完毕
    void onNoMore();

    //加载更多的View
    View getFootView();
}
