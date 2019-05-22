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
import com.hqq.core.R;
import com.hqq.core.ui.model.BaseListModel;
import com.hqq.core.widget.CusPtrClassicFrameLayout;

import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * @Author : huangqiqiang
 * @Package : com.core.library
 * @FileName :   BaseListActivity
 * @Date : 2018/11/5 0005  下午 4:23
 * @Descrive :
 * @Email :
 */
public abstract class BaseListActivity<T extends BaseQuickAdapter> extends BaseActivity implements
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mBaseListModel = new BaseListModel(this, this);
        super.onCreate(savedInstanceState);
    }

    /**
     * 重写了 getViewId    执行 getRootView
     *
     * @return
     */
    @Override
    public View getRootView() {
        return mBaseListModel.createRecycleView(this);
    }

    /**
     * 初始化  基础数据
     */
    @Override
    public void initView() {
        mLayoutManager = getRcLayoutManager();
        mAdapter = getAdapter();
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

    /**
     * 是需要加载更多
     *
     * @return
     */
    @Override
    public boolean isShowLoadMore() {
        return false;
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

    private RecyclerView.LayoutManager getRcLayoutManager() {
        return initDefLayoutManager();
    }

    protected RecyclerView.LayoutManager initDefLayoutManager() {
        return new LinearLayoutManager(this);
    }

    protected void onLoadMore() {

    }


}
