package com.hqq.core.ui.vm;

import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hqq.core.R;
import com.hqq.core.ui.model.BaseListModelView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui.vm
 * @FileName :   BaseListFragment
 * @Date : 2020/7/29 0029  下午 2:29
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public abstract class BaseVmListFragment<T extends ViewDataBinding, K extends BaseListViewModel, AD extends BaseQuickAdapter>
        extends BaseVmFragment<T, K> implements BaseListModelView.IBaseListModelView<AD> {
    protected RecyclerView mRcList;
    protected AD mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected BaseListModelView mBaseListModel;

    @Override
    public void addViewModel() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_recycle_view;
    }

    @Override
    public void initViews() {
        mBaseListModel = new BaseListModelView(this, getContext());
        mLayoutManager = getRcLayoutManager();
        mAdapter = initAdapter();

        mRcList = mBaseListModel.checkRecycleView(mRcList, mRootViewBuild.getRootView());

        mBaseListModel.initRecycleView(mRcList, mAdapter, mLayoutManager);

        mBaseListModel.initPtrPullDown(mRootViewBuild.getRootView());

        mViewModel.mDate.observe(this, new Observer<List>() {
            @Override
            public void onChanged(List arrayList) {
                mBaseListModel.fillingData(arrayList);

            }
        });


        initData();

    }

    @Override
    public void onLoadMoreRequested() {
        mViewModel.onLoadMore();
    }

    @Override
    public boolean isShowLoadMore() {
        return false;
    }

    @Override
    public AD getAdapter() {
        return mAdapter;
    }

    @Override
    public int getPageCount() {
        return mViewModel.getPageCount();
    }

    @Override
    public int getPageSize() {
        return mViewModel.getPageSize();
    }

    @Override
    public void addPageCount() {
        mViewModel.setPageCount(mViewModel.getPageCount() + 1);
    }

    @Override
    public void onRefreshBegin() {
        mViewModel.setPageCount(1);
        mAdapter.loadMoreComplete();
        mViewModel.onLoadMore();
    }

    @Override
    public ViewGroup getListView() {
        return mRcList;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBaseListModel.onDestroy();
        mBaseListModel = null;
    }

    @Override
    public RecyclerView.LayoutManager getRcLayoutManager() {
        return new LinearLayoutManager(getContext());
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
    }
}
