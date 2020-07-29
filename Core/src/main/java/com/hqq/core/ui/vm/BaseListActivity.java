package com.hqq.core.ui.vm;

import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hqq.core.R;
import com.hqq.core.ui.model.BaseListModel;

import java.util.ArrayList;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui.vm
 * @FileName :   BaseListActivity
 * @Date : 2020/7/29 0029  上午 9:54
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public abstract class BaseListActivity<T extends ViewDataBinding, K extends BaseListViewModel, AD extends BaseQuickAdapter>
        extends BaseActivity<T, K> implements BaseListModel.IBaseListModelView<AD> {

    protected RecyclerView mRcList;
    protected AD mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected BaseListModel mBaseListModel;


    @Override
    public int getLayoutId() {
        return R.layout.activity_recycle_view;
    }

    @Override
    public void initViews() {
        mBaseListModel = new BaseListModel(this, this);
        mLayoutManager = getRcLayoutManager();
        mAdapter = initAdapter();

        mRcList = mBaseListModel.checkRecycleView(mRcList, mRootViewBuild.getRootView());

        mBaseListModel.initRecycleView(mRcList, mAdapter, mLayoutManager);

        mBaseListModel.initPtrPullDown(mRootViewBuild.getRootView());

        mViewModel.mDate.observe(this, new Observer<ArrayList>() {
            @Override
            public void onChanged(ArrayList arrayList) {
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
    protected void onDestroy() {
        super.onDestroy();
        mBaseListModel.onDestroy();
        mBaseListModel = null;
    }

    @Override
    public RecyclerView.LayoutManager getRcLayoutManager() {
        return new LinearLayoutManager(this);
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
    }


}
