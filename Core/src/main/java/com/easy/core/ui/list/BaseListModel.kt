package com.easy.core.ui.list

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
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.chad.library.adapter.base.module.LoadMoreModule
import com.easy.core.R
import com.easy.core.ui.base.RootViewImpl
import com.easy.core.utils.data.DataUtils
import com.easy.core.utils.ResourcesUtils
import com.easy.core.widget.CusPtrClassicFrameLayout
import java.lang.ref.WeakReference

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.ui.model
 * @FileName :   BaseListModel
 * @Date : 2019/5/5 0005  上午 10:41
 * @Email : qiqiang213@gmail.com
 * @Describe :
 *
 * ---  这边不应该这样设计  应该当已 recycleView  adapter  为一组对象  这样扩充拓展性
 * 当一个页面出现两个列表加载的时候 就不会被局限
 *
 * 1. 点击事件的绑定交给Activity 来操作  adapter的点击事件绑定有两种 在多种布局的的情况下 点击事件写在adapter中可能会更合适一些
 *
 */
class BaseListModel(var mBaseListModelView: IBaseListModelView) {

    var context: WeakReference<Context>? = null

    var viewEmptyFoot: View? = null

    var ptrPullDown: CusPtrClassicFrameLayout? = null

    /**
     *  空布局 layout Id
     */
    var layoutEmptyView = R.layout.layout_load_more_empty

    /**
     * 应该要可以全局配置的 配置在xml中 可以替换
     */
    var emptyTextMessage: CharSequence = ResourcesUtils.getString(R.string.def_empty_message)

    var emptyImage: Int = R.mipmap.ic_empty_def

    /**
     * adapter
     */
    private val _adapter get() = mBaseListModelView.adapter

    constructor(mBaseListModelView: IBaseListModelView, iRootView: RootViewImpl) : this(
        mBaseListModelView
    ) {
        this.context = WeakReference<Context>(iRootView.activity?.get())
        mBaseListModelView.listView = initRecycleView(iRootView.rootView)
    }

    /**
     * 初始化 RecycleView 等一切操作
     * @param view View?
     * @return RecyclerView?
     */
    private fun initRecycleView(view: View?): RecyclerView? {
        val listView = checkRecycleView(mBaseListModelView.listView, view)
        if (listView != null) {
            listView.overScrollMode = View.OVER_SCROLL_NEVER
            listView.layoutManager = mBaseListModelView.layoutManager
            listView.adapter = _adapter
        }
        initPtrPullDown(view)
        return listView
    }

    /**
     * 检查是否有是否存在
     *
     * @param recyclerView
     * @param view
     * @return
     */
    private fun checkRecycleView(recyclerView: RecyclerView?, view: View?): RecyclerView? {
        recyclerView?.let {
            return@let
        }
        return view?.findViewById(R.id.rc_list)
    }

    /**
     * 初始化 下拉刷新
     *
     * @param view
     */
    private fun initPtrPullDown(view: View?) {
        if (ptrPullDown == null) {
            view?.findViewById<CusPtrClassicFrameLayout>(R.id.ptr_pull_down)?.let {
                ptrPullDown = it
                initPull()
            }
        }
    }

    /**
     * 初始化下拉刷新
     */
    private fun initPull() {
        ptrPullDown?.let {
            it.isPullToRefresh = false
            it.isKeepHeaderWhenRefresh = true
            it.setLastUpdateTimeRelateObject(this)
            it.setPtrHandler(object : PtrHandler {
                override fun onRefreshBegin(frame: PtrFrameLayout?) {
                    mBaseListModelView.onRefreshBegin()
                }

                override fun checkCanDoRefresh(
                    frame: PtrFrameLayout, content: View, header: View
                ): Boolean {
                    return PtrDefaultHandler.checkContentCanBePulledDown(
                        frame, mBaseListModelView.listView, header
                    )
                }
            })

        }


    }

    /**
     * 初始化 空布局
     *
     * @param emptyView
     */
    private fun initEmptyView(emptyView: View?) {
        if (emptyView == null) {
            return
        }
        val tvRefresh = emptyView.findViewById<TextView>(R.id.tv_Refresh)
        val tvEmptyMessage = emptyView.findViewById<TextView>(R.id.tv_empty_message)
        val ivEmpty = emptyView.findViewById<ImageView>(R.id.iv_empty)
        if (DataUtils.checkUnNull(tvRefresh)) {
            tvRefresh.visibility = View.GONE
        }
        if (DataUtils.checkUnNull(tvEmptyMessage)) {
            tvEmptyMessage.text = emptyTextMessage
        }
        if (DataUtils.checkUnNull(ivEmpty)) {
            ivEmpty.setImageResource(emptyImage)
        }
    }

    /**
     * 统一 标准的填充数据
     * 需要考虑 使用工厂模式 对 填充进行抽离
     *
     * @param data
     */
    fun fillingData(data: Collection<*>) {
        if (mBaseListModelView.pageCount == 1) {
            _adapter.setList(data as Collection<Nothing>)
        } else {
            _adapter.addData(data as Collection<Nothing>)
        }
        removeFoodEmptyView()
        when {
            // 没有头部的时候才可以加这个
            // 这边需要适配两种情况 空布局如果可以点击的话
            _adapter.itemCount == 0 -> {
                _adapter.setEmptyView(layoutEmptyView)
                initEmptyView(_adapter.emptyLayout)
            }
            //这个是空数据的显示
            _adapter.data.size == 0 -> {
                addFoodEmptyView()
                initEmptyView(viewEmptyFoot)
            }
            else -> {
                // 只有 adapter实现了 LoadMoreModule 才可以实现上啦加载
                if (this._adapter is LoadMoreModule) {
                    if (data.size < mBaseListModelView.pageSize) {
                        _adapter.loadMoreModule.loadMoreEnd()
                    } else {
                        mBaseListModelView.addPageCount()
                        _adapter.loadMoreModule.loadMoreComplete()
                    }
                }
            }
        }
        ptrPullDown?.refreshComplete()
    }

    /**
     * 创建更多数据
     *
     * @return
     */
    private fun createLoadMoreFoodView(): View? {
        if (viewEmptyFoot == null) {
            context?.let {
                viewEmptyFoot =
                    LayoutInflater.from(it.get()).inflate(R.layout.layout_load_more_empty, null)
            }
        }
        return viewEmptyFoot
    }

    /**
     * 添加 没有更多数据
     */
    private fun addFoodEmptyView() {
        createLoadMoreFoodView()
        if (_adapter.footerLayout == null) {
            viewEmptyFoot?.let { _adapter.addFooterView(it) }
        } else {
            val adapterFoodView = _adapter.footerLayout
            if (adapterFoodView != null) {
                if (adapterFoodView.childCount == 0) {
                    viewEmptyFoot?.let { _adapter.addFooterView(it) }
                } else if (adapterFoodView.getChildAt(adapterFoodView.childCount - 1) !== viewEmptyFoot) {
                    // 目前没测试 不知道会不会有问题
                    viewEmptyFoot?.let { _adapter.addFooterView(it) }
                }
            }
        }
    }

    /**
     * 移除更更多数据
     */
    private fun removeFoodEmptyView() {
        if (viewEmptyFoot != null) {
            _adapter.footerLayout?.removeView(viewEmptyFoot)
        }
    }

    /**
     *  检查是否是空数据了
     */
    fun checkAdapterDataIsNull() {
        if (_adapter.data.size == 0) {
            _adapter.setEmptyView(layoutEmptyView)
            initEmptyView(_adapter.emptyLayout)
        }
    }

    fun loadMoreError() {
        ptrPullDown?.refreshComplete()
        _adapter.loadMoreModule.loadMoreFail()
    }


    /**
     * m->v 的接口
     * k  adapter
     */
    interface IBaseListModelView : OnLoadMoreListener {
        /**
         * List列表模型
         */
        var listModel: BaseListModel

        /**
         * 布局类型
         *
         * @return
         */
        var layoutManager: RecyclerView.LayoutManager

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
         * 获取adapter
         *
         * @return
         */
        val adapter: BaseQuickAdapter<*, *>

        /**
         * 获取 recycleView
         *
         * @return
         */
        var listView: RecyclerView?

        /**
         * 进入下一页
         *
         * @return
         */
        fun addPageCount()

        /**
         * 开始下拉刷新
         */
        fun onRefreshBegin()

        /**
         * 初始化数据
         *  initData 也是执行在 onCreate 中
         *
         */
        fun initData()

    }

    companion object {
        /**
         * 创建一个 rootView = recycleView
         *
         * @param context
         * @return
         */
        @JvmOverloads
        fun createRecycleView(context: Context): View {
            val view = RecyclerView(context)
            view.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
            )
            view.id = R.id.rc_list
            return view
        }

    }
}