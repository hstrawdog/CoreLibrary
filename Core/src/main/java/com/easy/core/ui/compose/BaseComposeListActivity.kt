package com.easy.core.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.easy.core.ui.compose.refresh.SmartSwipeRefresh
import com.easy.core.ui.compose.refresh.SmartSwipeRefreshDefaultHeader
import com.easy.core.ui.compose.refresh.SmartSwipeRefreshState
import com.easy.core.ui.compose.refresh.SmartSwipeStateFlag
import com.easy.core.ui.compose.refresh.rememberSmartSwipeRefreshState

data class ComposeListPaddingSpec(
    val start: Int = 12,
    val top: Int = 12,
    val end: Int = 12,
    val bottom: Int = 12
)

data class ComposeListLoadMoreSpec(
    val enabled: Boolean = false,
    val triggerThreshold: Int = 1
)

/**
 * Compose-first 列表页基类。
 *
 * refresh 直接基于 ComposeSmartRefresh，
 * 当前只先服务 LazyColumn 场景。
 */
abstract class BaseComposeListActivity<T> : BaseComposeActivity() {

    protected val items = mutableStateListOf<T>()

    protected var isLoadingMore by mutableStateOf(false)
        private set

    protected var isRefreshing by mutableStateOf(false)
        private set

    protected var hasMore by mutableStateOf(true)
        private set

    protected var loadMoreErrorText by mutableStateOf<CharSequence?>(null)
        private set

    private var smartRefreshStateRef: SmartSwipeRefreshState? = null

    protected open fun provideListBackgroundColor(): Color = Color.Transparent

    protected open fun provideListPaddingSpec(): ComposeListPaddingSpec = ComposeListPaddingSpec()

    protected open fun provideItemSpacing(): Int = 8

    protected open fun provideLoadMoreSpec(): ComposeListLoadMoreSpec = ComposeListLoadMoreSpec()

    protected open fun enablePullRefresh(): Boolean = false

    protected open fun provideToolbarScrollSync(): Boolean = false

    protected open fun provideToolbarScrollThreshold(): Int = 96

    protected open fun itemKey(index: Int, item: T): Any = index

    protected open fun itemContentType(index: Int, item: T): Any? = null

    protected open fun shouldShowLoadMoreEndFooter(): Boolean = false

    protected open fun loadMoreLoadingText(): CharSequence = "加载中..."

    protected open fun loadMoreEndText(): CharSequence = "没有更多了"

    protected open fun emptyText(): CharSequence = "暂无数据"

    protected open fun initData() {}

    protected fun submitItems(newItems: List<T>) {
        items.clear()
        items.addAll(newItems)
    }

    protected fun appendItems(newItems: List<T>) {
        items.addAll(newItems)
    }

    protected fun clearItems() {
        items.clear()
    }

    protected fun resetLoadMore(hasMore: Boolean = true) {
        isLoadingMore = false
        this.hasMore = hasMore
        loadMoreErrorText = null
    }

    protected fun startRefresh() {
        if (isRefreshing || isLoadingMore) {
            return
        }
        isRefreshing = true
        loadMoreErrorText = null
        onRefresh()
    }

    protected fun finishRefresh() {
        isRefreshing = false
        dispatchRefreshResult(SmartSwipeStateFlag.SUCCESS)
    }

    protected fun finishRefreshError() {
        isRefreshing = false
        dispatchRefreshResult(SmartSwipeStateFlag.ERROR)
    }

    protected fun finishRefresh(
        newItems: List<T>,
        hasMore: Boolean = true
    ) {
        submitItems(newItems)
        isRefreshing = false
        dispatchRefreshResult(SmartSwipeStateFlag.SUCCESS)
        resetLoadMore(hasMore = hasMore)
    }

    private fun dispatchRefreshResult(flag: SmartSwipeStateFlag) {
        val state = smartRefreshStateRef ?: return
        if (state.refreshFlag == SmartSwipeStateFlag.REFRESHING || state.indicatorOffset > 0f || !state.animateIsOver) {
            state.refreshFlag = flag
        }
    }

    protected fun finishLoadMore(
        hasMore: Boolean = this.hasMore,
        errorText: CharSequence? = null
    ) {
        isLoadingMore = false
        this.hasMore = hasMore
        loadMoreErrorText = errorText
    }

    protected fun appendPage(
        pageItems: List<T>,
        hasMore: Boolean,
        errorText: CharSequence? = null
    ) {
        items.addAll(pageItems)
        finishLoadMore(hasMore = hasMore, errorText = errorText)
    }

    protected open fun onRefresh() {}

    protected open fun onLoadMore() {}

    protected open fun configurePullRefreshState(state: SmartSwipeRefreshState) {
        state.enableRefresh = true
        state.enableLoadMore = false
    }

    @Composable
    protected open fun rememberPullRefreshState(): SmartSwipeRefreshState {
        val state = rememberSmartSwipeRefreshState()
        configurePullRefreshState(state)
        return state
    }

    @Composable
    protected open fun PullRefreshHeader(state: SmartSwipeRefreshState) {
        SmartSwipeRefreshDefaultHeader(state = state)
    }

    override fun initView() {
        initData()
    }

    @Composable
    final override fun InitComposeView() {
        val listState = rememberLazyListState()

        SyncComposeToolbarScrollProgress(
            listState = listState,
            controller = provideComposeToolbarController(),
            enabled = provideToolbarScrollSync(),
            threshold = provideToolbarScrollThreshold()
        )

        BindLoadMore(
            listState = listState,
            itemCount = items.size,
            isLoadingMore = isLoadingMore,
            isRefreshing = isRefreshing,
            hasMore = hasMore,
            spec = provideLoadMoreSpec()
        ) {
            isLoadingMore = true
            loadMoreErrorText = null
            onLoadMore()
        }

        val content: @Composable () -> Unit = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(provideListBackgroundColor())
            ) {
                if (items.isEmpty()) {
                    EmptyContent()
                } else {
                    val padding = provideListPaddingSpec()
                    LazyColumn(
                        state = listState,
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(
                            start = padding.start.xdp,
                            top = padding.top.xdp,
                            end = padding.end.xdp,
                            bottom = padding.bottom.xdp
                        ),
                        verticalArrangement = Arrangement.spacedBy(provideItemSpacing().xdp)
                    ) {
                        buildList(listState)
                    }
                }
            }
        }

        if (!enablePullRefresh()) {
            content()
            return
        }

        val refreshState = rememberPullRefreshState()
        SideEffect {
            smartRefreshStateRef = refreshState
        }

        SmartSwipeRefresh(
            modifier = Modifier.fillMaxSize(),
            state = refreshState,
            onRefresh = {
                startRefresh()
            },
            onLoadMore = null,
            headerIndicator = {
                PullRefreshHeader(refreshState)
            },
            footerIndicator = null,
            contentScrollState = listState
        ) {
            content()
        }
    }

    protected open fun LazyListScope.buildList(listState: LazyListState) {
        itemsIndexed(
            items = items,
            key = { index, item -> itemKey(index, item) },
            contentType = { index, item -> itemContentType(index, item) }
        ) { index, item ->
            ItemContent(index, item)
        }

        if (shouldShowLoadMoreFooter()) {
            item(key = "compose_list_load_more_footer") {
                LoadMoreFooter()
            }
        }
    }

    protected open fun shouldShowLoadMoreFooter(): Boolean {
        return isLoadingMore || loadMoreErrorText != null || (!hasMore && shouldShowLoadMoreEndFooter())
    }

    @Composable
    protected open fun EmptyContent() {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = emptyText().toString(),
                fontSize = currentComposeDimens().text(14),
                color = Color(0xFF8A94A6)
            )
        }
    }

    @Composable
    protected open fun LoadMoreFooter() {
        val text = when {
            isLoadingMore -> loadMoreLoadingText()
            loadMoreErrorText != null -> loadMoreErrorText
            !hasMore -> loadMoreEndText()
            else -> null
        } ?: return

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.xdp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text.toString(),
                fontSize = currentComposeDimens().text(13),
                color = Color(0xFF8A94A6)
            )
        }
    }

    @Composable
    protected abstract fun ItemContent(index: Int, item: T)
}

@Composable
private fun SyncComposeToolbarScrollProgress(
    listState: LazyListState,
    controller: ComposeToolbarController?,
    enabled: Boolean,
    threshold: Int
) {
    if (!enabled || controller == null) {
        return
    }

    val thresholdPx = remember(threshold) { threshold.toFloat().coerceAtLeast(1f) }
    LaunchedEffect(listState, controller, thresholdPx) {
        snapshotFlow {
            if (listState.firstVisibleItemIndex > 0) {
                1f
            } else {
                (listState.firstVisibleItemScrollOffset / thresholdPx).coerceIn(0f, 1f)
            }
        }.collect(controller::setScrollProgress)
    }
}

@Composable
private fun BindLoadMore(
    listState: LazyListState,
    itemCount: Int,
    isLoadingMore: Boolean,
    isRefreshing: Boolean,
    hasMore: Boolean,
    spec: ComposeListLoadMoreSpec,
    onLoadMore: () -> Unit
) {
    if (!spec.enabled) {
        return
    }

    LaunchedEffect(listState, itemCount, isLoadingMore, isRefreshing, hasMore, spec) {
        snapshotFlow {
            val layoutInfo = listState.layoutInfo
            val lastVisibleIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1
            val totalItemsCount = layoutInfo.totalItemsCount
            lastVisibleIndex to totalItemsCount
        }.collect { (lastVisibleIndex, totalItemsCount) ->
            if (totalItemsCount <= 0 || isLoadingMore || isRefreshing || !hasMore) {
                return@collect
            }
            if (lastVisibleIndex >= totalItemsCount - 1 - spec.triggerThreshold) {
                onLoadMore()
            }
        }
    }
}
