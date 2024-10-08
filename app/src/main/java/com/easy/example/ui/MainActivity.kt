package com.easy.example.ui

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.easy.core.ui.base.open
import com.easy.core.ui.list.BaseListViewModel
import com.easy.core.ui.list.BaseVmListActivity
import com.easy.core.utils.ToastUtils
import com.easy.core.utils.log.LogUtils
import com.easy.example.adapter.MainAdapter
import com.easy.example.bean.MainBean
import com.easy.example.demo.DemoIndexActivity2
import com.easy.example.ui.MainActivity.MainViewModel
import com.easy.example.ui.adaptation.AdaptationIndexActivity2
import com.easy.example.ui.adaptation.permission.PermissionActivity
import com.easy.example.ui.bar.ToolBarActivity
import com.easy.example.ui.crash.ThrowIndexActivity2
import com.easy.example.ui.customize.CustomRadioActivity
import com.easy.example.ui.customize.CustomizeIndexActivity2
import com.easy.example.ui.dialog.TestDialogActivity
import com.easy.example.ui.file.FileIndexActivity2
import com.easy.example.ui.fragment.FragmentIndexActivity
import com.easy.example.ui.jetpack.databinding.BindingIndexActivity2
import com.easy.example.ui.parcelable.ParcelableActivity
import com.easy.example.ui.recycle.LoadMoreActivity
import com.easy.example.ui.system.info.BaseInfoActivity
import com.easy.example.ui.system.info.NetInfoActivity
import com.easy.example.ui.transitions.animation.TransitionsAnimationActivity2
import kotlinx.coroutines.launch

/**
 * @Author : huangqiqiang
 * @Package : com.easy.iblibrary
 * @FileName :   MainActivity
 * @Date : 2018/11/22 0022
 * @Describe : TODO
 * @Email :  qiqiang213@gmail.com
 */

class MainActivity : BaseVmListActivity<MainViewModel, ViewDataBinding>() {
    //  这边是kotlin
    override val adapter: MainAdapter = MainAdapter()
//    override fun bindingViewModelId(): Int {
//        return 0
//    }

    override var isLoadMore: Boolean =false

    override fun onCreate(savedInstanceState: Bundle?) {
        LogUtils.dInfo("MainActivity    onCreate1")
        super.onCreate(savedInstanceState)
        LogUtils.dInfo("MainActivity    onCreate2")
    }

    override fun initData() {
        LogUtils.dInfo("MainActivity    initData")
//        SysPermissionsUtils.requestBluetooth(supportFragmentManager) { status ->
//            if (status) {
//            }
//        }

        open(CustomRadioActivity::class.java)

//        open(BackgroundActivity::class.java)
//        open(LoadMoreActivity::class.java)
//        open(TestDialogActivity::class.java)
//        open(BannerActivity::class.java)
        LogUtils.de("111111111111111111111111")

//        FullDialog.showDialog(supportFragmentManager)
        //data/data/com.easy.core/cache/256965670.pdf

//        SysPermissionsUtils.requestStorage(object : PermissionsResult {
//            override fun onPermissionsResult(status: Boolean) {
//
//                if (status) {
//
//                    var oldFilePath = "data/data/com.easy.core/cache/256965670.pdf"
//
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                        FileUtils.copyFileToDownloadDir(activity, oldFilePath, "core", "256965670.pdf")
//                    }
//
////                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
////                    }
//                    var downLoadPath =
//                        FilePathTools.getExternalDownloadsPath() + File.separator + "core" + File.separator + "256965670.pdf"
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
////            var fileUri = MediaStore.Downloads.EXTERNAL_CONTENT_URI.buildUpon()
////                .appendPath(downLoadPath)
////                .build()
//                        ///sdcard/Download/ncpss/甘肃清水方言歇后语中的比喻探析.pdf
////                        var f = FileUtils.findUri4FileName(activity, "ncpss", "甘肃清水方言歇后语中的比喻探析.pdf")
//
//                        var ff = FileUtils.findDownloadsUri4Description(activity, "core", "256965670.pdf")
//
////                        var fileUri = FileUtils.findDownloadFile(activity, "core", "256965670.pdf")
////
////
////                        fileUri?.let { FileUtils.copyToPrivateDir(activity, it[0], FilePathTools.getCacheDir() + File.separator + "111.pdf") }
//
//
//                        if (ff.size > 0) {
//                            LogUtils.e("${ff[0]}")
//                        }
//                    FileUtils.listFiles(activity,"core")
//
//                    } else {
//                    }
//
//
//                }
//
//            }
//
//        })

//        open(AlbumIndexActivity::class.java)
//        open(FilePathActivity::class.java)
//        open(DefToolBarActivity::class.java)
//        loadingView.show()

//        open(DownLoadActivity::class.java)
//        open(RecProgressActivity::class.java)
//        open(ComposeIndexActivity::class.java)

//        startService(Intent(this, KillAppServers::class.java))

//        open(DownLoadActivity::class.java)
//        CropImageDialog().show(supportFragmentManager)

//        PhotoDialog.getSelectPhotoDialog(1) {
////            binding.cropImageView.imageBitmap = BitmapFactory.decodeFile(it[0].path)
//        }.apply {
//            isSendAlbum=false
//        }.show(supportFragmentManager)

//        open(FlowActivity::class.java)
//        open(WeatherActivity::class.java)

//        open(ComparedActivity::class.java)

//        VideoDialog().show(supportFragmentManager)

//        open(LoadMoreActivity::class.java)

//        open(AlbumIndexActivity::class.java)
//        open(ShareIndexActivity::class.java)
//        open(LogUtilsActivity::class.java)
//        open(TextViewActivity::class.java)
//open(BindingRecycleActivity::class.java)
//        PermissionsUtils.requestPermissions(IPermissionsHas.camera,IPermissionsHas.microphone){
//            if (it){
//                open(Camera2Activity::class.java)
//            }
//        }
//        SysPermissionsUtils.requestStorage(supportFragmentManager,{
//            if (it) {
//                open(ProgressBarViewBuilderActivity::class.java)
//                open(VideoViewActivity::class.java)
//                open(SeparationVideoActivity::class.java)
//            }
//        })

//        open(DefImgActivity::class.java)
//        open(BannerActivity::class.java)
//        open(TabLayoutActivity::class.java)


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
            arrayList.add(MainBean("文件相关", FileIndexActivity2::class.java))
            arrayList.add(MainBean("Ui相关", ViewIndexActivity2::class.java))
            arrayList.add(MainBean("Fragment相关", FragmentIndexActivity::class.java))
            arrayList.add(MainBean("异常相关测试", ThrowIndexActivity2::class.java))
            arrayList.add(MainBean("启动模式", com.easy.example.ui.launch.mode.SingleInstanceActivity::class.java))
            arrayList.add(MainBean("转场动画", TransitionsAnimationActivity2::class.java))
            arrayList.add(MainBean("标题/状态栏设置", ToolBarActivity::class.java))
            arrayList.add(MainBean("相册", AlbumIndexActivity::class.java))
            arrayList.add(MainBean("权限测试", PermissionActivity::class.java))
            arrayList.add(MainBean("Dialog系列", TestDialogActivity::class.java))
            arrayList.add(MainBean("Shape测试", ShapeTestActivity::class.java))
            arrayList.add(MainBean("自定义系列", CustomizeIndexActivity2::class.java))
            arrayList.add(MainBean("设备信息", BaseInfoActivity::class.java))
            arrayList.add(MainBean("网络信息", NetInfoActivity::class.java))
            arrayList.add(MainBean("适配相关", AdaptationIndexActivity2::class.java))
            arrayList.add(MainBean("Binding 相关", BindingIndexActivity2::class.java))
            arrayList.add(MainBean("Parcelable测试", ParcelableActivity::class.java))
            arrayList.add(MainBean("demo测试", DemoIndexActivity2::class.java))
            arrayList.add(MainBean("震动测试", VibrateActivity::class.java))
            arrayList.add(MainBean("Log", LogUtilsActivity::class.java))
            setData(arrayList)
        }
    }
}
