package com.hqq.example.ui.model;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hqq.example.R;
import com.hqq.example.widget.CusPtrClassicFrameLayout;

import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui.model
 * @FileName :   BaseListModel
 * @Date : 2019/5/5 0005  上午 10:41
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class BaseListModel {
    BaseListModelView mBaseListModelView;
    View mLoadMoreFoodView;
    protected CusPtrClassicFrameLayout mPtrPullDown;
    Context mContext;
    private int mDefLayoutEmptyViewById = R.layout.layout_load_more_empty;

    public BaseListModel(BaseListModelView baseListModelView, Context context) {
        mBaseListModelView = baseListModelView;
        mContext = context;
    }

    /**
     * 初始化 下拉刷新
     *
     * @param view
     */
    public void initPtrPullDown(View view) {
        if (mPtrPullDown == null) {
            mPtrPullDown = view.findViewById(R.id.ptr_pull_down);
        }
        if (mPtrPullDown != null) {
            initPull();
        }
    }

    /**
     * 统一 标准的填充数据
     * 需要考虑 使用工厂模式 对 填充进行抽离
     *
     * @param data
     */
    public void fillingData(List data) {
        if (mBaseListModelView.getPageCount() == 1) {
            mBaseListModelView.getAdapter().replaceData(data);
        } else {
            mBaseListModelView.getAdapter().addData(data);
        }
        removeLoadMoreFood();
        if (mBaseListModelView.getAdapter().getItemCount() == 0) {
            // 没有头部的时候才可以加这个
            // 这边需要适配两种情况 空布局如果可以点击的话
            mBaseListModelView.getAdapter().setEmptyView(getLayoutEmptyView(), mBaseListModelView.getListView());
        } else if (mBaseListModelView.getAdapter().getData().size() == 0) {
            //这个是空数据的显示
            addLoadMoreFoodView();
        } else if (data.size() < mBaseListModelView.getPageSize()) {
            mBaseListModelView.getAdapter().loadMoreEnd();
        } else {
            mBaseListModelView.addPageCount();
            mBaseListModelView.getAdapter().loadMoreComplete();
        }

        if (mPtrPullDown != null) {
            mPtrPullDown.refreshComplete();
        }

    }

    /**
     * 空布局 layout Id
     *
     * @return
     */
    private int getLayoutEmptyView() {
        return mDefLayoutEmptyViewById;
    }

    /**
     * 添加 没有更多数据
     */
    private void addLoadMoreFoodView() {
        createLoadMoreFoodView();
        if (mBaseListModelView.getAdapter().getFooterLayout() == null) {
            mBaseListModelView.getAdapter().addFooterView(mLoadMoreFoodView);
        } else {
            LinearLayout adapterFoodView = mBaseListModelView.getAdapter().getFooterLayout();
            if (adapterFoodView.getChildCount() == 0) {
                mBaseListModelView.getAdapter().addFooterView(mLoadMoreFoodView);
            } else if (adapterFoodView.getChildAt(adapterFoodView.getChildCount() - 1) != mLoadMoreFoodView) {
                // 目前没测试 不知道会不会有问题
                mBaseListModelView.getAdapter().addFooterView(mLoadMoreFoodView);
            }
        }
    }

    /**
     * 移除更更多数据
     */
    private void removeLoadMoreFood() {
        if (mLoadMoreFoodView != null && mBaseListModelView.getAdapter().getFooterLayout() != null) {
            LinearLayout adapterFoodView = mBaseListModelView.getAdapter().getFooterLayout();
            adapterFoodView.removeView(mLoadMoreFoodView);
        }
    }

    /**
     * 创建更多数据
     *
     * @return
     */
    private View createLoadMoreFoodView() {
        if (mLoadMoreFoodView == null) {
            mLoadMoreFoodView = LayoutInflater.from(mContext).inflate(R.layout.layout_load_more_empty, null);
        }
        return mLoadMoreFoodView;
    }


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
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, mBaseListModelView.getListView(), header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mBaseListModelView.onRefreshBegin();
            }
        });
    }

    /**
     * 初始化 RecycleView 等一切操作
     *
     * @param rcList
     * @param adapter
     * @param layoutManager
     * @param requestLoadMoreListener
     * @param onItemClickListener
     * @param onItemChildClickListener
     */
    public void initRecycleView(RecyclerView rcList, BaseQuickAdapter adapter, RecyclerView.LayoutManager layoutManager,
                                BaseQuickAdapter.RequestLoadMoreListener requestLoadMoreListener,
                                BaseQuickAdapter.OnItemClickListener onItemClickListener,
                                BaseQuickAdapter.OnItemChildClickListener onItemChildClickListener) {
        try {
            if (null == rcList) {
                throw new Exception("  listView is null ");
            }

            if (adapter == null) {
                throw new Exception("adapter is null ");
            }

            rcList.setOverScrollMode(View.OVER_SCROLL_NEVER);
            rcList.setLayoutManager(layoutManager);
            // 添加焦点
            rcList.setAdapter(adapter);
            if (mBaseListModelView.isShowLoadMore()) {
                adapter.setOnLoadMoreListener(requestLoadMoreListener, rcList);
            }
            adapter.setOnItemClickListener(onItemClickListener);
            adapter.setOnItemChildClickListener(onItemChildClickListener);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建一个 rootView = recycleView
     *
     * @param context
     * @return
     */
    public View createRecycleView(Context context) {
        //正常情况下父布局应该会是LinearLayout
        return createRecycleView(context, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public View createRecycleView(Context context, int height) {
        View view = new RecyclerView(context);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
        view.setId(R.id.rc_list);
        return view;
    }

    /**
     * 销毁
     */
    public void onDestroy() {
        mContext = null;
        mPtrPullDown = null;
    }

    public RecyclerView checkRecycleView(RecyclerView rcList, View view) {
        if (rcList == null) {
            rcList = view.findViewById(R.id.rc_list);
        }

        return rcList;
    }

    /**
     * m->v 的接口
     * k  adapter
     */
    public interface BaseListModelView<K extends BaseQuickAdapter> {

        /**
         * 分页下标
         *
         * @return
         */
        int getPageCount();

        /**
         * 分页大小
         *
         * @return
         */
        int getPageSize();

        /**
         * 进入下一页
         *
         * @return
         */
        int addPageCount();

        /**
         * 是否加载更多
         *
         * @return
         */
        boolean isShowLoadMore();

        /**
         * 开始下拉刷新
         */
        void onRefreshBegin();

        /**
         * 获取adapter
         *
         * @return
         */
        K getAdapter();

        K initAdapter();

        /**
         * 获取 recycleView
         *
         * @return
         */
        ViewGroup getListView();

        /**
         * 初始化数据
         */
        void initData();

        RecyclerView.LayoutManager getRcLayoutManager();

    }

}
