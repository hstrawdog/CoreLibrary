package com.hqq.core.ui;

import android.os.Bundle;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hqq.core.BaseCommonsKey;
import com.hqq.core.ui.model.BaseListModel;

/**
 * @Author : huangqiqiang
 * @Package : com.core.library
 * @FileName :   BaseListActivity
 * @Date : 2018/11/5 0005  下午 4:23
 * @Descrive :
 * @Email :  qiqiang213@gmail.com
 */
public abstract class BaseListActivity<T extends BaseQuickAdapter> extends BaseActivity implements
        BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener,
        BaseQuickAdapter.OnItemChildClickListener, BaseListModel.BaseListModelView<T> {
    protected RecyclerView mRcList;
    protected T mAdapter;
    protected int mPageSize = BaseCommonsKey.PAGE_SIZE;
    protected int mPageCount = 1;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected BaseListModel mBaseListModel;

    @Override
    public int setViewId() {
        return 0;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 重写了 setViewId    执行 setRootView
     * 默认都会执行一般这个方法 但是没有添加到rooView中
     *
     * @return
     */
    @Override
    public View setRootView() {
        if (setViewId() <= 0) {
            return BaseListModel.createRecycleView(this);
        } else {
            return null;
        }
    }

    /**
     * 初始化  基础数据
     */
    @Override
    @CallSuper
    public void initView() {
        mBaseListModel = new BaseListModel(this, this);
        mLayoutManager = getRcLayoutManager();
        mAdapter = initAdapter();
        mRcList = mBaseListModel.checkRecycleView(mRcList, mRootViewBuild.getRootView());
        mBaseListModel.initRecycleView(mRcList, mAdapter, mLayoutManager);
        mBaseListModel.initPtrPullDown(mRootViewBuild.getRootView());
        initData();
    }

    @Override
    public void onLoadMoreRequested() {
        onLoadMore();
    }

    @Override
    public boolean isShowLoadMore() {
        return false;
    }

    @Override
    public T getAdapter() {
        return mAdapter;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
    }

    @Override
    public int getPageCount() {
        return mPageCount;
    }

    @Override
    public int getPageSize() {
        return mPageSize;
    }

    @Override
    public int addPageCount() {
        return (mPageCount++);
    }

    @Override
    public void onRefreshBegin() {
        mPageCount = 1;
        mAdapter.loadMoreComplete();
        onLoadMore();
    }

    @Override
    public ViewGroup getListView() {
        return mRcList;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBaseListModel.onDestroy();
        mBaseListModel = null;
    }

    @Override
    public RecyclerView.LayoutManager getRcLayoutManager() {
        return new LinearLayoutManager(this);
    }


    protected void onLoadMore() {
    }


}
