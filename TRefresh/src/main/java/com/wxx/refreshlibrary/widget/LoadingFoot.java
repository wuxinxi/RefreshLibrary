package com.wxx.refreshlibrary.widget;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wxx.refreshlibrary.R;
import com.wxx.refreshlibrary.indicator.LoadingIndicatorView;
import com.wxx.refreshlibrary.iterfaces.ILoadMoreFooter;
import com.wxx.refreshlibrary.recyclerView.ProgressStyle;

/**
 * Created by Administrator on 2017/6/15 0015.
 */

public class LoadingFoot extends RelativeLayout implements ILoadMoreFooter {

    protected State mState = State.NORMAL;
    private View mLoadingView;
    private View mNetworkErrorView;
    private View mTheEndView;
    private ViewSwitcher mProgressView;
    private TextView mLoadingText;
    private TextView mNoMoreText;
    private TextView mNoNetWorkText;
    private String loadingHint;
    private String noMoreHint;
    private String noNetWorkHint;
    private int style;
    private int indicatorColor;
    private int hintColor = R.color.colorAccent;

    public LoadingFoot(Context context) {
        this(context, null);
    }

    public LoadingFoot(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingFoot(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.layout_recyclerview_list_footer, this);
        setOnClickListener(null);
        onReset();
        indicatorColor = ContextCompat.getColor(getContext(), R.color.colorAccent);
        style = ProgressStyle.BallPulse;
    }


    public void setLoadingHint(String msgHint) {
        this.loadingHint = msgHint;
    }

    public void setNoMoreHint(String msgHint) {
        this.noMoreHint = msgHint;
    }

    public void setNoNetWorkHint(String msgHint) {
        this.noNetWorkHint = msgHint;
    }

    public void setIndicatorColor(int color) {
        this.indicatorColor = color;
    }

    public void setHintTextColor(int color) {
        this.hintColor = color;
    }

    public void setViewBackgroundColor(int color) {
        this.setBackgroundColor(ContextCompat.getColor(getContext(), color));
    }

    public void setProgressStyle(int style) {
        this.style = style;
    }

    public State getState() {
        return mState;
    }

    public void setState(State state) {
        setState(state, true);
    }


    public void setState(State state, boolean showView) {
        if (mState == state) {
            return;
        }
        mState = state;
        switch (state) {
            case NORMAL:
                setOnClickListener(null);
                if (mLoadingView != null) {
                    mLoadingView.setVisibility(GONE);
                }

                if (mTheEndView != null) {
                    mTheEndView.setVisibility(GONE);
                }

                if (mNetworkErrorView != null) {
                    mNetworkErrorView.setVisibility(GONE);
                }
                break;
            case NOMORE:
                setOnClickListener(null);
                if (mLoadingView != null) {
                    mLoadingView.setVisibility(GONE);
                }

                if (mNetworkErrorView != null) {
                    mNetworkErrorView.setVisibility(GONE);
                }

                if (mTheEndView == null) {
                    ViewStub stub = (ViewStub) findViewById(R.id.end_viewstub);
                    mTheEndView = stub.inflate();
                    mNoMoreText = (TextView) mTheEndView.findViewById(R.id.loading_end_text);
                } else {
                    mTheEndView.setVisibility(VISIBLE);
                }

                mTheEndView.setVisibility(showView ? VISIBLE : GONE);
                mNoMoreText.setText(TextUtils.isEmpty(noMoreHint) ? getResources().getString(R.string.list_footer_end) : noMoreHint);
                mNoMoreText.setTextColor(ContextCompat.getColor(getContext(), hintColor));

                break;
            case LOADING:
                setOnClickListener(null);
                if (mTheEndView != null) {
                    mTheEndView.setVisibility(GONE);
                }

                if (mNetworkErrorView != null) {
                    mNetworkErrorView.setVisibility(GONE);
                }

                if (mLoadingView == null) {
                    ViewStub viewStub = (ViewStub) findViewById(R.id.loading_viewstub);
                    mLoadingView = viewStub.inflate();

                    mProgressView = (ViewSwitcher) mLoadingView.findViewById(R.id.loading_progressbar);
                    mLoadingText = (TextView) mLoadingView.findViewById(R.id.loading_text);
                }

                mLoadingView.setVisibility(showView ? VISIBLE : GONE);

                mProgressView.setVisibility(View.VISIBLE);
                mProgressView.removeAllViews();
                mProgressView.addView(initIndicatorView(style));

                mLoadingText.setText(TextUtils.isEmpty(loadingHint) ? getResources().getString(R.string.list_footer_loading) : loadingHint);
                mLoadingText.setTextColor(ContextCompat.getColor(getContext(), hintColor));

                break;
            case NETERROR:
                if (mLoadingView != null) {
                    mLoadingView.setVisibility(GONE);
                }

                if (mTheEndView != null) {
                    mTheEndView.setVisibility(GONE);
                }

                if (mNetworkErrorView == null) {
                    ViewStub viewStub = (ViewStub) findViewById(R.id.network_error_viewstub);
                    mNetworkErrorView = viewStub.inflate();
                    mNoNetWorkText = (TextView) mNetworkErrorView.findViewById(R.id.network_error_text);
                } else {
                    mNetworkErrorView.setVisibility(VISIBLE);
                }

                mNetworkErrorView.setVisibility(showView ? VISIBLE : GONE);
                mNoNetWorkText.setText(TextUtils.isEmpty(noNetWorkHint) ? getResources().getString(R.string.list_footer_network_error) : noNetWorkHint);
                mNoNetWorkText.setTextColor(ContextCompat.getColor(getContext(), hintColor));
                break;
            default:
                break;
        }
    }


    private View initIndicatorView(int style) {
        if (style == ProgressStyle.SysProgress) {
            return new ProgressBar(getContext(), null, android.R.attr.progressBarStyle);
        } else {
            LoadingIndicatorView progressView = (LoadingIndicatorView) LayoutInflater.from(getContext()).inflate(R.layout.layout_indicator_view, null);
            progressView.setIndicatorId(style);
            progressView.setIndicatorColor(indicatorColor);
            return progressView;
        }
    }


    @Override
    public void onReset() {
        onComplete();
    }

    @Override
    public void onLoading() {
        setState(State.LOADING);
    }

    @Override
    public void onComplete() {
        setState(State.NORMAL);
    }

    @Override
    public void onNoMore() {
        setState(State.NOMORE);

    }

    @Override
    public View getFootView() {
        return this;
    }

    public enum State {
        NORMAL, NOMORE, LOADING, NETERROR
    }
}
