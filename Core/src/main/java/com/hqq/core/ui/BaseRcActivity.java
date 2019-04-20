package com.hqq.core.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hqq.core.BaseCommonsKey;
import com.hqq.core.R;
import com.hqq.core.widget.CusPtrClassicFrameLayout;

import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * @Author : huangqiqiang
 * @Package : com.core.library
 * @FileName :   BaseRcActivity
 * @Date : 2018/11/5 0005  下午 4:23
 * @Descrive :
 * @Email :
 */
public abstract class BaseRcActivity<T extends BaseQuickAdapter> extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {
    protected RecyclerView mRcList;
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
    protected CusPtrClassicFrameLayout mPtrPullDown;


    @Override
    public int getViewId() {
        return 0;
    }

    /**
     * 重写了 getViewId    执行 getRootView
     *
     * @return
     */
    @Override
    protected View getRootView() {
        //父布局 LinearLayout
        View view = new RecyclerView(this);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        view.setId(R.id.rc_list);
        return view;
    }

    /**
     * 初始化  基础数据
     */
    @Override
    protected void initView() {
        mLayoutManager = getRcLayoutManager();
        if (mRcList == null) {
            mRcList = findViewById(R.id.rc_list);
        }
        mRcList.setOverScrollMode(View.OVER_SCROLL_NEVER);
        if (mPtrPullDown == null) {
            mPtrPullDown = findViewById(R.id.ptr_pull_down);
        }
        if (mPtrPullDown != null) {
            initPull();
        }
        mAdapter = getRcAdapter();

        mRcList.setLayoutManager(mLayoutManager);
        // 添加焦点
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
        return new LinearLayoutManager(this);
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
     * 初始化  data
     */
    protected abstract void initData();

    /**
     * 初始化下拉刷新
     */
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
                mAdapter.loadMoreComplete();
                onLoadMore();

            }
        });
    }

    /**
     * 标准的填充数据
     *
     * @param data
     */
    protected void fillingData(List data) {
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
            mLoadMoreFoodView = LayoutInflater.from(this).inflate(R.layout.layout_load_more_empty, null);
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
