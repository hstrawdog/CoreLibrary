package com.easy.example.ui.adaptation

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.easy.core.R
import com.easy.core.ui.compose.BaseComposeActivity
import com.easy.core.ui.compose.ComposeToolbarActionSpec
import com.easy.core.ui.compose.ComposeToolbarController
import com.easy.core.ui.compose.ProvideComposeDimens
import com.easy.core.ui.compose.currentComposeDimens
import com.easy.core.ui.compose.xdp

class ComposeToolbarControlActivity : BaseComposeActivity() {

    @Composable
    override fun InitComposeView() {
        ComposeToolbarControlScreen(toolbarController = composeToolbarController)
    }
}

@Composable
private fun ComposeToolbarControlScreen(toolbarController: ComposeToolbarController) {
    val dimens = currentComposeDimens()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7FB))
            .verticalScroll(rememberScrollState())
            .padding(12.xdp),
        verticalArrangement = Arrangement.spacedBy(8.xdp)
    ) {
        TitleBlock(
            title = "Toolbar Runtime Control",
            desc = "这页用来模拟旧 ToolBarActivity 的常见操作：改标题、改背景、单独改 bar 背景、改右侧 action、隐藏分割线。"
        )

        ActionRow(
            "标题",
            listOf(
                "默认标题" to {
                    toolbarController.setTitle("Compose Toolbar Control")
                },
                "新标题" to {
                    toolbarController.setTitle("new标题")
                }
            )
        )

        ActionRow(
            "背景色",
            listOf(
                "默认" to {
                    toolbarController.setBackgroundColor(com.easy.core.R.color.toolbar_bg_color)
                    toolbarController.setContentColor(com.easy.core.R.color.toolbar_text_color)
                },
                "主色" to {
                    toolbarController.setBackgroundColor(R.color.color_main)
                    toolbarController.setContentColor(R.color.white)
                },
                "白色" to {
                    toolbarController.setBackgroundColor(R.color.white)
                    toolbarController.setContentColor(R.color.color_333)
                },
                "透明" to {
                    toolbarController.setBackgroundColor(R.color.transparent)
                    toolbarController.setContentColor(R.color.color_333)
                }
            )
        )

        ActionRow(
            "仅Bar背景",
            listOf(
                "恢复跟随" to {
                    toolbarController.setToolbarBackgroundColor(null)
                },
                "Bar主色" to {
                    toolbarController.setToolbarBackgroundColor(R.color.color_main)
                    toolbarController.setContentColor(R.color.white)
                },
                "Bar黑色" to {
                    toolbarController.setToolbarBackgroundColor(R.color.black)
                    toolbarController.setContentColor(R.color.white)
                },
                "Bar白色" to {
                    toolbarController.setToolbarBackgroundColor(R.color.white)
                    toolbarController.setContentColor(R.color.color_333)
                }
            )
        )

        ActionRow(
            "仅状态栏背景",
            listOf(
                "恢复跟随" to {
                    toolbarController.setStatusBarBackgroundColor(null)
                },
                "状态栏主色" to {
                    toolbarController.setStatusBarBackgroundColor(R.color.color_main)
                    toolbarController.setContentColor(R.color.white)
                },
                "状态栏黑色" to {
                    toolbarController.setStatusBarBackgroundColor(R.color.black)
                    toolbarController.setContentColor(R.color.white)
                },
                "状态栏白色" to {
                    toolbarController.setStatusBarBackgroundColor(R.color.white)
                    toolbarController.setContentColor(R.color.color_333)
                }
            )
        )

        ActionRow(
            "右侧操作",
            listOf(
                "分享+更多" to {
                    toolbarController.setActionItems(
                        listOf(
                            ComposeToolbarActionSpec.Text("分享") {},
                            ComposeToolbarActionSpec.Icon(com.easy.core.R.drawable.ic_more) {}
                        )
                    )
                },
                "仅文字" to {
                    toolbarController.setActionItems(
                        listOf(
                            ComposeToolbarActionSpec.Text("完成", color = R.color.color_main) {}
                        )
                    )
                },
                "清空" to {
                    toolbarController.setActionItems(emptyList())
                }
            )
        )

        ActionRow(
            "分割线/返回图标",
            listOf(
                "显示线" to {
                    toolbarController.setShowBottomLine(true)
                    toolbarController.setNavigationIcon(com.easy.core.R.mipmap.ic_back_gray)
                },
                "隐藏线" to {
                    toolbarController.setShowBottomLine(false)
                },
                "白色返回" to {
                    toolbarController.setNavigationIcon(com.easy.core.R.mipmap.ic_black_whit)
                }
            )
        )

        Text(
            text = "这套控制方式不是直接操作 View，而是通过 ComposeToolbarController 改状态，toolbar 自己重组。",
            color = Color(0xFF5C6878),
            fontSize = dimens.text(14),
            modifier = Modifier.padding(top = 4.xdp)
        )
    }
}

@Composable
private fun TitleBlock(title: String, desc: String) {
    val dimens = currentComposeDimens()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(12.xdp))
            .padding(12.xdp),
        verticalArrangement = Arrangement.spacedBy(5.xdp)
    ) {
        Text(
            text = title,
            color = Color(0xFF18212F),
            fontSize = dimens.text(17),
            fontWeight = FontWeight.Bold
        )
        Text(
            text = desc,
            color = Color(0xFF5C6878),
            fontSize = dimens.text(14)
        )
    }
}

@Composable
private fun ActionRow(
    title: String,
    actions: List<Pair<String, () -> Unit>>
) {
    val dimens = currentComposeDimens()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(12.xdp))
            .padding(12.xdp),
        verticalArrangement = Arrangement.spacedBy(6.xdp)
    ) {
        Text(
            text = title,
            color = Color(0xFF18212F),
            fontSize = dimens.text(15),
            fontWeight = FontWeight.SemiBold
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.xdp)
        ) {
            actions.forEach { (label, action) ->
                Button(
                    onClick = action,
                    shape = RoundedCornerShape(9.xdp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1E88E5))
                ) {
                    Text(
                        text = label,
                        color = Color.White,
                        fontSize = dimens.text(12)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 393, heightDp = 1200)
@Composable
private fun ComposeToolbarControlScreenPreview() {
    val toolbarController = remember {
        ComposeToolbarController(
            title = "Compose Toolbar Control",
            actionItems = listOf(
                ComposeToolbarActionSpec.Text("分享") {},
                ComposeToolbarActionSpec.Icon(com.easy.core.R.drawable.ic_more) {}
            )
        )
    }
    ProvideComposeDimens(designWidth = 375) {
        MaterialTheme {
            ComposeToolbarControlScreen(toolbarController = toolbarController)
        }
    }
}
