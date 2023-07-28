package com.hqq.album.activity

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hqq.album.Adapter.AlbumDetailAdapter
import com.hqq.album.Adapter.AlbumDetailAdapter.OnPhotoSelectChangedListener
import com.hqq.album.Adapter.AlbumDirectoryAdapter
import com.hqq.album.AppManager
import com.hqq.album.R
import com.hqq.album.common.FunctionKey
import com.hqq.album.common.FunctionOptions
import com.hqq.album.common.LocalMediaLoader
import com.hqq.album.common.SelectOptions
import com.hqq.album.decoration.GridSpacingItemDecoration
import com.hqq.album.decoration.RecycleViewDivider
import com.hqq.album.entity.LocalMedia
import com.hqq.album.entity.LocalMediaFolder
import com.hqq.album.utils.AlbumUtils
import com.hqq.core.permission.PermissionsUtils
import com.hqq.core.ui.base.BaseActivity
import com.hqq.core.utils.ToastUtils

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.album.activity
 * @Date : 下午 4:51
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class AlbumFolderActivity : BaseActivity(), OnPhotoSelectChangedListener, View.OnClickListener,
    AlbumDirectoryAdapter.OnItemClickListener {

    companion object {
        private const val CODE_CLOSE = 0x9910
    }


    var mFolderList: List<LocalMediaFolder> = ArrayList()
    private var mAdapter: AlbumDirectoryAdapter? = null
    private var mRecyclerView: RecyclerView? = null
    var mTvFinish: TextView? = null
    var mAlbumDetailAdapter: AlbumDetailAdapter? = null
    var mTvTile: TextView? = null
    var mSelectPosition = 0
    var mFolderName: String? = null
    override val layoutViewId: Int = R.layout.activity_album_folder
    override fun initConfig() {
        super.initConfig()
        rootViewImpl.iToolBarBuilder.showToolBar = false

    }

    override fun initView() {
        mTvFinish = findViewById<View>(R.id.album_finish) as TextView
        mTvFinish!!.setOnClickListener(this)
        mRecyclerView = findViewById<View>(R.id.rc_list) as RecyclerView
        mTvTile = findViewById<View>(R.id.album_title) as TextView
        findViewById<View>(R.id.album_back).setOnClickListener(this)
        findViewById<View>(R.id.ll_title).setOnClickListener(this)
        mFolderName = FunctionOptions.instance.chooseFolder
        initViews()
        mRecyclerView!!.setHasFixedSize(true)
        mRecyclerView!!.addItemDecoration(GridSpacingItemDecoration(4, AlbumUtils.dip2px(this, 2f), false))
        mRecyclerView!!.layoutManager = GridLayoutManager(this, 4)
        mAlbumDetailAdapter = AlbumDetailAdapter(FunctionOptions.instance.maxSelectNum)
        mRecyclerView!!.adapter = mAlbumDetailAdapter
        mAlbumDetailAdapter!!.setOnPhotoSelectChangedListener(this)
    }

    override fun onResume() {
        super.onResume()
        mTvFinish!!.text =
            "完成(" + SelectOptions.instance.selectLocalMedia.size + "/" + FunctionOptions.instance.maxSelectNum + ")"
    }

    override fun onClick(view: View) {
        val i = view.id
        if (i == R.id.album_back) {
            if (mRecyclerView!!.adapter === mAdapter) {
                mRecyclerView!!.removeItemDecorationAt(0)
                mRecyclerView!!.addItemDecoration(GridSpacingItemDecoration(4, AlbumUtils.dip2px(this, 2f), false))
                mRecyclerView!!.layoutManager = GridLayoutManager(this, 4)
                mRecyclerView!!.adapter = mAlbumDetailAdapter
                return
            }
            AppManager.appManager?.finishActivity()
        } else if (i == R.id.album_finish) {
            AppManager.appManager?.finishAllActivityAndCallBack()
        } else if (i == R.id.ll_title) {
            mAdapter = AlbumDirectoryAdapter()
            mAdapter!!.bindFolderData(mFolderList as ArrayList<LocalMediaFolder>)
            mRecyclerView!!.removeItemDecorationAt(0)
            mRecyclerView!!.addItemDecoration(RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, AlbumUtils.dip2px(this, 0.5f), ContextCompat.getColor(this, R.color.line_color)))
            mRecyclerView!!.layoutManager = LinearLayoutManager(this)
            mRecyclerView!!.adapter = mAdapter
            mAdapter!!.setOnItemClickListener(this)
            mRecyclerView!!.scrollToPosition(mSelectPosition)
        }
    }

    private fun initViews() {
        // 第一次启动ImageActivity，没有获取过相册列表
        // 先判断手机是否有读取权限，主要是针对6.0已上系统
        PermissionsUtils.requestStorage() {
            if (it) {
                initData()
            } else {
                ToastUtils.showToast("读取内存卡权限已被拒绝,请在系统设置中开启权限")
            }
        }
    }

    private fun initData() {
        val localMediaLoader =
            LocalMediaLoader(this, FunctionOptions.instance.albumType, FunctionOptions.instance.isSupportGif)
        localMediaLoader.loadAllImage(object : LocalMediaLoader.LocalMediaLoadListener {
            override fun loadComplete(folders: List<LocalMediaFolder>?) {
                if (folders != null) {
                    mFolderList = folders
                    setAlbum(folders)
                    if (mAlbumDetailAdapter!!.itemCount <= 0) {
                        mFolderName = "最近照片"
                        setAlbum(folders)
                    }
                }

            }

        })

    }

    private fun setAlbum(folders: List<LocalMediaFolder>) {
        for (i in folders.indices) {
            if (folders[i].name == mFolderName) {
                mAlbumDetailAdapter!!.bindImagesData(folders[i].images)
                SelectOptions.instance.mFolderLocalMedia = folders[i].images
                mTvTile!!.text = mFolderName
                mSelectPosition = i
                break
            }
        }
    }

    override fun onTakePhoto() {}
    override fun onChange(selectImages: List<LocalMedia>?) {
        mTvFinish!!.text = "完成(" + selectImages?.size + "/" + FunctionOptions.instance.maxSelectNum + ")"
    }

    override fun onPictureClick(media: LocalMedia?, position: Int) {
        startActivityForResult(Intent(this, AlbumPreviewActivity::class.java).putExtra(FunctionKey.KEY_POSITION, position + 1)
            .putExtra(FunctionKey.KEY_FOLDER_NAME, mFolderName), CODE_CLOSE)
    }

    override fun onItemClick(folderName: String?, images: List<LocalMedia?>?) {
        mRecyclerView!!.removeItemDecorationAt(0)
        mRecyclerView!!.addItemDecoration(GridSpacingItemDecoration(4, AlbumUtils.dip2px(this, 2f), false))
        mRecyclerView!!.layoutManager = GridLayoutManager(this, 4)
        mAlbumDetailAdapter!!.bindImagesData(images as List<LocalMedia>)
        mRecyclerView!!.adapter = mAlbumDetailAdapter
        SelectOptions.instance.mFolderLocalMedia = images as ArrayList<LocalMedia>
        mFolderName = folderName
        for (i in mFolderList.indices) {
            if (mFolderList[i].name == folderName) {
                mTvTile!!.text = mFolderName
                mSelectPosition = i
                break
            }
        }
    }


}