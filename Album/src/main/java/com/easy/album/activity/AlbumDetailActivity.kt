package com.easy.album.activity

import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.easy.album.Adapter.AlbumDetailAdapter
import com.easy.album.Adapter.AlbumDetailAdapter.OnPhotoSelectChangedListener
import com.easy.album.AppManager
import com.easy.album.R
import com.easy.album.common.Album
import com.easy.album.common.FunctionKey
import com.easy.album.common.SelectOptions
import com.easy.album.databinding.ActivityAlbumDetailBinding
import com.easy.album.decoration.GridSpacingItemDecoration
import com.easy.album.entity.LocalMedia
import com.easy.album.utils.AlbumUtils

/**
 * @Author : huangqiqiang
 * @Package : cn.easy.halbum.activity
 * @FileName :   AlbumDetailActivity
 * @Date : 2018/10/8 0008
 * @Descrive :
 * @Email :
 */
class AlbumDetailActivity : BaseAlbumActivity<ActivityAlbumDetailBinding>(), View.OnClickListener, OnPhotoSelectChangedListener {
    private var mRecyclerView: RecyclerView? = null
    var mTvFinish: TextView? = null
    var mTvTile: TextView? = null
    var mAlbumDetailAdapter: com.easy.album.Adapter.AlbumDetailAdapter? = null
    override fun onResume() {
        super.onResume()
        val list = SelectOptions.instance.mFolderLocalMedia
        mAlbumDetailAdapter!!.bindImagesData(list)
        mTvFinish!!.text =
            "完成(" + SelectOptions.instance.selectLocalMedia.size + "/" + Album.functionOptions.maxSelectNum + ")"
    }

    override fun initConfig() {
        super.initConfig()
        rootViewImpl.iToolBarBuilder.showToolBar = false
        rootViewImpl.iToolBarBuilder.showLine = false
    }

    override fun initView() {
        mRecyclerView = findViewById<View>(R.id.rcv_album_detail) as RecyclerView
        mRecyclerView!!.setHasFixedSize(true)
        mRecyclerView!!.addItemDecoration(GridSpacingItemDecoration(4, AlbumUtils.dip2px(this, 2f), false))
        mRecyclerView!!.layoutManager = GridLayoutManager(this, 4)
        mAlbumDetailAdapter =
            com.easy.album.Adapter.AlbumDetailAdapter(Album.functionOptions.maxSelectNum)
        mRecyclerView!!.adapter = mAlbumDetailAdapter
        mAlbumDetailAdapter!!.setOnPhotoSelectChangedListener(this)
        findViewById<View>(R.id.album_back).setOnClickListener(this)
        findViewById<View>(R.id.album_finish).setOnClickListener(this)
        mTvFinish = findViewById<View>(R.id.album_finish) as TextView
        mTvTile = findViewById<View>(R.id.album_title) as TextView
        mTvTile!!.text = intent.getStringExtra(FunctionKey.KEY_FOLDER_NAME)
    }

    override fun onClick(view: View) {
        val i = view.id
        if (i == R.id.album_back) {
            onBackPressed()
        }
        if (i == R.id.album_finish) {
            AppManager.appManager?.finishAllActivityAndCallBack()
        }
    }

    override fun onTakePhoto() { // 打开相机
    }

    override fun onChange(selectImages: List<com.easy.album.entity.LocalMedia>?) {
        mTvFinish!!.text = "完成(" + selectImages?.size + "/" + Album.functionOptions.maxSelectNum + ")"
    }

    override fun onPictureClick(media: com.easy.album.entity.LocalMedia?, position: Int) {
        startActivityForResult(Intent(this, AlbumPreviewActivity::class.java).putExtra(FunctionKey.KEY_POSITION, position + 1)
            .putExtra(FunctionKey.KEY_FOLDER_NAME, intent.getStringExtra(FunctionKey.KEY_FOLDER_NAME)), CODE_CLOSE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CODE_CLOSE && resultCode == RESULT_OK) {
            finish()
        }
    }

    companion object {
        private const val CODE_CLOSE = 0x9910
    }
}