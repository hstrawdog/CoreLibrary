package com.hqq.example.ui.file

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
import android.widget.Button
import android.widget.TextView
import com.hqq.core.net.download.*
import com.hqq.core.ui.base.BaseViewBindingActivity
import com.hqq.core.utils.encrypt.MD5Utils
import com.hqq.core.utils.file.FileUtils
import com.hqq.core.utils.log.LogUtils
import com.hqq.example.R
import com.hqq.example.databinding.ActivityDownloadBinding
import com.hqq.core.net.DownloadListener
import com.hqq.core.net.ok.OkHttp
import com.hqq.core.utils.TimeTool.nowDate4yyyyMMdd
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Call
import java.io.*


/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui
 * @Date : 上午 9:45
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class DownLoadActivity : BaseViewBindingActivity<ActivityDownloadBinding>() {

    var okhttpCall: Call? = null

    override fun initView() {

//        HttpClient().download(Request().apply {
//            url("https://dldir1.qq.com/weixin/android/weixin802android1860_arm64.apk")
//        }, FileUtils.getCacheDir(this@DownLoadActivity) + "/a.apk",
//                object : DownloadCallback {
//                    override fun onDownloaded() {
//                        LogUtils.dInfo("onDownloaded")
//                    }
//
//                    override fun onProgress(percent: Float) {
//                        LogUtils.dInfo("onProgress " + percent)
//                    }
//
//                    override fun onFailure(code: Int, errMsg: String?) {
//                        LogUtils.dInfo("onFailure")
//                    }
//
//                })

        binding.button62.setOnClickListener {
            var pdfUrl =
                "https://ft.ncpssd.org/pdf/getxwkSky/topicsxwk/pdf/SKY_XJP/JHXK202203023.pdf"
            val path =
                FileUtils.getCacheDir(this@DownLoadActivity) + "/" + System.currentTimeMillis() + ".pdf"

//        RequestBody requestBody = RequestBody.create("", MediaType.parse("application/xml"));
            val sign: String = MD5Utils.encryptStr("L!N45S26y1SGzq9^" + nowDate4yyyyMMdd)
            HttpClient().download(Request().apply {
                url(pdfUrl).addHeader("sing", "7f7cb58c49faa56b4128bdb318e2837f")
                addHeader("Content-Type", "application/xml")
            }, path, object : DownloadCallback {
                override fun onDownloaded() {
                    LogUtils.e("onDownloaded")

                }

                override fun onProgress(percent: Float) {
                    LogUtils.e("onProgress   ${percent}")
                    CoroutineScope(Dispatchers.Main).launch {
                        binding.textView30.text = "  $percent  "
                    }

                }

                override fun onFailure(code: Int, errMsg: String?) {
                    LogUtils.e("onFailure")

                }

            })

        }


        findViewById<Button>(R.id.button61).setOnClickListener {
//        val url = "https://works-asp.obs.cn-north-1.myhuaweicloud.com/videos/D7E41FEA0FD864409B18DC2921D3CEECF309.mp4"
            val url = "https://media.w3.org/2010/05/sintel/trailer.mp4"
////            val url = "https://s186.convertio.me/p/m6uxG_51t9I1HKZbRmnv_A/d4c878bc05c3172944ea60d7cac37bbf/1647409049087.mp4"
//            val url = "https://works-asp.obs.cn-north-1.myhuaweicloud.com/videos/34DE7E9C6F0F7346A38B92CB23EB01571857.mp4"
////            val url = "https://works-asp.obs.cn-north-1.myhuaweicloud.com/videos/F5CD39FFCB763249766AC4EBC07650D832C2.mp4"
            val path =
                FileUtils.getCacheDir(this@DownLoadActivity) + "/" + System.currentTimeMillis() + ".mp4"
            File(path)?.delete()
            HttpClient().download(Request().apply {
                url(url)
            }, path, object : DownloadCallback {
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


        okhttp()
    }

    private fun okhttp() {

        var apkPath =
            "https://cos.pgyer.com/a23203790be8b427d740bbb60b574c48.apk?sign=6c5f523895347d047b9cdec85ef0306c&t=1668140148&response-content-disposition=attachment%3Bfilename%3D%E7%88%B1%E9%A5%B0%E6%8B%8D_3.3.3.apk"
        var fileName = "test.apk"
        var pdfPath = "http://103.247.176.188/Direct2.aspx?id=256965670&sign=8c5d1c425fdad174"
        binding.button69.setOnClickListener {
            okhttpCall = OkHttp.newHttpCompat().downloadFile(
                apkPath,
                0,
                fileName,
                FileUtils.getCacheDir(),
                object : DownloadListener {
                    override fun start(max: Long) {
                        binding.textView30.text = "start   $max"
                    }

                    override fun loading(progress: Float) {
                        binding.textView36.text = "loading    ${progress * 100}"

                    }

                    override fun complete(filePath: String?) {
                        binding.textView37.text = "complete     $filePath"

                    }

                    override fun fail(code: Int, e: java.lang.Exception?) {
                        binding.textView38.text = "fail $code       ${e.toString()}"

                    }
                })
        }
        binding.button70.setOnClickListener {
            okhttpCall?.cancel()
        }

        binding.button71.setOnClickListener {
            val file = File(FileUtils.getCacheDir() + File.separator + fileName)

            okhttpCall = OkHttp.newHttpCompat().downloadFile(
                apkPath,
                file.length(),
                fileName,
                FileUtils.getCacheDir(),
                object : DownloadListener {
                    override fun start(max: Long) {
                        binding.textView30.text = "start %=$max"
                    }

                    override fun loading(progress: Float) {
                        binding.textView36.text = "loading %=${progress * 100}"

                    }

                    override fun complete(filePath: String?) {
                        binding.textView37.text = "complete %=$filePath"

                    }

                    override fun fail(code: Int, e: java.lang.Exception?) {
                        binding.textView38.text = "fail %=${e.toString()}"

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
            val collection =
                MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
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