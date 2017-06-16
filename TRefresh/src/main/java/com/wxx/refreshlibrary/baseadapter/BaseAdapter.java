package com.wxx.refreshlibrary.baseadapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.wxx.refreshlibrary.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    protected int mLayoutID;

    protected List<T> mDatas = new ArrayList<>();

    public BaseAdapter(int mLayoutID, List<T> mDatas) {
        this.mLayoutID = mLayoutID;
        this.mDatas = mDatas;
    }


    public void setNewData(List<T> data) {
        this.mDatas = data;
        notifyDataSetChanged();
    }


    public void addMoreListData(List<T> mDatas) {
        this.mDatas.addAll(mDatas);
        notifyDataSetChanged();
    }

    public void addMoreListData(int postion,List<T> mDatas) {
        this.mDatas.addAll(postion,mDatas);
        notifyDataSetChanged();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder = BaseViewHolder.createViewHolder(parent, mLayoutID);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        convert(holder);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void convert(BaseViewHolder holder) {
        convert(holder, holder.getAdapterPosition());
    }

    protected abstract void convert(BaseViewHolder holder, int postion);


}
