package com.easy.core.ui.compose

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.CallSuper
import androidx.compose.runtime.Composable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter4.loadState.LoadState
import com.easy.core.BaseCommonsKey
import com.easy.core.R
import com.easy.core.ui.list.BaseListModel
import com.easy.core.ui.list.BaseListModel.IBaseListModelView

/**
 * Compose 宿主下的列表页基类。
 *
 * 设计目标：
 * 1. 继续沿用 RecyclerView + Adapter 的列表实现。
 * 2. 尽量保持 BaseListActivity 的使用习惯，便于页面平滑迁移。
 * 3. 只把页面壳子切到新的 compose 根布局路径。
 */
abstract class BaseComposeListActivity : BaseComposeActivity(), IBaseListModelView {

    override var pageSize = BaseCommonsKey.PAGE_SIZE

    override var pageCount = 1

    override var isLoadMore: Boolean = true

    /**
     * 默认使用竖向线性布局管理器。
     */
    override var layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)

    override var listView: RecyclerView? = null

    override lateinit var listModel: BaseListModel

    private var listContentView: View? = null

    /**
     * 与 BaseListActivity 保持一致。
     * 默认只创建 RecyclerView；如果页面需要复合布局，可以返回自定义 layout。
     */
    protected open fun getLayoutViewId(): Int = 0

    /**
     * 保留给子类自定义内容 View。
     * 默认在未提供 layoutId 时返回一个 RecyclerView。
     */
    protected open fun getLayoutView(parent: ViewGroup): View? {
        return if (getLayoutViewId() <= 0) {
            createDefaultRecyclerView()
        } else {
            null
        }
    }

    protected open fun createDefaultRecyclerView(): RecyclerView {
        return RecyclerView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            id = R.id.rc_list
        }
    }

    /**
     * 列表页默认走 ViewFactory 内容模式，继续承接 RecyclerView 体系。
     */
    override fun provideContentSpec(): ContentSpec {
        return ContentSpec.ViewFactory { parent ->
            buildListContentView(parent)
        }
    }

    /**
     * 列表页内容由 RecyclerView/ViewFactory 提供，这里不再渲染额外 Compose 内容。
     */
    @Composable
    final override fun InitComposeView() = Unit

    /**
     * 按“自定义 View -> layoutId -> 默认 RecyclerView”顺序构建内容区域。
     */
    private fun buildListContentView(parent: ViewGroup): View {
        val customView = getLayoutView(parent)
        if (customView != null) {
            listContentView = customView
            return customView
        }

        if (getLayoutViewId() > 0) {
            return LayoutInflater.from(parent.context)
                .inflate(getLayoutViewId(), parent, false)
                .also { listContentView = it }
        }

        return createDefaultRecyclerView().also { listContentView = it }
    }

    /**
     * 在列表容器准备完成后创建 listModel 并触发初始化数据流程。
     */
    @CallSuper
    override fun initView() {
        listModel = BaseListModel(this, listContentView)
        initData()
    }

    /**
     * 列表成功加载下一页后更新页码。
     */
    override fun addPageCount() {
        pageCount += 1
    }

    /**
     * 下拉刷新时重置分页状态并转交给统一加载更多入口。
     */
    override fun onRefreshBegin() {
        pageCount = 1
        listModel.helper?.leadingLoadState = LoadState.NotLoading(true)
        onLoadMore()
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
    }

    /**
     * 留给子类接入分页加载逻辑。
     */
    override fun onLoadMore() {}
}
