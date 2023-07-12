package com.hqq.example.ui.file

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
import android.widget.Button
import android.widget.TextView
import com.hqq.core.ui.base.BaseViewBindingActivity
import com.hqq.core.utils.file.FileUtils
import com.hqq.core.utils.log.LogUtils
import com.hqq.example.R
import com.hqq.example.databinding.ActivityDownloadBinding
import com.hqq.core.net.DownloadListener
import com.hqq.core.net.ok.OkHttp
import com.hqq.core.utils.file.FileUtils.videoSaveToNotifyGalleryToRefreshWhenVersionGreaterQ
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


        findViewById<Button>(R.id.button61).setOnClickListener {
            val url = "https://media.w3.org/2010/05/sintel/trailer.mp4"
            val path = FileUtils.getCacheDir(this@DownLoadActivity) + "/"
            OkHttp.newHttpCompat()
                .downloadFile(url, 0, "trailer.mp4", path, object : DownloadListener {
                    override fun start(max: Long) {
                        LogUtils.e("onDownloaded")
                    }

                    override fun loading(progress: Float) {
                        LogUtils.e("onProgress " + progress)
                        findViewById<TextView>(R.id.textView30).setText(
                            "当前进度:         " + (progress * 100).toString())
                    }

                    override fun complete(filePath: String?) {
                        LogUtils.e("complete")
                        findViewById<TextView>(R.id.textView30).setText("下载完成 :  ${filePath}")
                        videoSaveToNotifyGalleryToRefreshWhenVersionGreaterQ(activity,
                            File(path + "/trailer.mp4"))

                    }

                    override fun fail(code: Int, e: java.lang.Exception?) {
                        LogUtils.e("onFailure")
                        findViewById<TextView>(R.id.textView30).setText("失败 : " + code)
                    }

                })

        }

        binding.button62.setOnClickListener {
            var pdfPath = "http://103.247.176.188/Direct2.aspx?id=256965670&sign=8c5d1c425fdad174"
            val path = FileUtils.getCacheDir(this@DownLoadActivity) + "/"
            OkHttp.newHttpCompat()
                .downloadFile(pdfPath, 0, "trailer.pdf", path, object : DownloadListener {
                    override fun start(max: Long) {
                        LogUtils.e("onDownloaded")
                    }

                    override fun loading(progress: Float) {
                        LogUtils.e("onProgress " + progress)
                        findViewById<TextView>(R.id.textView36).setText(
                            "当前进度:         " + (progress * 100).toString())
                    }

                    override fun complete(filePath: String?) {
                        LogUtils.e("complete")
                        findViewById<TextView>(R.id.textView36).setText("下载完成 :  ${filePath}")

                    }

                    override fun fail(code: Int, e: java.lang.Exception?) {
                        LogUtils.e("onFailure")
                        findViewById<TextView>(R.id.textView36).setText("失败 : " + code)
                    }

                })
        }

        var apkPath =
            "https://36dc9d746160f66738522d22ba9bf5ff.dlied1.cdntips.net/imtt2.dd.qq.com/sjy.00009/sjy.00004/16891/apk/EE6A5A2A1334E9234F7D9112DBA33A2D.apk?mkey=648c38e1da0524f3&f=24c5&fsname=com.sww.asp_5.4.9_89.apk&cip=218.5.2.6&proto=https"
        var fileName = "test.apk"
        binding.button69.setOnClickListener {
            okhttpCall?.cancel()
            okhttpCall = OkHttp.newHttpCompat()
                .downloadFile(apkPath, 0, fileName, FileUtils.getCacheDir(),
                    object : DownloadListener {
                        override fun start(max: Long) {
                            binding.textView37.text = "start   $max"
                        }

                        override fun loading(progress: Float) {
                            binding.textView37.text = "loading    ${progress * 100}"
                        }

                        override fun complete(filePath: String?) {
                            binding.textView37.text = "complete     $filePath"
                        }

                        override fun fail(code: Int, e: java.lang.Exception?) {
                            binding.textView37.text = "fail $code       ${e.toString()}"
                        }
                    })
        }
        binding.button70.setOnClickListener {
            binding.textView37.text = "取消下载"

            okhttpCall?.cancel()
        }
        binding.button71.setOnClickListener {
            val file = File(FileUtils.getCacheDir() + File.separator + fileName)
            okhttpCall?.cancel()
            okhttpCall = OkHttp.newHttpCompat()
                .downloadFile(apkPath, file.length(), fileName, FileUtils.getCacheDir(),
                    object : DownloadListener {
                        override fun start(max: Long) {
                            binding.textView37.text = "start %=$max"
                        }

                        override fun loading(progress: Float) {
                            binding.textView37.text = "loading %=${progress * 100}"

                        }

                        override fun complete(filePath: String?) {
                            binding.textView37.text = "complete %=$filePath"

                        }

                        override fun fail(code: Int, e: java.lang.Exception?) {
                            binding.textView37.text = "fail %=${e.toString()}"

                        }
                    })

        }
    }


}