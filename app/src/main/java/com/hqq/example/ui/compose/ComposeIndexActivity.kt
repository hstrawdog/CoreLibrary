package com.hqq.example.ui.compose

import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hqq.core.ui.base.BaseActivity
import com.hqq.core.utils.ScreenUtils


class ComposeIndexActivity : BaseActivity() {
    override val layoutViewId:Int = 0


    override fun getLayoutView(parent:ViewGroup):View {
        return ComposeView(activity).apply {
            setContent {
                HelloView()
            }
        }
    }

    override fun initView() {

    }

    // @Composable 注解代表一个 Compose View
    @Composable
    fun HelloView() {
        Row(modifier = Modifier.size(420.dp, 720.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
            Text("Hello Compose!", color = Color.Red)
        }
    }

    @Composable
    fun getComposeView() {
        return HelloView()
    }

    //预览
    @Preview
    @Composable
    fun ComposePreView() {

        HelloView()
    }


}