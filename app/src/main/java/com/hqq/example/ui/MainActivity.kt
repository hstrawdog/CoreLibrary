package com.hqq.example.ui

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.hqq.core.ui.base.open
import com.hqq.core.ui.list.BaseListViewModel
import com.hqq.core.ui.list.BaseVmListActivity
import com.hqq.core.utils.ToastUtils
import com.hqq.core.utils.log.LogUtils
import com.hqq.example.adapter.MainAdapter
import com.hqq.example.bean.MainBean
import com.hqq.example.demo.DemoIndexActivity
import com.hqq.example.ui.MainActivity.MainViewModel
import com.hqq.example.ui.adaptation.AdaptationIndexActivity
import com.hqq.example.ui.adaptation.DefImgActivity
import com.hqq.example.ui.adaptation.permission.PermissionActivity
import com.hqq.example.ui.bar.ToolBarActivity
import com.hqq.example.ui.customize.CustomizeIndexActivity
import com.hqq.example.ui.crash.ThrowIndexActivity
import com.hqq.example.ui.dialog.TestDialogActivity
import com.hqq.example.ui.file.FileIndexActivity
import com.hqq.example.ui.layout.fold.FoldViewActivity
import com.hqq.example.ui.fragment.FragmentIndexActivity
import com.hqq.example.ui.system.info.BaseInfoActivity
import com.hqq.example.ui.jetpack.databinding.BindingIndexActivity
import com.hqq.example.ui.launch.mode.SingleInstanceActivity
import com.hqq.example.ui.parcelable.ParcelableActivity
import com.hqq.example.ui.recycle.RecycleIndexActivity
import com.hqq.example.ui.soft.hide.keyboard.DefSofKeyBoardAndShowActivity
import com.hqq.example.ui.soft.hide.keyboard.SoftHideKeyBoardScrollActivity
import com.hqq.example.ui.transitions.animation.TransitionsAnimationActivity
import com.hqq.example.ui.web.WebActivity
import kotlinx.coroutines.*

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.iblibrary
 * @FileName :   MainActivity
 * @Date : 2018/11/22 0022
 * @Descrive : TODO
 * @Email :  qiqiang213@gmail.com
 */

class MainActivity : BaseVmListActivity<MainViewModel, ViewDataBinding>() {
    //  这边是kotlin
    override val adapter: MainAdapter = MainAdapter()
    override val bindingViewModelId: Int
        get() = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        LogUtils.dInfo("MainActivity    onCreate1")
        super.onCreate(savedInstanceState)
        LogUtils.dInfo("MainActivity    onCreate2")
    }

    override fun initData() {
        LogUtils.dInfo("MainActivity    initData")

        open(DefSofKeyBoardAndShowActivity::class.java)
    }


    private var mExitTime: Long = 0
    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (event.keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN && event.repeatCount == 0) {
            if (System.currentTimeMillis() - mExitTime > 2000) {
                ToastUtils.showToast("再按一次退出程序")
                mExitTime = System.currentTimeMillis()
            } else {
                val mHomeIntent = Intent(Intent.ACTION_MAIN)
                mHomeIntent.addCategory(Intent.CATEGORY_HOME)
                mHomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
                startActivity(mHomeIntent)
                // 退出程序
                System.exit(0)
            }
            return true
        }
        return super.dispatchKeyEvent(event)
    }

    class MainViewModel : BaseListViewModel() {
        init {
            LogUtils.dInfo("MainModel init 1  ")
            viewModelScope.launch {
                LogUtils.dInfo("MainModel init viewModelScope")
            }
            LogUtils.dInfo("MainModel init 2")
        }

        override fun initData(extras: Bundle?) {
            super.initData(extras)
            LogUtils.dInfo("MainModel initData 1")
        }

        override fun onCreate(owner: LifecycleOwner) {
            LogUtils.dInfo("MainModel onCrete 1")
            super.onCreate(owner)
            LogUtils.dInfo("MainModel onCrete 2")
            val arrayList = mutableListOf<MainBean<*>>()
            arrayList.add(MainBean("文件相关", FileIndexActivity::class.java))
            arrayList.add(MainBean("Ui相关", ViewIndexActivity::class.java))
            arrayList.add(MainBean("Fragment相关", FragmentIndexActivity::class.java))
            arrayList.add(MainBean("异常相关测试", ThrowIndexActivity::class.java))
            arrayList.add(MainBean("启动模式", SingleInstanceActivity::class.java))
            arrayList.add(MainBean("转场动画", TransitionsAnimationActivity::class.java))
            arrayList.add(MainBean("标题/状态栏设置", ToolBarActivity::class.java))
            arrayList.add(MainBean("权限测试", PermissionActivity::class.java))
            arrayList.add(MainBean("Dialog系列", TestDialogActivity::class.java))
            arrayList.add(MainBean("Shape测试", ShapeTestActivity::class.java))
            arrayList.add(MainBean("自定义系列", CustomizeIndexActivity::class.java))
            arrayList.add(MainBean("设备信息", BaseInfoActivity::class.java))
            arrayList.add(MainBean("适配相关", AdaptationIndexActivity::class.java))
            arrayList.add(MainBean("Binding 相关", BindingIndexActivity::class.java))
            arrayList.add(MainBean("Parcelable测试", ParcelableActivity::class.java))
            arrayList.add(MainBean("demo测试", DemoIndexActivity::class.java))
            arrayList.add(MainBean("震动测试", VibrateActivity::class.java))
            setData(arrayList)
        }
    }
}
