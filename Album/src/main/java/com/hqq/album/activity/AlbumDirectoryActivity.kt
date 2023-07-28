/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.hqq.album.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hqq.album.Adapter.AlbumDirectoryAdapter
import com.hqq.album.AppManager
import com.hqq.album.R
import com.hqq.album.annotation.LocalMediaType
import com.hqq.album.common.FunctionKey
import com.hqq.album.common.FunctionOptions
import com.hqq.album.common.LocalMediaLoader
import com.hqq.album.common.SelectOptions
import com.hqq.album.customize.FilterImageView
import com.hqq.album.databinding.ActivityAlbumBinding
import com.hqq.album.decoration.RecycleViewDivider
import com.hqq.album.entity.LocalMedia
import com.hqq.album.entity.LocalMediaFolder
import com.hqq.album.utils.AlbumFileUtils
import com.hqq.album.utils.AlbumUtils
import com.hqq.core.CoreConfig
import com.hqq.core.permission.IPermissionsHas
import com.hqq.core.permission.PermissionsUtils
import com.hqq.core.ui.base.BaseActivity
import com.hqq.core.utils.ToastUtils
import com.hqq.core.utils.file.FileUtils
import java.io.File

/**
 * 主界面
 * 文件夹 选择界面
 *
 * @Author : huangqiqiang
 * @Package : cn.hqq.halbum.activity
 * @FileName :   AlbumDirectoryActivity
 * @Date : 2018/6/8 0008  下午 3:14
 * @Descrive :
 * @Email :  593979591@qq.com
 */
class AlbumDirectoryActivity : BaseAlbumActivity<ActivityAlbumBinding>(), AlbumDirectoryAdapter.OnItemClickListener, View.OnClickListener {
    var mLocalAlbumCamera: FilterImageView? = null
    var mRecyclerView: RecyclerView? = null
    var mFolderList: ArrayList<LocalMediaFolder>? = ArrayList()
    private var mAdapter: AlbumDirectoryAdapter? = null
    var mIvProgressBar: ImageView? = null
    var mTvProgress: TextView? = null
    private var cameraPath: String? = null



    override fun initConfig() {
        super.initConfig()
        rootViewImpl.iToolBarBuilder.showToolBar = false
        rootViewImpl.iToolBarBuilder.showLine = false
    }

    override fun initView() {
        initViews()
    }


    override fun onClick(v: View) {
        val i = v.id
        if (i == R.id.album_back) {
            AppManager.appManager?.finishAllActivity()
        } else if (i == R.id.loacal_album_camera) {
            startUpCamera()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            // on take photo success
            if (requestCode == FunctionKey.REQUEST_CODE_REQUEST_CAMERA) {
                // 拍照返回
                val file = File(cameraPath)
                //                int degree = AlbumFileUtils.readPictureDegree(file.getAbsolutePath());
//                //旋转图片
//                Bitmap bitmap = AlbumFileUtils.rotateBitmap(degree, BitmapFactory.decodeFile(cameraPath));
//                AlbumFileUtils.saveBitmap(cameraPath, bitmap);
                if (FunctionOptions.instance.isSendAlbum) {
                    sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)))
                }
                // takePhotoSuccess = true;
                // 生成新拍照片或视频对象
                val media = LocalMedia()
                media.path = cameraPath
                media.localMediaType = LocalMediaType.VALUE_TYPE_IMAGE
                SelectOptions.instance.selectLocalMedia.add(media)
               AppManager.appManager?.finishAllActivityAndCallBack()
            }
        } else if (requestCode == FunctionKey.REQUEST_CODE_REQUEST_CAMERA && resultCode != RESULT_OK) {
            // 这边是直接打开相册的 那返回的话 直接到上个界面
            if (FunctionOptions.instance.isStartUpCamera) {
                AppManager.appManager?.finishAllActivity()
            }
        }
    }

    override fun onDestroy() {
        mFolderList = null
        super.onDestroy()
    }

    private fun startUpCamera() {
        // 启动相机拍照,先判断手机是否有拍照权限
        PermissionsUtils.requestPermissions(IPermissionsHas.camera, IPermissionsHas.storage) {
            if (it) {
                startOpenCamera()
            } else {
                mTvProgress!!.text = "哎呀!没有获取到权限,\n请在系统设置中开启权限"

            }
        }


    }

    private fun initViews() {
        mRecyclerView = findViewById(R.id.rcv_album_list)
        mIvProgressBar = findViewById(R.id.iv_progress_bar)
        mTvProgress = findViewById(R.id.tv_progress)
        findViewById<View>(R.id.album_back).setOnClickListener(this)

        // 判断是否显示相机
        if (FunctionOptions.instance.isDisplayCamera) {
            mLocalAlbumCamera = findViewById(R.id.loacal_album_camera)
            mLocalAlbumCamera?.setOnClickListener(this)
            mLocalAlbumCamera?.setVisibility(View.VISIBLE)
        }
        val manager = LinearLayoutManager(this)
        mRecyclerView?.setItemAnimator(DefaultItemAnimator())
        mRecyclerView?.addItemDecoration(RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, AlbumUtils.dip2px(this, 0.5f), ContextCompat.getColor(this, R.color.line_color)))
        mRecyclerView?.setLayoutManager(manager)
        mAdapter = AlbumDirectoryAdapter()
        mFolderList?.let { mAdapter!!.bindFolderData(it) }
        mRecyclerView?.setAdapter(mAdapter)
        mAdapter!!.setOnItemClickListener(this)
        val animation = AnimationUtils.loadAnimation(this, R.anim.rotate_loading)
        mIvProgressBar?.startAnimation(animation)

        // 第一次启动ImageActivity，没有获取过相册列表
        // 先判断手机是否有读取权限，主要是针对6.0已上系统

        PermissionsUtils.requestStorage() {
            if (it) {
                initData()
            } else {
                mTvProgress!!.text = "哎呀!没有获取到权限,\n请在系统设置中开启权限"

            }
        }
    }

    private fun initData() {
        // 判断是否直接打开相机
        if (FunctionOptions.instance.isStartUpCamera) {
            startUpCamera()
            return
        }
        // 否则再去 读取内存中的数据
        val localMediaLoader =
            LocalMediaLoader(this, FunctionOptions.instance.albumType, FunctionOptions.instance.isSupportGif)
        localMediaLoader.loadAllImage(object : LocalMediaLoader.LocalMediaLoadListener {
            override fun loadComplete(folders: List<LocalMediaFolder>?) {
                if (folders != null) {
                    mAdapter!!.bindFolderData(folders as ArrayList<LocalMediaFolder>)
                    mIvProgressBar!!.clearAnimation()
                    (mIvProgressBar!!.parent as View).visibility = View.GONE
                    mRecyclerView!!.visibility = View.VISIBLE
                }

            }

        })
    }

    /**
     * start to camera、preview、crop
     */
    fun startOpenCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (cameraIntent.resolveActivity(packageManager) != null) {
            val cameraFile = AlbumFileUtils.createCameraFile(this, 1)
            cameraPath = cameraFile?.absolutePath
//             imageUri: Uri ?
//           CoreConfig.get().application.packageName
//            val authority = "$packageName.FileProvider"
            var imageUri = FileUtils.getFile2Uri(cameraPath)


            cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            startActivityForResult(cameraIntent, FunctionKey.REQUEST_CODE_REQUEST_CAMERA)
        }
    }

    override fun onItemClick(folderName: String?, images: List<LocalMedia?>?) {

        SelectOptions.instance.mFolderLocalMedia = images as ArrayList<LocalMedia>
        startActivity(Intent(this, AlbumDetailActivity::class.java).putExtra(FunctionKey.KEY_FOLDER_NAME, folderName))
    }
}