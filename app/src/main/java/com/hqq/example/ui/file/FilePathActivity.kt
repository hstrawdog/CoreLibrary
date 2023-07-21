package com.hqq.example.ui.file

import android.os.Environment
import android.widget.TextView
import com.hqq.core.ui.base.BaseActivity
import com.hqq.core.utils.file.FileUtils
import com.hqq.core.utils.text.TextSpannableBuilder
import com.hqq.example.R

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.system.info
 * @FileName :   FilePathActivity
 * @Date : 2019/11/29 0029  上午 10:23
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
class FilePathActivity : BaseActivity() {
    lateinit var mTvInfo: TextView
    override val layoutViewId: Int = R.layout.activity_base_info

    override fun initView() {
        mTvInfo = findViewById(R.id.tv_info)
        mTvInfo.setText(TextSpannableBuilder()
                .addTextPart("\nContext.getCacheDir()  ")
                .addTextPart("\n")
                .addTextPart(FileUtils.getCacheDir(this))
                .addTextPart("\n")
                .addTextPart("\nContext.getFilesDir()  ")
                .addTextPart("\n")
                .addTextPart(FileUtils.getFilesDir(this))
                .addTextPart("\n")
                .addTextPart("\nContext.getPackageDir()  ")
                .addTextPart("\n")
                .addTextPart(FileUtils.getPackageDir(""))
                .addTextPart("\n")
                .addTextPart("\nContext.getFileStreamPath()  ")
                .addTextPart("\n")
                .addTextPart(FileUtils.getFileStreamPath(this))
                .addTextPart("\n")
                .addTextPart("\nContext.getFileStreamPath()  ")
                .addTextPart("\n")
                .addTextPart(FileUtils.getFileStreamPath(this, "fileName"))
                .addTextPart("\n")
                .addTextPart("\nContext.getExternalCacheDir()  ")
                .addTextPart("\n")
                .addTextPart(FileUtils.getExternalCacheDir(this))
                .addTextPart("\n")
                .addTextPart("\nContext.getExternalFilesDir()  ")
                .addTextPart("\n")
                .addTextPart(FileUtils.getExternalFilesDir(this))
                .addTextPart("\n")
                .addTextPart("\nContext.getExternalFilesDir()  ")
                .addTextPart("\n")
                .addTextPart(FileUtils.getExternalFilesDir(this, "fileName"))
                .addTextPart("\n")
                .addTextPart("\nContext.getExternalStorageDirectory()  ")
                .addTextPart("\n")
                .addTextPart(Environment.getExternalStorageDirectory().absolutePath)

                .build())
    }
}