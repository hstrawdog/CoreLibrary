package com.hqq.example.ui

import com.hqq.core.net.ok.download.*
import com.hqq.core.ui.base.BaseActivity
import com.hqq.core.utils.file.FileUtils
import com.hqq.core.utils.log.LogUtils
import com.hqq.example.R

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

        HttpClient().download(Request().apply {
            url("https://dldir1.qq.com/weixin/android/weixin802android1860_arm64.apk")
        }, FileUtils.getCacheDir(this@DownLoadActivity) + "/a.apk",
                object : DownloadCallback {
                    override fun onDownloaded() {
                        LogUtils.e("onDownloaded")
                    }

                    override fun onProgress(percent: Float) {
                        LogUtils.e("onProgress " + percent)
                    }

                    override fun onFailure(code: Int, errMsg: String?) {
                        LogUtils.e("onFailure")
                    }

                })
    }
}