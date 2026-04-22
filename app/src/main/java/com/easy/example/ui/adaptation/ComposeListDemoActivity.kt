package com.easy.example.ui.adaptation

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.lifecycleScope
import com.easy.core.kt.open
import com.easy.core.ui.compose.BaseComposeListActivity
import com.easy.core.ui.compose.ComposeListLoadMoreSpec
import com.easy.core.ui.compose.ComposeToolbarActionSpec
import com.easy.core.ui.compose.ComposeToolbarSpec
import com.easy.core.ui.compose.currentComposeDimens
import com.easy.core.ui.compose.xdp
import com.easy.core.ui.compose.refresh.SmartSwipeRefreshState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ComposeListDemoActivity : BaseComposeListActivity<ComposeListDemoActivity.ComposeEntry>() {

    private var page = 1

    override fun providePageTitle(): CharSequence = "Compose List 验证"

    override fun provideListBackgroundColor(): Color = Color(0xFFF5F7FB)

    override fun provideToolbarScrollSync(): Boolean = true

    override fun provideToolbarScrollThreshold(): Int = 88

    override fun provideLoadMoreSpec(): ComposeListLoadMoreSpec {
        return ComposeListLoadMoreSpec(
            enabled = true,
            triggerThreshold = 1
        )
    }

    override fun enablePullRefresh(): Boolean = true

    override fun configurePullRefreshState(state: SmartSwipeRefreshState) {
        super.configurePullRefreshState(state)
        state.stickinessLevel = 0.55f
    }

    override fun shouldShowLoadMoreEndFooter(): Boolean = true

    override fun provideComposeToolbarSpec(): ComposeToolbarSpec {
        return ComposeToolbarSpec(
            title = providePageTitle(),
            controller = composeToolbarController,
            swapNavigationIconOnScroll = true,
            actionItems = listOf(
                ComposeToolbarActionSpec.Text("重置") {
                    resetDemo()
                }
            )
        )
    }

    override fun initView() {
        super.initView()
        resetDemo()
    }

    override fun onLoadMore() {
        val nextPage = page + 1
        lifecycleScope.launch {
            delay(320)
            val pageItems = buildPagedTips(nextPage)
            page = nextPage
            appendPage(
                pageItems = pageItems,
                hasMore = nextPage < 3
            )
        }
    }

    override fun onRefresh() {
        lifecycleScope.launch {
            delay(650)
            page = 1
            finishRefresh(
                newItems = buildFirstPage(),
                hasMore = true
            )
        }
    }

    @Composable
    override fun ItemContent(index: Int, item: ComposeEntry) {
        val dimens = currentComposeDimens()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(18.xdp))
                .background(Color.White)
                .clickable(enabled = item.target != null) {
                    item.target?.let(::open)
                }
                .padding(18.xdp),
            verticalArrangement = Arrangement.spacedBy(8.xdp)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(999.xdp))
                    .background(if (item.target != null) Color(0xFFE8F1FF) else Color(0xFFF1F3F7))
                    .padding(horizontal = 10.xdp, vertical = 5.xdp)
            ) {
                Text(
                    text = if (item.target != null) "Demo ${index + 1}" else "LoadMore",
                    fontSize = dimens.text(12),
                    fontWeight = FontWeight.Medium,
                    color = if (item.target != null) Color(0xFF1E6EEB) else Color(0xFF6B7380)
                )
            }

            Text(
                text = item.title,
                fontSize = dimens.text(18),
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF18212F)
            )

            Text(
                text = item.summary,
                fontSize = dimens.text(14),
                color = Color(0xFF5C6678)
            )
        }
    }

    private fun resetDemo() {
        page = 1
        submitItems(buildFirstPage())
        finishRefresh()
        resetLoadMore(hasMore = true)
        composeToolbarController.setTitle("Compose List 验证")
    }

    private fun buildFirstPage(): List<ComposeEntry> {
        return listOf(
            ComposeEntry(
                title = "Compose 屏宽适配",
                summary = "验证 375dp 基线、xdp 和适配字号是否按设计稿心智工作。",
                target = ComposeDimensActivity::class.java
            ),
            ComposeEntry(
                title = "Compose Toolbar 滚动",
                summary = "列表滚动时同步 toolbar scrollProgress，观察标题栏状态切换。",
                target = ComposeToolbarScrollActivity::class.java
            ),
            ComposeEntry(
                title = "Compose Toolbar 控制",
                summary = "验证 ComposeToolbarController 的动态标题、背景和操作项更新。",
                target = ComposeToolbarControlActivity::class.java
            ),
            ComposeEntry(
                title = "Compose Toolbar 宿主验证",
                summary = "统一验证 Activity / Fragment / Dialog 三类宿主，并覆盖 LinearLayout 与 FrameLayout 两种根布局。",
                target = ComposeToolbarHostCatalogActivity::class.java
            )
        )
    }

    private fun buildPagedTips(page: Int): List<ComposeEntry> {
        return List(4) { index ->
            val order = (page - 2) * 4 + index + 1
            ComposeEntry(
                title = "Compose List Tip $order",
                summary = "这一页是 Compose LazyColumn 的分页占位示例。后续可以继续往这里补刷新、空态、错误态、sticky header 等通用能力。"
            )
        }
    }

    data class ComposeEntry(
        val title: String,
        val summary: String,
        val target: Class<out AppCompatActivity>? = null
    )
}
