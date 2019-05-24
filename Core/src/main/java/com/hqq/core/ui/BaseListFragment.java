package com.hqq.core.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hqq.core.BaseCommonsKey;
import com.hqq.core.ui.model.BaseListModel;
import com.hqq.core.widget.CusPtrClassicFrameLayout;
import com.hqq.core.R;

import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.ui
 * @FileName :   BaseListFragment
 * @Date : 2018/11/23 0023  上午 11:47
 * @Descrive :
 * @Email :  qiqiang213@gmail.com
 */
public abstract class BaseListFragment<T extends BaseQuickAdapter> extends BaseFragment implements
        BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener,
        BaseQuickAdapter.OnItemChildClickListener, BaseListModel.BaseListModelView<T> {
    protected RecyclerView mRcList;
    protected T mAdapter;
    /**
     * 默认的填充数据
     *
     * @param data
     */
    protected int mPageSize = BaseCommonsKey.PAGE_SIZE;
    protected int mPageCount = 1;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected BaseListModel mBaseListModel;

    @Override
    public int getViewId() {
        return 0;
    }

    @Override
    public View getRootView() {
        return mBaseListModel.createRecycleView(getContext());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mBaseListModel = new BaseListModel(this, getContext());
        }
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initBasic(Bundle savedInstanceState) {
        mLayoutManager = getRcLayoutManager();
        mAdapter = initAdapter();
        mRcList = mBaseListModel.checkRecycleView(mRcList, mRootViewBuild.getRootView());

        mBaseListModel.initRecycleView(mRcList, mAdapter, mLayoutManager,
                this, this, this);
        mBaseListModel.initPtrPullDown(mRootViewBuild.getRootView());
        initData();
    }

    @Override
    public void onLoadMoreRequested() {
        onLoadMore();
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
    public T getAdapter() {
        return mAdapter;
    }

    @Override
    public ViewGroup getListView() {
        return mRcList;
    }

    @Override
    public boolean isShowLoadMore() {
        return false;
    }

    @Override
    public RecyclerView.LayoutManager getRcLayoutManager() {
        return new LinearLayoutManager(mActivity);
    }

    /**
     * 加载更过数据
     */
    protected void onLoadMore() {

    }


}
