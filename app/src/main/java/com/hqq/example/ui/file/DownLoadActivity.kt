package com.hqq.example.ui.file

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.provider.MediaStore
import com.hqq.core.net.ok.download.*
import com.hqq.core.ui.base.BaseActivity
import com.hqq.core.utils.file.FileUtils
import com.hqq.core.utils.log.LogUtils
import com.hqq.example.R
import android.net.Uri
import java.io.*
import java.lang.Exception
import android.os.ParcelFileDescriptor
import android.widget.Button
import android.widget.TextView


/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui
 * @Date : 上午 9:45
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class DownLoadActivity : BaseActivity() {
    override val layoutViewId: Int
        get() = R.layout.activity_download

    override fun initView() {

//        HttpClient().download(Request().apply {
//            url("https://dldir1.qq.com/weixin/android/weixin802android1860_arm64.apk")
//        }, FileUtils.getCacheDir(this@DownLoadActivity) + "/a.apk",
//                object : DownloadCallback {
//                    override fun onDownloaded() {
//                        LogUtils.e("onDownloaded")
//                    }
//
//                    override fun onProgress(percent: Float) {
//                        LogUtils.e("onProgress " + percent)
//                    }
//
//                    override fun onFailure(code: Int, errMsg: String?) {
//                        LogUtils.e("onFailure")
//                    }
//
//                })

        findViewById<Button>(R.id.button61).setOnClickListener {
//        val url = "https://works-asp.obs.cn-north-1.myhuaweicloud.com/videos/D7E41FEA0FD864409B18DC2921D3CEECF309.mp4"
//        val url = "https://media.w3.org/2010/05/sintel/trailer.mp4"
            val url = "https://s186.convertio.me/p/m6uxG_51t9I1HKZbRmnv_A/d4c878bc05c3172944ea60d7cac37bbf/1647409049087.mp4"
            val path = FileUtils.getCacheDir(this@DownLoadActivity) + "/" + System.currentTimeMillis() + ".mp4"
            File(path)?.delete()
            HttpClient().download(Request().apply {
                url(url)
            }, path,
                object : DownloadCallback {
                    override fun onDownloaded() {
                        LogUtils.e("onDownloaded")
                        videoSaveToNotifyGalleryToRefreshWhenVersionGreaterQ(activity, File(path))
                    }

                    override fun onProgress(percent: Float) {
                        LogUtils.e("onProgress " + percent)
                        findViewById<TextView>(R.id.textView30).setText("当前进度:         " + percent.toString())
                    }

                    override fun onFailure(code: Int, errMsg: String?) {
                        LogUtils.e("onFailure")
                        findViewById<TextView>(R.id.textView30).setText("失败 : " + code)

                    }

                })
        }

    }

    fun videoSaveToNotifyGalleryToRefreshWhenVersionGreaterQ(context: Context, destFile: File) {
        val values = ContentValues()
        val uriSavedVideo: Uri?
        uriSavedVideo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Video.Media.RELATIVE_PATH, "Movies")
            values.put(MediaStore.Video.Media.TITLE, destFile.name)
            values.put(MediaStore.Video.Media.DISPLAY_NAME, destFile.name)
            values.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4")
            values.put(MediaStore.Video.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
            val collection = MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            context.contentResolver.insert(collection, values)
        } else {
            values.put(MediaStore.Video.Media.TITLE, destFile.name)
            values.put(MediaStore.Video.Media.DISPLAY_NAME, destFile.name)
            values.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4")
            values.put(MediaStore.Video.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
            values.put(MediaStore.Video.Media.DATA, destFile.absolutePath)
            context.contentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Video.Media.DATE_TAKEN, System.currentTimeMillis())
            values.put(MediaStore.Video.Media.IS_PENDING, 1)
        }
        val pfd: ParcelFileDescriptor?
        try {
            pfd = context.contentResolver.openFileDescriptor(uriSavedVideo!!, "w")
            val out = FileOutputStream(pfd!!.fileDescriptor)
            val inputStream = FileInputStream(destFile)
            val buf = ByteArray(8192)
            var len: Int
            while (inputStream.read(buf).also { len = it } > 0) {
                out.write(buf, 0, len)
            }
            out.close()
            inputStream.close()
            pfd.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.clear()
            values.put(MediaStore.Video.Media.IS_PENDING, 0)
            context.contentResolver.update(uriSavedVideo!!, values, null, null)
        }
    }

}