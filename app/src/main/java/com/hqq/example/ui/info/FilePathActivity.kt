package com.hqq.example.ui.info

import android.widget.TextView
import com.hqq.core.ui.base.BaseActivity
import com.hqq.core.utils.FilePathUtils.externalStorageDirectory
import com.hqq.core.utils.FilePathUtils.getCacheDir
import com.hqq.core.utils.FilePathUtils.getExternalCacheDir
import com.hqq.core.utils.FilePathUtils.getExternalFilesDir
import com.hqq.core.utils.FilePathUtils.getFileStreamPath
import com.hqq.core.utils.FilePathUtils.getFilesDir
import com.hqq.core.utils.FilePathUtils.getSdCardExternalStoragePublicDirectory
import com.hqq.core.utils.FilePathUtils.path4Data
import com.hqq.core.utils.FilePathUtils.sdCardExternalStorageDirectory
import com.hqq.core.utils.FilePathUtils.sdCardExternalStoragePublicDirectory
import com.hqq.core.utils.TextSpannableBuilder
import com.hqq.example.R

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.info
 * @FileName :   FilePathActivity
 * @Date : 2019/11/29 0029  上午 10:23
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
class FilePathActivity : BaseActivity() {
    lateinit var mTvInfo: TextView
    override fun getLayoutViewId(): Int {
        return R.layout.activity_base_info
    }

    override fun initView() {
        mTvInfo = findViewById(R.id.tv_info)
        mTvInfo.setText(TextSpannableBuilder()
                .addTextPart("\nEnvironment.getDataDirectory()    ")
                .addTextPart(path4Data)
                .addTextPart("\nContext.getCacheDir()  ")
                .addTextPart(getCacheDir(this))
                .addTextPart("\nContext.getFilesDir()  ")
                .addTextPart(getFilesDir(this))
                .addTextPart("\nContext.getFileStreamPath()  ")
                .addTextPart(getFileStreamPath(this))
                .addTextPart("\nContext.getFileStreamPath()  ")
                .addTextPart(getFileStreamPath(this, "fileName"))
                .addTextPart("\nContext.getExternalStorageDirectory()  ")
                .addTextPart(externalStorageDirectory)
                .addTextPart("\nContext.getExternalCacheDir()  ")
                .addTextPart(getExternalCacheDir(this))
                .addTextPart("\nContext.getExternalFilesDir()  ")
                .addTextPart(getExternalFilesDir(this))
                .addTextPart("\nContext.getExternalFilesDir()  ")
                .addTextPart(getExternalFilesDir(this, "fileName"))
                .addTextPart("\nContext.getSdCardExternalStorageDirectory()  ")
                .addTextPart(sdCardExternalStorageDirectory)
                .addTextPart("\nContext.getSdCardExternalStoragePublicDirectory()  ")
                .addTextPart(sdCardExternalStoragePublicDirectory)
                .addTextPart("\nContext.getSdCardExternalStoragePublicDirectory()  ")
                .addTextPart(getSdCardExternalStoragePublicDirectory("fileName"))
                .addTextPart("\n  ")
                .addTextPart("")
                .build())
    }
}