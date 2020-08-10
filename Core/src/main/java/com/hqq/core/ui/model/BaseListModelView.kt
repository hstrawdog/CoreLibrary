package com.hqq.core.ui.model

import `in`.srain.cube.views.ptr.PtrDefaultHandler
import `in`.srain.cube.views.ptr.PtrFrameLayout
import `in`.srain.cube.views.ptr.PtrHandler
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter.RequestLoadMoreListener
import com.hqq.core.R
import com.hqq.core.utils.RegexUtils
import com.hqq.core.utils.ResourcesUtils
import com.hqq.core.utils.log.LogUtils
import com.hqq.core.widget.CusPtrClassicFrameLayout

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui.model
 * @FileName :   BaseListModel
 * @Date : 2019/5/5 0005  上午 10:41
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 *
 *
 *
 *
 *
 *
 * ---  这边不应该这样设计  应该当已 recycleView  adapter  为一组对象  这样扩充拓展性
 * 当一个页面出现两个列表加载的时候 就不会被局限
 */
class BaseListModelView(var mBaseListModelView: IBaseListModelView<*>, var mContext: Context?) {
    var mViewEmptyFoot: View? = null
    lateinit var mPtrPullDown: CusPtrClassicFrameLayout

    /**
     * 空布局 layout Id
     *
     * @return
     */
    private val layoutEmptyView = R.layout.layout_load_more_empty

    /**
     * 初始化 下拉刷新
     *
     * @param view
     */
    fun initPtrPullDown(view: View?) {
        if (mPtrPullDown == null) {
            mPtrPullDown = view!!.findViewById(R.id.ptr_pull_down) as (CusPtrClassicFrameLayout)
        }
        if (mPtrPullDown != null) {
            initPull()
        }
    }

    /**
     * 初始化下拉刷新
     */
    protected fun initPull() {
        mPtrPullDown.setPullToRefresh(false)
        mPtrPullDown.setKeepHeaderWhenRefresh(true)
        mPtrPullDown!!.setLastUpdateTimeRelateObject(this)
        mPtrPullDown.setPtrHandler(object : PtrHandler {
            override fun checkCanDoRefresh(frame: PtrFrameLayout, content: View, header: View): Boolean {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, mBaseListModelView.listView, header)
            }

            override fun onRefreshBegin(frame: PtrFrameLayout) {
                mBaseListModelView.onRefreshBegin()
            }
        })
    }

    /**
     * 初始化 空布局
     *
     * @param emptyView
     */
    private fun initEmptyView(emptyView: View?) {
        val tvRefresh = emptyView!!.findViewById<TextView>(R.id.tv_Refresh)
        val tvEmptyMessage = emptyView.findViewById<TextView>(R.id.tv_empty_message)
        val ivEmpty = emptyView.findViewById<ImageView>(R.id.iv_empty)
        if (RegexUtils.unNull(tvRefresh)) {
            tvRefresh.visibility = View.GONE
        }
        if (RegexUtils.unNull(tvEmptyMessage)) {
            tvEmptyMessage.text = emptyTextMessage
        }
        if (RegexUtils.unNull(ivEmpty)) {
            ivEmpty.setImageResource(emptyImage)
        }
    }

    /**
     * 统一 标准的填充数据
     * 需要考虑 使用工厂模式 对 填充进行抽离
     *
     * @param data
     */
    fun fillingData(data: MutableCollection<out Nothing>) {
        if (mBaseListModelView.pageCount == 1) {
            adapter.replaceData(data)
        } else {
            adapter.addData(data)
        }
        removeLoadMoreFood()
        if (adapter.itemCount == 0) {
            // 没有头部的时候才可以加这个
            // 这边需要适配两种情况 空布局如果可以点击的话
            adapter.setEmptyView(layoutEmptyView, mBaseListModelView.listView)
            initEmptyView(adapter.emptyView)
        } else if (adapter.data.size == 0) {
            //这个是空数据的显示
            addLoadMoreFoodView()
            initEmptyView(mViewEmptyFoot)
        } else if (data.size < mBaseListModelView.pageSize) {
            adapter.loadMoreEnd()
        } else {
            mBaseListModelView.addPageCount()
            adapter.loadMoreComplete()
        }
        if (mPtrPullDown != null) {
            mPtrPullDown.refreshComplete()
        }
    }

    /**
     * 创建更多数据
     *
     * @return
     */
    private fun createLoadMoreFoodView(): View? {
        if (mViewEmptyFoot == null) {
            mViewEmptyFoot = LayoutInflater.from(mContext).inflate(R.layout.layout_load_more_empty, null)
        }
        return mViewEmptyFoot
    }

    /**
     * 应该要可以全局配置的 配置在xml中 可以替换
     *
     * @return string
     */
    private val emptyTextMessage: CharSequence?
        private get() = ResourcesUtils.getString(R.string.def_empty_message)

    private val emptyImage: Int
        private get() = R.mipmap.ic_empty_def

    /**
     * 添加 没有更多数据
     */
    private fun addLoadMoreFoodView() {
        createLoadMoreFoodView()
        if (adapter.footerLayout == null) {
            adapter.addFooterView(mViewEmptyFoot)
        } else {
            val adapterFoodView = adapter.footerLayout
            if (adapterFoodView.childCount == 0) {
                adapter.addFooterView(mViewEmptyFoot)
            } else if (adapterFoodView.getChildAt(adapterFoodView.childCount - 1) !== mViewEmptyFoot) {
                // 目前没测试 不知道会不会有问题
                adapter.addFooterView(mViewEmptyFoot)
            }
        }
    }

    /**
     * 移除更更多数据
     */
    private fun removeLoadMoreFood() {
        if (mViewEmptyFoot != null && adapter.footerLayout != null) {
            val adapterFoodView = adapter.footerLayout
            adapterFoodView.removeView(mViewEmptyFoot)
        }
    }

    /**
     * 初始化 RecycleView 等一切操作
     *
     * @param rcList
     * @param adapter
     * @param layoutManager
     */
    fun initRecycleView(rcList: RecyclerView?, adapter: BaseQuickAdapter<*, *>?, layoutManager: RecyclerView.LayoutManager?) {
        try {
            if (null == rcList) {
                LogUtils.e(Exception("  listView is null "))
            }
            if (adapter == null) {
                LogUtils.e(Exception("adapter is null "))
            }
            rcList!!.overScrollMode = View.OVER_SCROLL_NEVER
            rcList.layoutManager = layoutManager
            // 添加焦点
            rcList.adapter = adapter
            if (mBaseListModelView.isShowLoadMore) {
                adapter!!.setOnLoadMoreListener(mBaseListModelView, rcList)
            }
            adapter!!.setOnItemClickListener(mBaseListModelView)
            adapter.setOnItemChildClickListener(mBaseListModelView)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 销毁
     */
    fun onDestroy() {
        mContext = null
    }

    /**
     * 检查是否有是否存在
     *
     * @param rcList
     * @param view
     * @return
     */
    fun checkRecycleView(rcList: RecyclerView?, view: View?): RecyclerView? {
        var rcList = rcList
        if (rcList == null) {
            rcList = view!!.findViewById(R.id.rc_list)
        }
        return rcList
    }

    /**
     * adapter
     *
     * @return
     */
    private val adapter: BaseQuickAdapter<*, *>
        private get() = mBaseListModelView.adapter!!

    /**
     * m->v 的接口
     * k  adapter
     */
    interface IBaseListModelView<K : BaseQuickAdapter<*, *>?> : RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {
        /**
         * 分页下标
         *
         * @return
         */
        val pageCount: Int

        /**
         * 分页大小
         *
         * @return
         */
        val pageSize: Int

        /**
         * 进入下一页
         *
         * @return
         */
        fun addPageCount()

        /**
         * 是否加载更多
         *
         * @return
         */
        val isShowLoadMore: Boolean

        /**
         * 开始下拉刷新
         */
        fun onRefreshBegin()

        /**
         * 获取adapter
         *
         * @return
         */
        val adapter: K
        fun initAdapter(): K

        /**
         * 获取 recycleView
         *
         * @return
         */
        val listView: ViewGroup?

        /**
         * 初始化数据
         */
        fun initData()

        /**
         * 布局类型
         *
         * @return
         */
        val rcLayoutManager: RecyclerView.LayoutManager
    }

    companion object {
        /**
         * 创建一个RecycleView
         *
         * @param context
         * @param height
         * @return
         */
        /**
         * 创建一个 rootView = recycleView
         *
         * @param context
         * @return
         */
        @JvmOverloads
        fun createRecycleView(context: Context?, height: Int = ViewGroup.LayoutParams.MATCH_PARENT): View {
            val view = RecyclerView(context!!)
            view.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height)
            view.id = R.id.rc_list
            return view
        }
    }

}