package com.hqq.core.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hqq.core.BaseCommonsKey;
import com.hqq.core.widget.CusPtrClassicFrameLayout;
import com.hqq.core.R;

import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.ui
 * @FileName :   BaseRcFragment
 * @Date : 2018/11/23 0023  上午 11:47
 * @Descrive :
 * @Email :
 */
public abstract class BaseRcFragment<T extends BaseQuickAdapter> extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {
    protected RecyclerView mRcList;
    protected CusPtrClassicFrameLayout mPtrPullDown;

    protected T mAdapter;
    /**
     * 默认的填充数据
     *
     * @param data
     */

    View mLoadMoreFoodView;
    protected int mPageSize = BaseCommonsKey.PAGE_SIZE;
    protected int mPageCount = 1;
    RecyclerView.LayoutManager mLayoutManager;

    @Override
    public int getViewId() {
        return 0;
    }

    @Override
    protected View getRootView() {
        mRcList = new RecyclerView(getContext());
        //父布局 LinearLayout
        mRcList.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return mRcList;
    }

    @Override
    public void initBasic(Bundle savedInstanceState) {
        mLayoutManager = getRcLayoutManager();
        if (mPtrPullDown == null && findViewById(R.id.ptr_pull_down) != null) {
            mPtrPullDown = (CusPtrClassicFrameLayout) findViewById(R.id.ptr_pull_down);
        }
        if (mRcList == null) {
            mRcList = (RecyclerView) findViewById(R.id.rc_list);
            mRcList.setOverScrollMode(View.OVER_SCROLL_NEVER);
        }

        mRcList.setLayoutManager(mLayoutManager);
        mAdapter = getRcAdapter();
        mRcList.setAdapter(mAdapter);
        if (isShowLoadMore()) {
            mAdapter.setOnLoadMoreListener(this, mRcList);
        }
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
        initData();
    }

    private RecyclerView.LayoutManager getRcLayoutManager() {
        return initDefLayoutManager();
    }

    protected RecyclerView.LayoutManager initDefLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    public void onLoadMoreRequested() {
        onLoadMore();
    }

    protected boolean isShowLoadMore() {
        return false;
    }

    /**
     * 初始化  adapter
     *
     * @return
     */
    protected abstract T getRcAdapter();

    /**
     *
     */
    protected abstract void initData();

    protected void initPull() {

        mPtrPullDown.setPullToRefresh(false);
        mPtrPullDown.setKeepHeaderWhenRefresh(true);
        mPtrPullDown.setLastUpdateTimeRelateObject(this);
        mPtrPullDown.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, mRcList, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPageCount = 1;
                onLoadMore();

            }
        });
    }

    protected void fillingData(List data) {
        mLoadingView.dismiss();

        if (mPageCount == 1) {
            mAdapter.replaceData(data);
        } else {
            mAdapter.addData(data);
        }

        removeLoadMoreFood();

        if (mAdapter.getItemCount() == 0) {
            // 没有头部的时候才可以加这个
            mAdapter.setEmptyView(R.layout.layout_load_more_empty, mRcList);
        } else if (mAdapter.getData().size() == 0) {
            //这个是空数据的显示
            addLoadMoreFoodView();
        } else if (data.size() < mPageSize) {
            mAdapter.loadMoreEnd();
        } else {
            mPageCount++;
            mAdapter.loadMoreComplete();
        }
        if (mPtrPullDown != null) {
            mPtrPullDown.refreshComplete();
        }


    }

    private void addLoadMoreFoodView() {
        getLoadMoreFoodView();
        if (mAdapter.getFooterLayout() == null) {
            mAdapter.addFooterView(mLoadMoreFoodView);
        } else {
            LinearLayout adapterFoodView = mAdapter.getFooterLayout();
            if (adapterFoodView.getChildCount() == 0) {
                mAdapter.addFooterView(mLoadMoreFoodView);
            } else if (adapterFoodView.getChildAt(adapterFoodView.getChildCount() - 1) != mLoadMoreFoodView) {
                // 目前没测试 不知道会不会有问题
                mAdapter.addFooterView(mLoadMoreFoodView);
            }
        }
    }

    private void removeLoadMoreFood() {
        if (mLoadMoreFoodView != null && mAdapter.getFooterLayout() != null) {
            LinearLayout adapterFoodView = mAdapter.getFooterLayout();
            adapterFoodView.removeView(mLoadMoreFoodView);
        }
    }

    private View getLoadMoreFoodView() {

        if (mLoadMoreFoodView == null) {
            mLoadMoreFoodView = LayoutInflater.from(getContext()).inflate(R.layout.layout_load_more_empty, null);
        }
        return mLoadMoreFoodView;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    protected void onLoadMore() {

    }
}
