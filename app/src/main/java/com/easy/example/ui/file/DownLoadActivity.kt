package com.easy.example.ui.file

import android.os.Build
import android.widget.Button
import android.widget.TextView
import com.easy.core.ui.base.BaseViewBindingActivity
import com.easy.core.utils.file.FileUtils
import com.easy.core.utils.log.LogUtils
import com.easy.example.R
import com.easy.example.databinding.ActivityDownloadBinding
import com.easy.core.net.net.DownloadListener
import com.easy.core.net.net.ok.OkHttp
import com.easy.core.utils.file.FilePathTools
import com.easy.core.utils.file.FileUtils.videoSaveToNotifyGalleryToRefreshWhenVersionGreaterQ
import okhttp3.Call
import java.io.*


/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui
 * @Date : 上午 9:45
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class DownLoadActivity : BaseViewBindingActivity<ActivityDownloadBinding>() {

    var okhttpCall: Call? = null

    override fun initView() {

        findViewById<Button>(R.id.button61).setOnClickListener {
            val url = "https://media.w3.org/2010/05/sintel/trailer.mp4"
            val path = FilePathTools.getCacheDir(this@DownLoadActivity) + "/"
            OkHttp.newHttpCompat()
                .downloadFile(url, 0, "trailer.mp4", path, object : DownloadListener {
                    override fun start(max: Long) {
                        LogUtils.e("onDownloaded")
                    }

                    override fun loading(progress: Float) {
                        LogUtils.e("onProgress " + progress)
                        findViewById<TextView>(R.id.textView30).setText("当前进度:         " + (progress * 100).toString())
                    }

                    override fun complete(filePath: String?) {
                        LogUtils.e("complete")
                        findViewById<TextView>(R.id.textView30).setText("下载完成 :  ${filePath}")
                        videoSaveToNotifyGalleryToRefreshWhenVersionGreaterQ(activity, File(path + "/trailer.mp4"))

                    }

                    override fun fail(code: Int, e: java.lang.Exception?) {
                        LogUtils.e("onFailure")
                        findViewById<TextView>(R.id.textView30).setText("失败 : " + code)
                    }

                })

        }

        binding.button62.setOnClickListener {
            var pdfPath = "http://103.247.176.188/Direct2.aspx?id=256965670&sign=8c5d1c425fdad174"
            val path = FilePathTools.getCacheDir(this@DownLoadActivity) + "/"
            OkHttp.newHttpCompat()
                .downloadFile(pdfPath, 0, "trailer.pdf", path, object : DownloadListener {
                    override fun start(max: Long) {
                        LogUtils.e("onDownloaded")
                    }

                    override fun loading(progress: Float) {
                        LogUtils.e("onProgress " + progress)
                        findViewById<TextView>(R.id.textView36).setText("当前进度:         " + (progress * 100).toString())
                    }

                    override fun complete(filePath: String?) {
                        LogUtils.e("complete")
                        findViewById<TextView>(R.id.textView36).setText("下载完成 :  ${filePath}")
                        filePath?.let { it1 ->
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                FileUtils.copyFileToDownloadDir(activity, it1, null,"")
                            } else {
                            }
                        }

                    }

                    override fun fail(code: Int, e: java.lang.Exception?) {
                        LogUtils.e("onFailure")
                        findViewById<TextView>(R.id.textView36).setText("失败 : " + code)
                    }

                })
        }

        var apkPath =
            "https://978b5bbe091e5b4cd0ca40c281b27ab8.dlied1.cdntips.net/imtt2.dd.qq.com/sjy.00009/sjy.00004/16891/apk/C0A06F33B61D42BEAD581A78B7A3316C.apk?mkey=lego_ztc&f=00&sche_type=7&fsname=com.sww.aspcommon_1.0.0.apk&cip=36.251.40.32&proto=https"
        var fileName = "test.apk"
        binding.button69.setOnClickListener {
            okhttpCall?.cancel()
            okhttpCall = OkHttp.newHttpCompat()
                .downloadFile(apkPath, 0, fileName, FilePathTools.getCacheDir(), object : DownloadListener {
                    override fun start(max: Long) {
                        binding.textView37.text = "start   $max"
                    }

                    override fun loading(progress: Float) {
                        binding.textView37.text = "loading    ${progress * 100}"
                    }

                    override fun complete(filePath: String?) {
                        binding.textView37.text = "complete     $filePath"
                        // 下载完成之后 复制到指定目录中


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
            val file = File(FilePathTools.getCacheDir() + File.separator + fileName)
            okhttpCall?.cancel()
            okhttpCall = OkHttp.newHttpCompat()
                .downloadFile(apkPath, file.length(), fileName, FilePathTools.getCacheDir(), object : DownloadListener {
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

    override fun onDestroy() {
        super.onDestroy()
        okhttpCall?.cancel()
    }
}