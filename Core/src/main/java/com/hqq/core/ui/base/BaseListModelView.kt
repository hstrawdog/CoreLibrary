package com.hqq.core.ui.base

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
import com.hqq.core.R
import com.hqq.core.utils.RegexUtils
import com.hqq.core.utils.ResourcesUtils
import com.hqq.core.widget.CusPtrClassicFrameLayout
import java.lang.ref.WeakReference

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui.model
 * @FileName :   BaseListModel
 * @Date : 2019/5/5 0005  上午 10:41
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 *
 * ---  这边不应该这样设计  应该当已 recycleView  adapter  为一组对象  这样扩充拓展性
 * 当一个页面出现两个列表加载的时候 就不会被局限
 *
 * 1. 点击事件的绑定交给Activity 来操作  adapter的点击事件绑定有两种 在多种布局的的情况下 点击事件写在adapter中可能会更合适一些
 *
 */
class BaseListModelView(var mBaseListModelView: IBaseListModelView<*>) {
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
            view.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            view.id = R.id.rc_list
            return view
        }
    }

    constructor(mBaseListModelView: IBaseListModelView<*>, iRootView: ICreateRootViewImpl<*>?) : this(mBaseListModelView) {
        iRootView?.let {
            this.context = WeakReference<Context>(it.activity)
            mBaseListModelView.listView = initRecycleView(it.rootView)
        }
    }

    var context: WeakReference<Context>? = null

    var viewEmptyFoot: View? = null
    var ptrPullDown: CusPtrClassicFrameLayout? = null

    /**
     * 空布局 layout Id
     *
     * @return
     */
    private val layoutEmptyView = R.layout.layout_load_more_empty

    /**
     * 应该要可以全局配置的 配置在xml中 可以替换
     *
     * @return string
     */
    private val emptyTextMessage: CharSequence?
         get() = ResourcesUtils.getString(R.string.def_empty_message)

    private val emptyImage: Int
         get() = R.mipmap.ic_empty_def

    /**
     * adapter
     *
     * @return
     */
    private val adapter: BaseQuickAdapter<*, *>
         get() = mBaseListModelView.baseAdapter!!


    /**
     * 初始化 RecycleView 等一切操作
     *
     * @param rcList
     * @param adapter
     * @param layoutManager
     */
    fun initRecycleView(view: View?): RecyclerView? {
        var listView = checkRecycleView(mBaseListModelView.listView, view)
        if (listView != null && adapter != null) {
            listView.overScrollMode = View.OVER_SCROLL_NEVER
            listView.layoutManager = mBaseListModelView.rcLayoutManager
            listView.adapter = adapter
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
            ptrPullDown!!.setPullToRefresh(false)
            ptrPullDown!!.setKeepHeaderWhenRefresh(true)
            ptrPullDown!!.setLastUpdateTimeRelateObject(this)
            ptrPullDown!!.setPtrHandler(object : PtrHandler {
                override fun onRefreshBegin(frame: PtrFrameLayout?) {
                    mBaseListModelView.onRefreshBegin()
                }

                override fun checkCanDoRefresh(frame: PtrFrameLayout, content: View, header: View): Boolean {
                    return PtrDefaultHandler.checkContentCanBePulledDown(frame, mBaseListModelView.listView, header)
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
    fun fillingData(data: Collection<Nothing>) {
        if (mBaseListModelView.pageCount == 1) {
            adapter.setList(data)
        } else {
            adapter.addData(data)
        }
        removeLoadMoreFood()
        when {
            // 没有头部的时候才可以加这个
            // 这边需要适配两种情况 空布局如果可以点击的话
            adapter.itemCount == 0 -> {
                adapter.setEmptyView(layoutEmptyView)
                initEmptyView(adapter.emptyLayout)
            }
            //这个是空数据的显示
            adapter.data.size == 0 -> {
                addLoadMoreFoodView()
                initEmptyView(viewEmptyFoot)
            }
            data.size < mBaseListModelView.pageSize -> {
                adapter.loadMoreModule.loadMoreEnd()
            }
            else -> {
                mBaseListModelView.addPageCount()
                adapter.loadMoreModule.loadMoreComplete();
            }
        }
        ptrPullDown?.let {
            it.refreshComplete()
        }
    }

    /**
     * 创建更多数据
     *
     * @return
     */
    private fun createLoadMoreFoodView(): View? {
        if (viewEmptyFoot == null) {
            context?.let {

                viewEmptyFoot = LayoutInflater.from(it.get()).inflate(R.layout.layout_load_more_empty, null)
            }
        }
        return viewEmptyFoot
    }

    /**
     * 添加 没有更多数据
     */
    private fun addLoadMoreFoodView() {
        createLoadMoreFoodView()
        if (adapter.footerLayout == null) {
            adapter.addFooterView(viewEmptyFoot!!)
        } else {
            val adapterFoodView = adapter.footerLayout
            if (adapterFoodView != null) {
                if (adapterFoodView.childCount == 0) {
                    adapter.addFooterView(viewEmptyFoot!!)
                } else if (adapterFoodView.getChildAt(adapterFoodView.childCount - 1) !== viewEmptyFoot) {
                    // 目前没测试 不知道会不会有问题
                    adapter.addFooterView(viewEmptyFoot!!)
                }
            }
        }
    }

    /**
     * 移除更更多数据
     */
    private fun removeLoadMoreFood() {
        if (viewEmptyFoot != null && adapter.footerLayout != null) {
            val adapterFoodView = adapter.footerLayout
            adapterFoodView!!.removeView(viewEmptyFoot)
        }
    }

    /**
     * m->v 的接口
     * k  adapter
     */
    interface IBaseListModelView<K : BaseQuickAdapter<*, *>> : OnLoadMoreListener {
        /**
         * 布局类型
         *
         * @return
         */
        val rcLayoutManager: RecyclerView.LayoutManager

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
        val baseAdapter: K

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


}