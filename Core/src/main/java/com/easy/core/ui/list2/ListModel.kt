package com.easy.core.ui.list2


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.QuickAdapterHelper
import com.chad.library.adapter4.loadState.LoadState
import com.chad.library.adapter4.loadState.trailing.TrailingLoadStateAdapter
import com.easy.core.CoreConfig
import com.easy.core.R
import com.easy.core.lifecycle.BaseLifecycleEventObserver
import com.easy.core.lifecycle.BaseLifecycleObserver
import com.easy.core.ui.base.BaseActivity
import com.easy.core.ui.base.BaseFragment
import com.easy.core.ui.base.RootViewImpl
import com.easy.core.utils.ResourcesUtils
import com.easy.core.utils.data.DataUtils
import com.easy.core.utils.log.LogUtils
import com.easy.core.widget.CusPtrClassicFrameLayout
import `in`.srain.cube.views.ptr.PtrDefaultHandler
import `in`.srain.cube.views.ptr.PtrFrameLayout
import `in`.srain.cube.views.ptr.PtrHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
abstract class ListModel<T : Any> : BaseLifecycleObserver {

    private constructor()

    constructor(activity: BaseActivity) : this() {
        LogUtils.e("listModel   constructor    BaseActivity11111")

        context = activity
        recycleView = initRecycleView(activity.rootViewImpl.rootView)
        activity.lifecycle.coroutineScope.launch {
            withContext(Dispatchers.Main) {
                activity.lifecycle.addObserver(this@ListModel)
            }
        }
        LogUtils.e("listModel   constructor    BaseActivity2222")

    }

    constructor(fragment: BaseFragment) : this() {

        LogUtils.e("listModel   constructor    BaseFragment")
        context = fragment.context
        recycleView = initRecycleView(fragment.rootViewImpl.rootView)
        fragment.lifecycle.coroutineScope.launch {
            withContext(Dispatchers.Main) {
                fragment.lifecycle.addObserver(this@ListModel)
            }
        }
    }

    /**
     * 布局类型
     *
     * @return
     */
    open var layoutManager: RecyclerView.LayoutManager? = null

    /**
     *  上下文
     */
    var context: Context? = null

    /**
     * 获取adapter
     *
     * @return
     */
    abstract var adapter: BaseQuickAdapter<T, *>

    /**
     *  是否  开启 加载更多
     */
    var isLoadMore: Boolean = true

    /**
     * 下拉刷新对象
     */
    var ptrPullDown: CusPtrClassicFrameLayout? = null

    /**
     * 分页 管理对象
     */
    var helper: QuickAdapterHelper? = null

    /**
     *  空布局 layout Id
     */
    var layoutEmptyView = R.layout.layout_load_more_empty

    /**
     * 应该要可以全局配置的 配置在xml中 可以替换
     */
    var emptyTextMessage: CharSequence = ResourcesUtils.getString(R.string.def_empty_message)

    /**
     *  空布局图片
     */
    var emptyImage: Int = R.mipmap.ic_empty_def

    /**
     *  列表
     */
    var recycleView: RecyclerView? = null

    /**
     * 分页下标
     *
     * @return
     */
    var pageCount: Int = 1

    /**
     * 分页大小
     *
     * @return
     */
    var pageSize: Int = 10


    fun createRecycleView(context: Context): RecyclerView {
        val view = RecyclerView(context)
        view.layoutParams =
            LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        view.id = R.id.rc_list
        return view
    }

    /**
     * 初始化 RecycleView 等一切操作
     * @param view View?
     * @return RecyclerView?
     */
    fun initRecycleView(view: View?): RecyclerView {
        var listView = checkRecycleView(recycleView, view)
        if (listView == null) {
            listView = createRecycleView(context!!)
        }

        listView.overScrollMode = View.OVER_SCROLL_NEVER


        initPtrPullDown(view)
        return listView
    }

    /**
     * 初始化 下拉刷新
     *
     * @param view
     */
    private fun initPtrPullDown(view: View?) {
        if (ptrPullDown == null) {
            view?.findViewById<CusPtrClassicFrameLayout>(R.id.ptr_pull_down)
                ?.let {
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
                    onRefreshBegin()
                }

                override fun checkCanDoRefresh(frame: PtrFrameLayout, content: View, header: View): Boolean {
                    return PtrDefaultHandler.checkContentCanBePulledDown(frame, recycleView, header)
                }
            })

        }


    }


    var isBuild = false

    fun build() {
        if (adapter == null) {
            throw IllegalStateException("adapter 未初始化")
        }
        adapter.isStateViewEnable = true
        recycleView?.layoutManager = layoutManager
        if (isLoadMore) {
            helper = adapter?.let {
                QuickAdapterHelper.Builder(it)
                    .setTrailingLoadStateAdapter(object : TrailingLoadStateAdapter.OnTrailingListener {
                        override fun onFailRetry() {
                            onFailRetry()
                        }

                        override fun onLoad() {
                            onLoadMore()
                        }
                    })
                    .build()
            }
            recycleView?.adapter = helper?.adapter
        } else {
            recycleView?.adapter = adapter

        }
        isBuild = true
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
    fun fillingData(data: List<T>) {
        if (isBuild == false) {
            build()
        }


        if (pageCount == 1) {
            adapter?.submitList(data)
        } else {
            adapter?.addAll(data)
        }
        when {
            // 没有头部的时候才可以加这个
            adapter?.itemCount == 0 -> {
                CoreConfig.get().currActivity?.let {
                    var emptyView = LayoutInflater.from(it)
                        .inflate(layoutEmptyView, null)
                    initEmptyView(emptyView)
                    adapter?.stateView = emptyView
                }
            }

            else -> {
                // 只有 adapter实现了 LoadMoreModule 才可以实现上啦加载
                if (data.size < pageSize) {
                    // 设置状态为未加载，并且没有分页数据了
                    helper?.trailingLoadState = LoadState.NotLoading(true)
                } else {
//                    mBaseListModelView.addPageCount()
                    //  设置状态为未加载，并且还有分页数据
                    helper?.trailingLoadState = LoadState.NotLoading(false)
                }
            }
        }
        ptrPullDown?.refreshComplete()
    }

    /**
     *  加载 失败
     */
    fun loadMoreError() {
        ptrPullDown?.refreshComplete()
        helper?.trailingLoadState = LoadState.Error(RuntimeException("load fail"))
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
        return view?.findViewById<RecyclerView>(R.id.rc_list)
    }

    override fun onAny() {

    }


    override fun onCrete() {
        LogUtils.e(" ListModel  onCrete")
        // 日志 上看去是是在 Activity 之后执行的

    }

    override fun onDestroy() {
        context = null
        recycleView = null

    }

    override fun onPause() {
    }

    override fun onResume() {
    }

    override fun onStart() {
    }

    override fun onStop() {
    }

    /**
     *  加载失败
     */
    fun onFailRetry() {
        loadData()
    }

    /**
     * 开始下拉刷新
     */
    fun onRefreshBegin() {
        pageCount = 1
        loadData()
    }

    /**
     *  加载更多
     */
    fun onLoadMore() {
        pageCount++
        loadData()

    }

    abstract fun loadData()
}
