package com.hqq.example.ui

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.viewModels
import androidx.databinding.ViewDataBinding
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hqq.core.ui.vm.BaseListViewModel
import com.hqq.core.ui.vm.BaseVmListActivity
import com.hqq.core.utils.ToastUtils
import com.hqq.core.utils.log.LogUtils
import com.hqq.example.adapter.MainAdapter
import com.hqq.example.bean.MainBean
import com.hqq.example.demo.DemoIndexActivity
import com.hqq.example.ui.MainActivity.MainViewModel
import com.hqq.example.ui.adaptation.AdaptationIndexActivity
import com.hqq.example.ui.adaptation.DefImgActivity
import com.hqq.example.ui.adaptation.PermissionActivity
import com.hqq.example.ui.bar.ToolBarActivity
import com.hqq.example.ui.customize.CustomizeIndexActivity
import com.hqq.example.ui.dialog.TestDialogActivity
import com.hqq.example.ui.exception.ThrowActivity
import com.hqq.example.ui.info.BaseInfoActivity
import com.hqq.example.ui.info.FilePathActivity
import com.hqq.example.ui.jetpack.livedata.LiveDateActivity
import com.hqq.example.ui.launch.mode.SingleInstanceActivity
import com.hqq.example.ui.recycle.RecycleIndexActivity
import com.hqq.example.ui.skin.SkinAActivity
import com.hqq.example.ui.transitions.animation.TransitionsAnimationActivity
import com.hqq.example.ui.view.BlackAndWhiteActivity
import com.hqq.example.ui.view.SvgActivity
import com.hqq.example.ui.web.WebActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.iblibrary
 * @FileName :   MainActivity
 * @Date : 2018/11/22 0022
 * @Descrive : TODO
 * @Email :  qiqiang213@gmail.com
 */
@AndroidEntryPoint
class MainActivity : BaseVmListActivity<ViewDataBinding, MainViewModel, MainAdapter>() {

    override val baseAdapter: MainAdapter = MainAdapter()
    override val bindingViewModelId: Int
        get() = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        LogUtils.e("MainActivity    onCreate1")
        super.onCreate(savedInstanceState)
        LogUtils.e("MainActivity    onCreate2")

    }

    override fun initData() {
        LogUtils.e("MainActivity    initData")
//        LiveDateActivity.open(this)

    }



    val mV: MainViewModel by viewModels()

    private var mExitTime: Long = 0
    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (event.keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN && event.repeatCount == 0) {
            if (System.currentTimeMillis() - mExitTime > 2000) {
                ToastUtils.showToast("再按一次退出程序")
                mExitTime = System.currentTimeMillis()
            } else {
                val mHomeIntent = Intent(Intent.ACTION_MAIN)
                mHomeIntent.addCategory(Intent.CATEGORY_HOME)
                mHomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
                startActivity(mHomeIntent)
                // 退出程序
                System.exit(0)
            }
            return true
        }
        return super.dispatchKeyEvent(event)
    }

    val mVl: MainViewModel by viewModels()

    override fun getViewModel(): ViewModel {
        return mVl
    }

    class MainViewModel @ViewModelInject constructor() : BaseListViewModel() {
        init {
            LogUtils.e("MainModel init 1  ")
            viewModelScope.launch {
                LogUtils.e("MainModel init viewModelScope")
            }
            LogUtils.e("MainModel init 2")
        }

        override fun onCrete() {
            LogUtils.e("MainModel onCrete 1")
            super.onCrete()
            LogUtils.e("MainModel onCrete 2")
            val arrayList = mutableListOf<MainBean<*>>()
            arrayList.add(MainBean("启动模式", SingleInstanceActivity::class.java))
            arrayList.add(MainBean("转场动画", TransitionsAnimationActivity::class.java))
            arrayList.add(MainBean("标题/状态栏设置", ToolBarActivity::class.java))
            arrayList.add(MainBean("默认图显示", DefImgActivity::class.java))
            arrayList.add(MainBean("Recycle 相关", RecycleIndexActivity::class.java))
            arrayList.add(MainBean("权限测试", PermissionActivity::class.java))
            arrayList.add(MainBean("Dialog系列", TestDialogActivity::class.java))
            arrayList.add(MainBean("Shape测试", ShapeTestActivity::class.java))
            arrayList.add(MainBean("自定义系列", CustomizeIndexActivity::class.java))
            arrayList.add(MainBean("设备信息", BaseInfoActivity::class.java))
            arrayList.add(MainBean("网页测试", WebActivity::class.java))
            arrayList.add(MainBean("文件路径信息", FilePathActivity::class.java))
            arrayList.add(MainBean("适配相关", AdaptationIndexActivity::class.java))
            arrayList.add(MainBean("DateBinding测试", LiveDateActivity::class.java))
            arrayList.add(MainBean("Throw异常测试", ThrowActivity::class.java))
            arrayList.add(MainBean("黑白化测试", BlackAndWhiteActivity::class.java))
            arrayList.add(MainBean("换肤测试", SkinAActivity::class.java))
            arrayList.add(MainBean("SVG测试", SvgActivity::class.java))
            arrayList.add(MainBean("demo测试", DemoIndexActivity::class.java))
            setData(arrayList)

        }
    }


}
