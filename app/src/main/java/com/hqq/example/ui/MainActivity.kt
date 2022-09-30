package com.hqq.example.ui

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.hqq.core.net.ok.OkHttp
import com.hqq.core.net.ok.download.HttpClient
import com.hqq.core.ui.base.open
import com.hqq.core.ui.list.BaseListViewModel
import com.hqq.core.ui.list.BaseVmListActivity
import com.hqq.core.utils.ToastUtils
import com.hqq.core.utils.log.LogUtils
import com.hqq.example.accessibility.TestAccessibilityActivity
import com.hqq.example.adapter.MainAdapter
import com.hqq.example.bean.MainBean
import com.hqq.example.demo.DemoIndexActivity
import com.hqq.example.ui.MainActivity.MainViewModel
import com.hqq.example.ui.adaptation.AdaptationIndexActivity
import com.hqq.example.ui.adaptation.DefImgActivity
import com.hqq.example.ui.adaptation.permission.PermissionActivity
import com.hqq.example.ui.bar.ToolBarActivity
import com.hqq.example.ui.customize.CustomizeIndexActivity
import com.hqq.example.ui.dialog.TestDialogActivity
import com.hqq.example.ui.crash.ThrowIndexActivity
import com.hqq.example.ui.file.FileIndexActivity
import com.hqq.example.ui.fold.FoldViewActivity
import com.hqq.example.ui.fragment.FragmentIndexActivity
import com.hqq.example.ui.fragment.TestLiveFragmentActivity
import com.hqq.example.ui.info.BaseInfoActivity
import com.hqq.example.ui.jetpack.databinding.BindingIndexActivity
import com.hqq.example.ui.jetpack.livedata.LiveDateActivity
import com.hqq.example.ui.launch.mode.SingleInstanceActivity
import com.hqq.example.ui.parcelable.ParcelableActivity
import com.hqq.example.ui.recycle.RecycleIndexActivity
import com.hqq.example.ui.transitions.animation.TransitionsAnimationActivity
import com.hqq.example.ui.view.CanvasDrawBitmapActivity
import com.hqq.example.ui.view.MirrorActivity
import com.hqq.example.ui.web.WebActivity
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.coroutines.coroutineContext

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
//        loadingView.show()
//        Handler().postDelayed(3 * 1000) {
//            MyPopupWindow(this).showPopupWindow(iCreateRootView.rootView);
//        }
//        startActivity(Intent(this, LoginActivity::class.java))
//        RoomActivity.open(this)
//        SoftHideKeyboardScrollView.getNavigationBarHeight(this)
//        SoftHideKeyboardScrollView.checkDeviceHasNavigationBar(this)
//        startActivity(Intent(this, SaveBitmapActivity::class.java))
//        startActivity(Intent(this, DownLoadActivity::class.java))
//        open(FilePathActivity::class.java)
//        open(DataBindingActivity::class.java)
//
//        open(ViewPager2FragmentLiveActivity::class.java)
//        open(WeatherActivity::class.java,Bundle().apply {
//            putParcelable("A", ParcelableBean())
//        })

//        open(SpinnerActivity::class.java)
//        open(GyroActivity::class.java)
//        open(TestAccessibilityActivity::class.java)
//        startActivity(Intent(this, ComparedActivity::class.java))
//    open(BrushViewActivity::class.java)

//        open(TestLiveFragmentActivity::class.java)
//        open(MirrorActivity::class.java)

//    OkHttp.newHttpCompat()

//        open(FragmentIndexActivity::class.java)
        open(LiveDateActivity::class.java)

        val testFlow = flow<String>() {
            emit("1213")
            emit("123")

        }
        val testFlow2 = flow<String>() {
            emit("1213")
            emit("123")

        }
        combine(testFlow,testFlow2){
                response1, response2 ->
        }
        testFlow.zip(testFlow2){response1, response2 ->
            Pair(response1,response2)
        }.onEach {
            it.first
            it.second
        }.launchIn(lifecycleScope).start()
    }


//    val mV: MainViewModel by viewModels()

    private var mExitTime: Long = 0
    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (event.keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN && event.repeatCount == 0) {
            if (System.currentTimeMillis() - mExitTime > 2000) {
                ToastUtils.showToast("再按一次退出程序")
                mExitTime = System.currentTimeMillis()
            } else {
                val mHomeIntent = Intent(Intent.ACTION_MAIN)
                mHomeIntent.addCategory(Intent.CATEGORY_HOME)
                mHomeIntent.addFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK
                            or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
                )
                startActivity(mHomeIntent)
                // 退出程序
                System.exit(0)
            }
            return true
        }
        return super.dispatchKeyEvent(event)
    }

//    val mVl: MainViewModel by viewModels()
//
//    override fun getViewModel(): MainViewModel {
//        return mVl
//    }

    class MainViewModel constructor() : BaseListViewModel() {
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

        override fun onCrete() {
            LogUtils.dInfo("MainModel onCrete 1")
            super.onCrete()
            LogUtils.dInfo("MainModel onCrete 2")
            val arrayList = mutableListOf<MainBean<*>>()
            arrayList.add(MainBean("文件相关", FileIndexActivity::class.java))
            arrayList.add(MainBean("Fragment相关", FragmentIndexActivity::class.java))
            arrayList.add(MainBean("异常相关测试", ThrowIndexActivity::class.java))
            arrayList.add(MainBean("启动模式", SingleInstanceActivity::class.java))
            arrayList.add(MainBean("转场动画", TransitionsAnimationActivity::class.java))
            arrayList.add(MainBean("标题/状态栏设置", ToolBarActivity::class.java))
            arrayList.add(MainBean("默认图显示", DefImgActivity::class.java))
            arrayList.add(MainBean("RecycleView 相关", RecycleIndexActivity::class.java))
            arrayList.add(MainBean("权限测试", PermissionActivity::class.java))
            arrayList.add(MainBean("Dialog系列", TestDialogActivity::class.java))
            arrayList.add(MainBean("Shape测试", ShapeTestActivity::class.java))
            arrayList.add(MainBean("自定义系列", CustomizeIndexActivity::class.java))
            arrayList.add(MainBean("设备信息", BaseInfoActivity::class.java))
            arrayList.add(MainBean("网页测试", WebActivity::class.java))
            arrayList.add(MainBean("适配相关", AdaptationIndexActivity::class.java))
            arrayList.add(MainBean("折叠布局", FoldViewActivity::class.java))
            arrayList.add(MainBean("Binding 相关", BindingIndexActivity::class.java))
            arrayList.add(MainBean("Parcelable测试", ParcelableActivity::class.java))
            arrayList.add(MainBean("DrawBitmap测试", CanvasDrawBitmapActivity::class.java))
            arrayList.add(MainBean("demo测试", DemoIndexActivity::class.java))
            setData(arrayList)

        }
    }
}
