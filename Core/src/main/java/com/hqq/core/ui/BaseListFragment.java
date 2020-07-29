package com.hqq.core.ui;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hqq.core.BaseCommonsKey;
import com.hqq.core.ui.model.BaseListModel;

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.ui
 * @FileName :   BaseListFragment
 * @Date : 2018/11/23 0023  上午 11:47
 * @Descrive :
 * @Email :  qiqiang213@gmail.com
 */
public abstract class BaseListFragment<T extends BaseQuickAdapter> extends BaseCoreFragment implements
        BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener,
        BaseQuickAdapter.OnItemChildClickListener, BaseListModel.IBaseListModelView<T> {
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
    public int getLayoutViewId() {
        return 0;
    }

    @Override
    public View getLayoutView(ViewGroup group) {
        if (getLayoutViewId() <= 0) {
            return BaseListModel.createRecycleView(getContext());
        } else {
            return null;
        }

    }


    @Override
    public void initView() {
        mBaseListModel = new BaseListModel(this, getContext());
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
    public void addPageCount() {
        mPageCount++;
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
