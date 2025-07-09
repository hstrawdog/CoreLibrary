package com.easy.example.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.text.format.Time
import android.util.Base64
import android.util.Log
import android.view.KeyEvent
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.easy.core.kt.open
import com.easy.core.ui.list.BaseListViewModel
import com.easy.core.ui.list.BaseVmListActivity
import com.easy.core.utils.ToastUtils
import com.easy.core.utils.encrypt.AESUtils
import com.easy.core.utils.encrypt.Base64Utils
import com.easy.core.utils.log.LogUtils
import com.easy.example.adapter.MainAdapter
import com.easy.example.bean.MainBean
import com.easy.example.demo.DemoIndexActivity2
import com.easy.example.jsbridge.MainWebView2Activity
import com.easy.example.ui.MainActivity.MainViewModel
import com.easy.example.ui.adaptation.AdaptationIndexActivity2
import com.easy.example.ui.adaptation.permission.PermissionActivity
import com.easy.example.ui.bar.ToolBarActivity
import com.easy.example.ui.crash.ThrowIndexActivity2
import com.easy.example.ui.customize.CustomizeIndexActivity2
import com.easy.example.ui.dialog.TestDialogActivity
import com.easy.example.ui.file.FileIndexActivity2
import com.easy.example.ui.fragment.FragmentIndexActivity
import com.easy.example.ui.fragment.FragmentUtilsActivity
import com.easy.example.ui.jetpack.databinding.BindingIndexActivity2
import com.easy.example.ui.launch.mode.SingleInstanceActivity
import com.easy.example.ui.parcelable.ParcelableActivity
import com.easy.example.ui.system.info.BaseInfoActivity
import com.easy.example.ui.system.info.NetInfoActivity
import com.easy.example.ui.transitions.animation.TransitionsAnimationActivity2
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.text.SimpleDateFormat
import java.util.Calendar
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.SecretKeySpec


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
    override val adapter:MainAdapter = MainAdapter()
//    override fun bindingViewModelId(): Int {
//        return 0
//    }

    override var isLoadMore:Boolean = false

    @SuppressLint("Range")
    override fun initData() {
        LogUtils.dInfo("MainActivity    initData")
//        EncryptUtil.aesDecrypt("666")
//        EncryptUtil.aesDecryptForMessageForDebug("ekhDPpQtWDWwXVldrTJ4xA==")


//            open(MainWebView2Activity::class.java)
            open(FragmentUtilsActivity::class.java)


    }







//    private fun getPdfFilesFromDownloadDirectory3() {
//        val downloadsUri = MediaStore.Downloads.EXTERNAL_CONTENT_URI
//
//        val projection = arrayOf(MediaStore.Files.FileColumns._ID, MediaStore.Files.FileColumns.DATA, MediaStore.Files.FileColumns.MIME_TYPE)
//
//        val selection = null
//        val selectionArgs = null
//        val sortOrder = "${MediaStore.Files.FileColumns.DATE_ADDED} DESC"
//
//        val cursor = contentResolver.query(downloadsUri, projection, selection, selectionArgs, sortOrder)
//
//        cursor?.use {
//            val idColumnIndex = it.getColumnIndex(MediaStore.Files.FileColumns._ID)
//            val dataColumnIndex = it.getColumnIndex(MediaStore.Files.FileColumns.DATA)
//            val mimeTypeColumnIndex = it.getColumnIndex(MediaStore.Files.FileColumns.MIME_TYPE)
//
//            while (it.moveToNext()) {
//                val fileId = it.getLong(idColumnIndex)
//                val filePath = it.getString(dataColumnIndex)
//                val mimeType = it.getString(mimeTypeColumnIndex)
//                LogUtils.e("MediaStore", "ID: $fileId, Path: $filePath, MIME Type: $mimeType")
//            }
//        }
//
//
//    }
//
//    private fun getPdfFilesFromDownloadDirectory2() {
//
//        val projection = arrayOf(MediaStore.Files.FileColumns._ID, MediaStore.Files.FileColumns.DATA, MediaStore.Files.FileColumns.MIME_TYPE)
//
//        val selection = null // 不指定筛选条件，获取所有文件
//        val selectionArgs = null
//
//        val sortOrder = "${MediaStore.Files.FileColumns.DATE_ADDED} DESC"
//
//        val cursor = contentResolver.query(MediaStore.Files.getContentUri("external"), projection, selection, selectionArgs, sortOrder)
//
//        cursor?.use {
//            val idColumnIndex = it.getColumnIndex(MediaStore.Files.FileColumns._ID)
//            val dataColumnIndex = it.getColumnIndex(MediaStore.Files.FileColumns.DATA)
//            val mimeTypeColumnIndex = it.getColumnIndex(MediaStore.Files.FileColumns.MIME_TYPE)
//
//            while (it.moveToNext()) {
//                val fileId = it.getLong(idColumnIndex)
//                val filePath = it.getString(dataColumnIndex)
//                val mimeType = it.getString(mimeTypeColumnIndex)
//
//                LogUtils.e("MediaStore", "ID: $fileId, Path: $filePath, MIME Type: $mimeType")
//            }
//        }
//
//
//    }
//
//
//    fun getPdfFilesFromDownloadDirectory() {
//        val projection = arrayOf(MediaStore.Files.FileColumns.DISPLAY_NAME, MediaStore.Files.FileColumns._ID, MediaStore.Files.FileColumns.MIME_TYPE)
//        val selection = "${MediaStore.Files.FileColumns.RELATIVE_PATH} = ?"
//        val selectionArgs = arrayOf("Download/") // 下载目录的相对路径
//
//        val uri = MediaStore.Files.getContentUri("external")
//        val cursor = contentResolver.query(uri, projection, selection, selectionArgs, null)
//
//        cursor?.use {
//            val displayNameColumn = it.getColumnIndex(MediaStore.Files.FileColumns.DISPLAY_NAME)
//            val mimeTypeColumn = it.getColumnIndex(MediaStore.Files.FileColumns.MIME_TYPE)
//
//            while (it.moveToNext()) {
//                val displayName = it.getString(displayNameColumn)
//                val mimeType = it.getString(mimeTypeColumn)
//
//                // 过滤出 PDF 文件
//                if (mimeType == "application/pdf") {
//                    LogUtils.e("PDFFile", "Found PDF: $displayName")
//                }
//            }
//        }
//    }


    private var mExitTime:Long = 0
    override fun dispatchKeyEvent(event:KeyEvent):Boolean {
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

        override fun initData(extras:Bundle?) {
            super.initData(extras)
            LogUtils.dInfo("MainModel initData 1")
        }

        override fun onCreate(owner:LifecycleOwner) {
            LogUtils.dInfo("MainModel onCrete 1")
            super.onCreate(owner)
            LogUtils.dInfo("MainModel onCrete 2")
            val arrayList = mutableListOf<MainBean<*>>()
            arrayList.add(MainBean("文件相关", FileIndexActivity2::class.java))
            arrayList.add(MainBean("Ui相关", ViewIndexActivity2::class.java))
            arrayList.add(MainBean("Fragment相关", FragmentIndexActivity::class.java))
            arrayList.add(MainBean("异常相关测试", ThrowIndexActivity2::class.java))
            arrayList.add(MainBean("启动模式", SingleInstanceActivity::class.java))
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
