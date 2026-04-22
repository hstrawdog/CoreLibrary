package com.easy.example.ui

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
import com.easy.core.kt.open
import com.easy.core.ui.compose.BaseComposeListActivity
import com.easy.core.ui.compose.currentComposeDimens
import com.easy.core.ui.compose.xdp
import com.easy.example.bean.MainBean
import com.easy.example.ui.adaptation.DefImgActivity
import com.easy.example.ui.layout.fold.FoldViewActivity
import com.easy.example.ui.recycle.RecycleIndexActivity
import com.easy.example.ui.tab.layout.TabLayoutActivity
import com.easy.example.ui.web.WebActivity

class ViewIndexActivity2 : BaseComposeListActivity<MainBean<*>>() {

    override fun providePageTitle(): CharSequence = "Ui相关"

    override fun provideListBackgroundColor(): Color = Color(0xFFF5F7FB)

    override fun initData() {
        submitItems(
            listOf(
                MainBean("网页测试", WebActivity::class.java),
                MainBean("RecycleView 相关", RecycleIndexActivity::class.java),
                MainBean("折叠布局", FoldViewActivity::class.java),
                MainBean("默认图显示", DefImgActivity::class.java),
                MainBean("TabLayoutActivity 相关", TabLayoutActivity::class.java)
            )
        )
    }

    override fun itemKey(index: Int, item: MainBean<*>): Any = item.title ?: index

    @Composable
    override fun ItemContent(index: Int, item: MainBean<*>) {
        val dimens = currentComposeDimens()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(18.xdp))
                .background(Color.White)
                .clickable {
                    open(item.className)
                }
                .padding(18.xdp),
            verticalArrangement = Arrangement.spacedBy(8.xdp)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(999.xdp))
                    .background(Color(0xFFE8F1FF))
                    .padding(horizontal = 10.xdp, vertical = 5.xdp)
            ) {
                Text(
                    text = "Item ${index + 1}",
                    fontSize = dimens.text(12),
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF1E6EEB)
                )
            }

            Text(
                text = item.title,
                fontSize = dimens.text(18),
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF18212F)
            )

            Text(
                text = "BaseComposeListActivity 已切到 LazyColumn 主路径，这个入口页现在直接验证 Compose 列表链路。",
                fontSize = dimens.text(14),
                color = Color(0xFF5C6678)
            )
        }
    }
}
